package com.example.toutiao.ui.page.newsChannel;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.toutiao.R;
import com.example.toutiao.models.news.NewsDataModel;
import com.example.toutiao.ui.card.newsCardList.NewsCardAdapter;
import com.example.toutiao.ui.card.newsCardList.NewsCardItemDataModel;
import com.example.toutiao.ui.refresh.CircleRefreshLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.toutiao.ui.card.newsCardList.NewsCardItemDataModel.NO_IMAGE_TYPE;
import static com.example.toutiao.ui.card.newsCardList.NewsCardItemDataModel.ONE_IMAGE_TYPE;
import static com.example.toutiao.ui.card.newsCardList.NewsCardItemDataModel.THREE_IMAGE_TYPE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsChannelFragment#newInstance} factory method to
 * create an instance of this fragment.
 * 让 fragment 实现 BGARefreshLayoutDelegate 接口
 */
public class NewsChannelFragment extends Fragment {
    private final static String BASE_URL =
            "https://www.toutiao.com/api/pc/feed/?max_behot_time=%d&category=%s";
    private static final String[] CATEGORY_ATTR = new String[]{
            "__all__",
            "news_tech",
//            "news_image",
            "news_hot",
            "news_entertainment",
            "news_game",
            "news_sports",
            "news_finance",
            "digital"
    };
    private final static String DEFAULT_AVATAR =
            "https://img.88icon.com/download/jpg/20200901/84083236c883964781afea41f1ea4e9c_512_511.jpg!88bg";
    private final static String DEFAULT_IMAGE =
            "https://www.asiapacdigital.com/Zh_Cht/img/ap/services/reseller/TouTiao_1.jpg";
    private ArrayList<NewsDataModel> mNewsDataModelList = new ArrayList<>();
    private PageViewModel mPageViewModel;
    private RecyclerView mCardListRecyclerView;
    private NewsCardAdapter mCardListAdapter;
    private RecyclerView.LayoutManager mCardListLayoutManager;
    private LottieAnimationView mLoadingAnimationView;
    private CircleRefreshLayout mCardListRefreshLayout;
    private Button mLoadingMoreButton;
    private FloatingActionButton mPublishFab;
    private View mScreenMaskView;
    private Boolean mIsScrollToTop = false;
    private final List<NewsCardItemDataModel> mCardDataModelList = new ArrayList<>();
    private String mCategory;
    private int mIndex;
    private boolean mIsRefresh = false;
    private boolean mIsLoadMore = false;
    private boolean mIsLoadingFail = false;
    private int mMaxBehotTime = 0;
    private String mCookie;

    public NewsChannelFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static NewsChannelFragment newInstance(String category, int index) {
        NewsChannelFragment fragment = new NewsChannelFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        bundle.putInt("index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        if (getArguments() != null) {
            mCategory = getArguments().getString("category");
            mIndex = getArguments().getInt("index");
        }
        mPageViewModel.setCategory(mCategory);
        mPageViewModel.setIndex(mIndex);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_channel, container, false);

        // screen mask show when loading
        mScreenMaskView = view.findViewById(R.id.view_screen_mask);
        mScreenMaskView.setVisibility(View.GONE);

        // setting loading animation view
        mLoadingAnimationView = view.findViewById(R.id.animation_view_loading);
        // animation file
        mLoadingAnimationView.setAnimation("load-animation.json");
        // speed
        mLoadingAnimationView.setSpeed(1);
        mLoadingAnimationView.playAnimation();
        // setting loading button
        mLoadingMoreButton = view.findViewById(R.id.button_loading_more);
        mLoadingMoreButton.setVisibility(View.GONE);
        // Publish FAB
        mPublishFab = view.findViewById(R.id.fab_publish);
        mPublishFab.hide();
        mIsScrollToTop = false;
        // setting FAB onClick event
        mPublishFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCardListRecyclerView != null) {
                    // TODO: define some events
                }
            }
        });
        // cardList
        mCardListRecyclerView = view.findViewById(R.id.recycler_view_card_list);
        mCardListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (!recyclerView.canScrollVertically(1)) { //1 for down
                    try {
                        loadMoreNews();
                        mScreenMaskView.setVisibility(View.VISIBLE);
                        mLoadingMoreButton.setVisibility(View.VISIBLE);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (dy < 0) {
                    mPublishFab.hide();
                }
                else if (dy > 0) {
                    mPublishFab.show();
                }
            }
        });

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mCardListRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mCardListLayoutManager = new LinearLayoutManager(container.getContext());
        mCardListRecyclerView.setLayoutManager(mCardListLayoutManager);

        // specify an adapter and pass in our data model list
        mCardListAdapter = new NewsCardAdapter(mCardDataModelList, container.getContext());
        mCardListRecyclerView.setAdapter(mCardListAdapter);

        mCardListRefreshLayout = view.findViewById(R.id.refresh_layout_card_list);
        mCardListRefreshLayout.setOnRefreshListener(new CircleRefreshLayout.OnCircleRefreshListener() {
            @Override
            public void completeRefresh() {

            }

            @Override
            public void refreshing() throws IOException, JSONException {
                refreshNews();
                mScreenMaskView.setVisibility(View.VISIBLE);
            }
        });

        TextView mSectionLabelTextView = view.findViewById(R.id.text_view_section_label);

            try {
                // init
                getInitNews();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        mPageViewModel.getCategory().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mSectionLabelTextView.setText(s);
            }
        });

        return view;
    }

    // render the recycler view card list when init and refreshing
    public void initRenderCardList() {
        Log.v("start init render", "render init card, news list size: " + mNewsDataModelList.size());
        for (int i = 0; i < mNewsDataModelList.size(); i++) {
            int type = mNewsDataModelList.get(i).getNewsCardStyleType();
            String newsId = mNewsDataModelList.get(i).getNewsId();
            String newsTitle = mNewsDataModelList.get(i).getNewsTitle();
            String newsAbstract = mNewsDataModelList.get(i).getNewsAbstract();
            int newsCommentsCount = mNewsDataModelList.get(i).getNewsCommentsCount();
            String newsSource = mNewsDataModelList.get(i).getNewsSource();
            String newsMediaAvatarUrl = mNewsDataModelList.get(i).getNewsMediaAvatarUrl();
            String newsSourceUrl = mNewsDataModelList.get(i).getNewsSourceUrl();

            if (type == NO_IMAGE_TYPE) {
                mCardDataModelList.add(new NewsCardItemDataModel(
                        NO_IMAGE_TYPE,
                        newsId,
                        newsTitle,
                        newsAbstract,
                        newsCommentsCount,
                        newsSource,
                        newsMediaAvatarUrl,
                        newsSourceUrl
                ));
            } else if (type == ONE_IMAGE_TYPE) {
                String middleImage = mNewsDataModelList.get(i).getNewsImageUrl();
                mCardDataModelList.add(new NewsCardItemDataModel(
                        ONE_IMAGE_TYPE,
                        newsId,
                        newsTitle,
                        newsAbstract,
                        newsCommentsCount,
                        newsSource,
                        newsMediaAvatarUrl,
                        newsSourceUrl,
                        middleImage
                ));

            } else if (type == THREE_IMAGE_TYPE) {
                ArrayList<String> newsThreeImage = mNewsDataModelList.get(i).getNewsThreeImage();
                mCardDataModelList.add(new NewsCardItemDataModel(
                        THREE_IMAGE_TYPE,
                        newsId,
                        newsTitle,
                        newsAbstract,
                        newsCommentsCount,
                        newsSource,
                        newsMediaAvatarUrl,
                        newsSourceUrl,
                        newsThreeImage
                ));
            }
        }
        mLoadingAnimationView.setVisibility(View.GONE);
        mScreenMaskView.setVisibility(View.GONE);
        mCardListRefreshLayout.finishRefreshing();
        mCardListAdapter = new NewsCardAdapter(mCardDataModelList, getContext());
        mCardListRecyclerView.setAdapter(mCardListAdapter);

        mIsRefresh = false;
    }

    /**
     * render the recycler view card list when loading more
     */
    private void loadMoreRenderCardList() {
        Log.v("start more render", "render more card, news list size: " + mNewsDataModelList.size());

        List<NewsCardItemDataModel> tempCardDataModelList = new ArrayList<>();

        for (int i = 0; i < mNewsDataModelList.size(); i++) {
            int type = mNewsDataModelList.get(i).getNewsCardStyleType();
            String newsId = mNewsDataModelList.get(i).getNewsId();
            String newsTitle = mNewsDataModelList.get(i).getNewsTitle();
            String newsAbstract = mNewsDataModelList.get(i).getNewsAbstract();
            int newsCommentsCount = mNewsDataModelList.get(i).getNewsCommentsCount();
            String newsSource = mNewsDataModelList.get(i).getNewsSource();
            String newsMediaAvatarUrl = mNewsDataModelList.get(i).getNewsMediaAvatarUrl();
            String newsSourceUrl = mNewsDataModelList.get(i).getNewsSourceUrl();

            if (type == NO_IMAGE_TYPE) {
                tempCardDataModelList.add(new NewsCardItemDataModel(
                        NO_IMAGE_TYPE,
                        newsId,
                        newsTitle,
                        newsAbstract,
                        newsCommentsCount,
                        newsSource,
                        newsMediaAvatarUrl,
                        newsSourceUrl
                ));
            } else if (type == ONE_IMAGE_TYPE) {
                String middleImage = mNewsDataModelList.get(i).getNewsImageUrl();
                tempCardDataModelList.add(new NewsCardItemDataModel(
                        ONE_IMAGE_TYPE,
                        newsId,
                        newsTitle,
                        newsAbstract,
                        newsCommentsCount,
                        newsSource,
                        newsMediaAvatarUrl,
                        newsSourceUrl,
                        middleImage
                ));

            } else if (type == THREE_IMAGE_TYPE) {
                ArrayList<String> newsThreeImage = mNewsDataModelList.get(i).getNewsThreeImage();
                tempCardDataModelList.add(new NewsCardItemDataModel(
                        THREE_IMAGE_TYPE,
                        newsId,
                        newsTitle,
                        newsAbstract,
                        newsCommentsCount,
                        newsSource,
                        newsMediaAvatarUrl,
                        newsSourceUrl,
                        newsThreeImage
                ));
            }
        }
        mLoadingAnimationView.setVisibility(View.GONE);
        mScreenMaskView.setVisibility(View.GONE);
        mLoadingMoreButton.setVisibility(View.GONE);
        mCardListAdapter.setDataModelList(tempCardDataModelList);
        Log.v("after load more", "card list size: " + mCardListAdapter.getItemCount());
        mIsLoadMore = false;
        mCardListAdapter.notifyDataSetChanged();
    }

    /**
     * when loading fail (request body is null), show toast
     */
    private void loadingFail() {
        Toast showFailToast = Toast.makeText(getActivity(), "加载失败", Toast.LENGTH_LONG);
        showFailToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL,0 , 200);
        showFailToast.show();
        mLoadingAnimationView.setVisibility(View.GONE);
        mScreenMaskView.setVisibility(View.GONE);
        mLoadingMoreButton.setVisibility(View.GONE);
        mCardListRefreshLayout.finishRefreshing();
        mIsRefresh = false;
        mIsLoadMore = false;
        mIsLoadingFail = false;
    }

    private String getUserAgent() {
        String userAgent = "";
        try {
            userAgent = WebSettings.getDefaultUserAgent(getContext());
        } catch (Exception e) {
            userAgent = System.getProperty("https.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * a methods to init card list
     *
     * @throws IOException
     * @throws JSONException
     */
    public void getInitNews() throws IOException, JSONException {
        // init data
        mNewsDataModelList.clear();
        mCardDataModelList.clear();
        mLoadingAnimationView.setVisibility(View.VISIBLE);
        mMaxBehotTime = 0;
        // setting request
        OkHttpClient client = new OkHttpClient();
        Log.v("request url", String.format(Locale.ENGLISH, BASE_URL, mMaxBehotTime, CATEGORY_ATTR[mIndex]));
        Request request = new Request.Builder()
                .get()
                .url(String.format(Locale.ENGLISH, BASE_URL, mMaxBehotTime, CATEGORY_ATTR[mIndex]))
                .addHeader("User-Agent", getUserAgent())
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                Log.v("json status", " " + response.code());
                Log.v("json body", " " + (response.body() != null));
                String jsonData = response.body().string();
                // deal with request body
                dealWithResponseBody(jsonData);
                mCookie = response.header("Set-Cookie");
                Log.v("cookie", "set cookie: " + mCookie);
            }
        });
    }

    /**
     * a methods to refresh card list
     *
     * @throws IOException
     * @throws JSONException
     */
    public void refreshNews() throws IOException, JSONException {
        // init data
        mIsRefresh = true;
        mNewsDataModelList.clear();
        mCardDataModelList.clear();
        mMaxBehotTime = 0;
        // setting request
        OkHttpClient client = new OkHttpClient();
        Log.v("request url", String.format(Locale.ENGLISH, BASE_URL, mMaxBehotTime, CATEGORY_ATTR[mIndex]));
        Request request = new Request.Builder()
                .get()
                .url(String.format(Locale.ENGLISH, BASE_URL, mMaxBehotTime, CATEGORY_ATTR[mIndex]))
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Cookie", mCookie)
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                Log.v("json status", " " + response.code());
                Log.v("json body", " " + (response.body() != null));
                String jsonData = response.body().string();
                dealWithResponseBody(jsonData);
                mCookie = response.header("Set-Cookie");
                Log.v("cookie", "set cookie: " + mCookie);
            }
        });
    }

    /**
     * a methods to load more card list items
     *
     * @throws IOException
     * @throws JSONException
     */
    public void loadMoreNews() throws IOException, JSONException {
        // init data
        mIsLoadMore = true;
        mNewsDataModelList.clear();
        //setting request
        OkHttpClient client = new OkHttpClient();
        Log.v("request url", String.format(Locale.ENGLISH, BASE_URL, mMaxBehotTime, CATEGORY_ATTR[mIndex]));
        Request request = new Request.Builder()
                .get()
                .url(String.format(Locale.ENGLISH, BASE_URL, mMaxBehotTime, CATEGORY_ATTR[mIndex]))
                .addHeader("User-Agent", getUserAgent())
                .addHeader("Cookie", mCookie)
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                Log.v("json status", " " + response.code());
                Log.v("json body", " " + (response.body() != null));
                String jsonData = response.body().string();
                dealWithResponseBody(jsonData);
                mCookie = response.header("Set-Cookie");
                Log.v("cookie", "set cookie: " + mCookie);
            }
        });
    }

    /**
     * @param jsonData
     */
    public void dealWithResponseBody(String jsonData) {
        // avoid that jsonData is null
        if(jsonData.length() < 1) {
            mIsLoadingFail = true;
            // run on main ui thread
            runThread();
            return;
        }
        Log.v("deal with response", "string to JsonObject");
        Log.v("deal with response", "json data\n" + jsonData);
        JsonObject result = JsonParser.parseString(jsonData).getAsJsonObject();
        Log.v("deal with response", "result, before max_behot_time " + mMaxBehotTime);
        Log.v("deal with response", String.valueOf(result.getAsJsonObject("next").has("max_behot_time")));
        mMaxBehotTime = result.getAsJsonObject("next").get("max_behot_time").getAsInt();
        Log.v("deal with response", "After max_behot_time : " + mMaxBehotTime + " get news object");
        JsonArray newsData = result.getAsJsonArray("data");
        for (int i = 0; i < newsData.size(); i++) {
            Log.v("deal with response", "newsObjects " + i);
            try {
                dealWithNewsObject(newsData.get(i).getAsJsonObject());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        runThread();
    }

    /**
     * running on main UI thread to render card list
     */
    private void runThread() {
        Handler handler = new Handler(Looper.getMainLooper());

        new Thread() {
            public void run() {
                if(mIsLoadMore) {
                    // load more
                    handler.post(() -> loadMoreRenderCardList());
                } else if(mIsLoadingFail) {
                    // fail
                    handler.post(() -> loadingFail());
                } else {
                    // init & refresh
                    handler.post(() -> initRenderCardList());
                }
            }
        }.start();
    }

    /**
     * a methods to transfer JsonObject to NewsDataModel
     *
     * @param object
     * @throws JSONException
     */
    public void dealWithNewsObject(JsonObject object) throws JSONException {
        NewsDataModel temp;
        // id
        Log.v("deal with news object", "news_id " + object.get("group_id").getAsString());
        String newsId = object.get("group_id").getAsString();
        // title
        Log.v("deal with news object", "news_title " + object.get("title").getAsString());
        String newsTitle = object.get("title").getAsString();
        // remove \r \n \t
        newsTitle = newsTitle.replaceAll("\r|\n|\t", "");
        // abstract
        String newsAbstract = newsTitle;
        Log.v("deal with news object", "news_abstract " + object.has("abstract"));
        if (object.has("abstract")) {
            newsAbstract = object.get("abstract").getAsString();
            // remove \r \n \t
            newsAbstract = newsAbstract.replaceAll("\r|\n|\t", "");
        }
        // comments count
        Log.v("deal with news object", "news_comments_count " + object.has("comments_count"));
        int newsCommentsCount = 0;
        if (object.has("comments_count")) {
            newsCommentsCount = object.get("comments_count").getAsInt();
        }
        // news source
        Log.v("deal with news object", "news_source " + object.get("source").getAsString());
        String newsSource = object.get("source").getAsString();
        // news media avatar url
        String newsMediaAvatarUrl = DEFAULT_AVATAR;
        if (object.has("media_avatar_url")) {
            newsMediaAvatarUrl = "https:" + object.get("media_avatar_url").getAsString();
        }
        // news source url
        Log.v("deal with news object", "news_source_url " + object.get("source_url").getAsString());
        String newsSourceUrl = object.get("source_url").getAsString();

        Log.v("deal with news object", "have image_list " + object.has("image_list"));
        Log.v("deal with news object", "single_mode " + object.get("single_mode").getAsBoolean());
        // three image style
        if (object.has("image_list")) {
            JsonArray imageList = object.get("image_list").getAsJsonArray();
            ArrayList<String> newsThreeImage = new ArrayList<>();

            // the Json Array is not null
            if(imageList.size() < 3) {
                for (int i = 0; i < 3; i++) {
                    // avoid url is null
                    newsThreeImage.add(DEFAULT_IMAGE);
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    // avoid url is null
                    String url = imageList.get(i).getAsJsonObject().get("url").getAsString();
                    if(url.length() == 0) {
                        url = DEFAULT_IMAGE;
                    } else {
                        url = "https:" + url;
                    }
                    newsThreeImage.add(url);
                }
            }
            temp = new NewsDataModel(
                    NewsDataModel.THREE_IMAGE_TYPE,
                    newsId,
                    newsTitle,
                    newsAbstract,
                    newsCommentsCount,
                    newsSource,
                    newsMediaAvatarUrl,
                    newsSourceUrl,
                    newsThreeImage
            );
        }
        // one image style
        else if (object.get("single_mode").getAsBoolean()) {
            String middleImage = "https:" + object.get("image_url").getAsString();

            temp = new NewsDataModel(
                    NewsDataModel.ONE_IMAGE_TYPE,
                    newsId,
                    newsTitle,
                    newsAbstract,
                    newsCommentsCount,
                    newsSource,
                    newsMediaAvatarUrl,
                    newsSourceUrl,
                    middleImage
            );
        }
        // no image style
        else {
            temp = new NewsDataModel(
                    NewsDataModel.NO_IMAGE_TYPE,
                    newsId,
                    newsTitle,
                    newsAbstract,
                    newsCommentsCount,
                    newsSource,
                    newsMediaAvatarUrl,
                    newsSourceUrl
            );
        }
        mNewsDataModelList.add(temp);
    }

    public boolean isInternetConnection()
    {
        boolean connected = false;
        ConnectivityManager connectivityManager =
                (ConnectivityManager)getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        return  connected;
    }

}