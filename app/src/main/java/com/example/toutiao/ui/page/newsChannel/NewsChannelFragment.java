package com.example.toutiao.ui.page.newsChannel;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.toutiao.R;
import com.example.toutiao.models.news.NewsDataModel;
import com.example.toutiao.ui.card.cardList.CardAdapter;
import com.example.toutiao.ui.card.cardList.CardItemDataModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.scwang.smart.refresh.footer.BallPulseFooter;
import com.scwang.smart.refresh.header.BezierRadarHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.toutiao.ui.card.cardList.CardItemDataModel.NO_IMAGE_TYPE;
import static com.example.toutiao.ui.card.cardList.CardItemDataModel.ONE_IMAGE_TYPE;
import static com.example.toutiao.ui.card.cardList.CardItemDataModel.THREE_IMAGE_TYPE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsChannelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsChannelFragment extends Fragment {
    private final static String BASE_URL = "https://www.toutiao.com/api/pc/feed/?max_behot_time=%d&category=%s";
    private static final String[] CATEGORY_ATTR = new String[]{
            "__all__",
            "news_tech",
            "news_hot",
            "news_image",
            "news_entertainment",
            "news_game",
            "news_sports",
            "news_finance",
            "digital"
    };
    private final static String DEFAULT_AVATAR = "https://img.88icon.com/download/jpg/20200901/84083236c883964781afea41f1ea4e9c_512_511.jpg!88bg";
    ArrayList<NewsDataModel> mNewsDataModelList = new ArrayList<>();
    private PageViewModel mPageViewModel;
    private RecyclerView mCardListRecyclerView;
    private CardAdapter mCardListAdapter;
    private RecyclerView.LayoutManager mCardListLayoutManager;
    private LottieAnimationView mLoadingAnimationView;
    private RefreshLayout mCardListRefreshLayout;
    private View mScreenMaskView;
    private final List<CardItemDataModel> mCardDataModelList = new ArrayList<>();
    private String mCategory;
    private int mIndex;
    private boolean mIsRefresh;
    private boolean mIsLoadMore;
    private int mMaxBehotTime = 0;
    private String mCookie;
    private JsonObject mResult;
    private JsonArray mNewsData;
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
        mIsLoadMore = false;
        mIsRefresh = false;

        mScreenMaskView = view.findViewById(R.id.view_screen_mask);
        mScreenMaskView.setVisibility(View.GONE);

        mLoadingAnimationView = view.findViewById(R.id.animation_view_loading);
        mLoadingAnimationView.setAnimation("load-animation.json");
        mLoadingAnimationView.setSpeed(1);
        mLoadingAnimationView.playAnimation();
        // cardList
        mCardListRecyclerView = view.findViewById(R.id.recycler_view_card_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mCardListRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mCardListLayoutManager = new LinearLayoutManager(container.getContext());
        mCardListRecyclerView.setLayoutManager(mCardListLayoutManager);

        // specify an adapter and pass in our data model list
        mCardListAdapter = new CardAdapter(mCardDataModelList, container.getContext());
        mCardListRecyclerView.setAdapter(mCardListAdapter);

        mCardListRefreshLayout = view.findViewById(R.id.refresh_layout_card_list);
        mCardListRefreshLayout.setRefreshHeader(new BezierRadarHeader(container.getContext()));
        mCardListRefreshLayout.setRefreshFooter(new BallPulseFooter(container.getContext()));
        mCardListRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NotNull RefreshLayout refreshlayout) {
                try {
                    refreshNews();
                    mScreenMaskView.setVisibility(View.VISIBLE);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                // refreshlayout.finishRefresh(true/*,false*/);
                mCardListRefreshLayout.autoRefresh(); //传入false表示刷新失败
            }
        });
        mCardListRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NotNull RefreshLayout refreshlayout) {
                try {
                    loadMoreNews();
                    mScreenMaskView.setVisibility(View.VISIBLE);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                //refreshlayout.finishLoadMore(true/*,false*/);
                mCardListRefreshLayout.autoLoadMore(); //传入false表示加载失败
            }
        });

        TextView mSectionLabelTextView = view.findViewById(R.id.text_view_section_label);
        try {
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

    // render the recycler view card list
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
                mCardDataModelList.add(new CardItemDataModel(
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
                mCardDataModelList.add(new CardItemDataModel(
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
                mCardDataModelList.add(new CardItemDataModel(
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
        mCardListRefreshLayout.finishRefresh();
        mCardListAdapter = new CardAdapter(mCardDataModelList, getContext());
        mCardListRecyclerView.setAdapter(mCardListAdapter);

        mIsRefresh = false;
    }

    private void loadMoreRenderCardList() {
        Log.v("start more render", "render more card, news list size: " + mNewsDataModelList.size());

        List<CardItemDataModel> tempCardDataModelList = new ArrayList<>();

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
                tempCardDataModelList.add(new CardItemDataModel(
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
                tempCardDataModelList.add(new CardItemDataModel(
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
                tempCardDataModelList.add(new CardItemDataModel(
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
        mCardListRefreshLayout.finishLoadMore();
        mCardListAdapter.setDataModelList(tempCardDataModelList);
        Log.v("after load more", "card list size: " + mCardListAdapter.getItemCount());
        mIsLoadMore = false;
        mCardListAdapter.notifyDataSetChanged();
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
        mNewsDataModelList.clear();
        mCardDataModelList.clear();
        mLoadingAnimationView.setVisibility(View.VISIBLE);
        mMaxBehotTime = 0;
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
                String jsonData = response.body().string();
                responseBody(jsonData);
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
        mIsRefresh = true;
        mNewsDataModelList.clear();
        mCardDataModelList.clear();
        mMaxBehotTime = 0;
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
                String jsonData = Objects.requireNonNull(response.body()).string();
                responseBody(jsonData);
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
        mIsLoadMore = true;
        mNewsDataModelList.clear();
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
                String jsonData = Objects.requireNonNull(response.body()).string();
                responseBody(jsonData);
                mCookie = response.header("Set-Cookie");
                Log.v("cookie", "set cookie: " + mCookie);
            }
        });
    }

    /**
     * @param jsonData
     */
    public void responseBody(String jsonData) {
        Log.v("deal with response", "string to JsonObject");
        mResult = JsonParser.parseString(jsonData).getAsJsonObject();
        Log.v("deal with response", "result, before max_behot_time " + mMaxBehotTime);
        Log.v("deal with response", String.valueOf(mResult.getAsJsonObject("next").has("max_behot_time")));
        mMaxBehotTime = mResult.getAsJsonObject("next").get("max_behot_time").getAsInt();
        Log.v("deal with response", "After max_behot_time : " + mMaxBehotTime + " get news object");
        mNewsData = mResult.getAsJsonArray("data");
        for (int i = 0; i < mNewsData.size(); i++) {
            Log.v("deal with response", "newsObjects " + i);
            try {
                dealWithNewsObject(mNewsData.get(i).getAsJsonObject());
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
                    handler.post(() -> loadMoreRenderCardList());
                } else {
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
        Log.v("deal with news object", "news_id " + object.get("group_id").getAsString());
        String newsId = object.get("group_id").getAsString();
        Log.v("deal with news object", "news_title " + object.get("title").getAsString());
        String newsTitle = object.get("title").getAsString();
        String newsAbstract = newsTitle;
        Log.v("deal with news object", "news_abstract " + object.has("abstract"));
        if (object.has("abstract")) {
            newsAbstract = object.get("abstract").getAsString();
        }
        Log.v("deal with news object", "news_comments_count " + object.has("comments_count"));
        int newsCommentsCount = 0;
        if (object.has("comments_count")) {
            newsCommentsCount = object.get("comments_count").getAsInt();
        }
        Log.v("deal with news object", "news_source " + object.get("source").getAsString());
        String newsSource = object.get("source").getAsString();
        String newsMediaAvatarUrl = DEFAULT_AVATAR;
        if (object.has("media_avatar_url")) {
            newsMediaAvatarUrl = "https:" + object.get("media_avatar_url").getAsString();
        }
        Log.v("deal with news object", "news_source_url " + object.get("source_url").getAsString());
        String newsSourceUrl = object.get("source_url").getAsString();

        Log.v("deal with news object", "have image_list " + object.has("image_list"));
        Log.v("deal with news object", "single_mode " + object.get("single_mode").getAsBoolean());
        // three image style
        if (object.has("image_list")) {
            JsonArray imageList = object.get("image_list").getAsJsonArray();
            ArrayList<String> newsThreeImage = new ArrayList<>();
            for (int i = 0; i < imageList.size(); i++) {
                newsThreeImage.add("https:" + imageList.get(i).getAsJsonObject().get("url").getAsString());
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

}