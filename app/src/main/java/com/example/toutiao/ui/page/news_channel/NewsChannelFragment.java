package com.example.toutiao.ui.page.news_channel;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.example.toutiao.ui.card.card_list.CardAdapter;
import com.example.toutiao.ui.card.card_list.CardItemDataModel;
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

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.toutiao.ui.card.card_list.CardItemDataModel.NO_IMAGE_TYPE;
import static com.example.toutiao.ui.card.card_list.CardItemDataModel.ONE_IMAGE_TYPE;
import static com.example.toutiao.ui.card.card_list.CardItemDataModel.THREE_IMAGE_TYPE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsChannelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsChannelFragment extends Fragment {
    private PageViewModel pageViewModel;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    private LottieAnimationView animationView;
    //    private SwipeRefreshLayout swipeContainer;
    private RefreshLayout refreshLayout;
    //    private CustomViewPager customViewPager;
    private ImageView mScreenMask;

    private List<CardItemDataModel> dataModelList = new ArrayList<>();
    private String category;
    private int index;
    private boolean isRefresh;
    private boolean isLoadMore;

    public NewsChannelFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
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
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        if (getArguments() != null) {
            category = getArguments().getString("category");
            index = getArguments().getInt("index");
        }
        pageViewModel.setCategory(category);
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_channel, container, false);
        isLoadMore = false;
        isRefresh = false;

//        customViewPager = view.findViewById(R.id.custom_viewPager);
//        View thisFragment = view.findViewById(R.id.news_channel_fragment);
//        thisFragment.setClickable(false);
//        view.setOnTouchListener();
        mScreenMask = view.findViewById(R.id.screen_mask);
        mScreenMask.setVisibility(View.GONE);

        animationView = view.findViewById(R.id.animation_view);
        animationView.setAnimation("load-animation.json");
        animationView.setSpeed(1);
        animationView.playAnimation();
        // cardList
        mRecyclerView = view.findViewById(R.id.recycler_view);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter and pass in our data model list
        mAdapter = new CardAdapter(dataModelList, container.getContext());
        mRecyclerView.setAdapter(mAdapter);

        refreshLayout = view.findViewById(R.id.refreshLayout);
        refreshLayout.setRefreshHeader(new BezierRadarHeader(getContext()));
        refreshLayout.setRefreshFooter(new BallPulseFooter(getContext()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                try {
                    refreshNews();
                    mScreenMask.setVisibility(View.VISIBLE);
//                    customViewPager.setPagingEnabled(false);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                //refreshlayout.finishRefresh(true/*,false*/);//传入false表示刷新失败
                refreshLayout.autoRefresh();
            }
        });
        refreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(RefreshLayout refreshlayout) {
                try {
                    loadMoreNews();
                    mScreenMask.setVisibility(View.VISIBLE);
//                    customViewPager.setPagingEnabled(false);
                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                //refreshlayout.finishLoadMore(true/*,false*/);//传入false表示加载失败
                refreshLayout.autoLoadMore();
            }
        });

        TextView textView = (TextView) view.findViewById(R.id.section_label);
        try {
            getInitNews();
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }

        pageViewModel.getCategory().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        return view;
    }


    // render the recycler view card list
    public void renderCardList() {
        System.out.println("render card, news list size: " + newsDataModelList.size());
        for (int i = 0; i < newsDataModelList.size(); i++) {
            int type = newsDataModelList.get(i).getNews_card_style_type();
            String news_id = newsDataModelList.get(i).getNews_id();
            String news_title = newsDataModelList.get(i).getNews_title();
            String news_abstract = newsDataModelList.get(i).getNews_abstract();
            int news_comments_count = newsDataModelList.get(i).getNews_comments_count();
            String news_source = newsDataModelList.get(i).getNews_source();
            String news_media_avatar_url = newsDataModelList.get(i).getNews_media_avatar_url();
            String news_source_url = newsDataModelList.get(i).getNews_source_url();

            if (type == NO_IMAGE_TYPE) {
                dataModelList.add(new CardItemDataModel(
                        NO_IMAGE_TYPE,
                        news_id,
                        news_title,
                        news_abstract,
                        news_comments_count,
                        news_source,
                        news_media_avatar_url,
                        news_source_url
                ));
            } else if (type == ONE_IMAGE_TYPE) {
                String middle_image = newsDataModelList.get(i).getNews_image_url();
                dataModelList.add(new CardItemDataModel(
                        ONE_IMAGE_TYPE,
                        news_id,
                        news_title,
                        news_abstract,
                        news_comments_count,
                        news_source,
                        news_media_avatar_url,
                        news_source_url,
                        middle_image
                ));

            } else if (type == THREE_IMAGE_TYPE) {
                ArrayList<String> news_three_image = newsDataModelList.get(i).getNews_three_image();
                dataModelList.add(new CardItemDataModel(
                        THREE_IMAGE_TYPE,
                        news_id,
                        news_title,
                        news_abstract,
                        news_comments_count,
                        news_source,
                        news_media_avatar_url,
                        news_source_url,
                        news_three_image
                ));
            }
        }
        animationView.setVisibility(View.GONE);
        mScreenMask.setVisibility(View.GONE);
        refreshLayout.finishRefresh();
        refreshLayout.finishLoadMore(/*,false*/);
//        swipeContainer.setRefreshing(false);
//        mRecyclerView.setNestedScrollingEnabled(false);
//        // use a linear layout manager
//        mLayoutManager = new LinearLayoutManager(getContext());
//        mRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter and pass in our data model list
        int item = mAdapter.getItemCount();
        mAdapter = new CardAdapter(dataModelList, getContext());
        mRecyclerView.setAdapter(mAdapter);
//        mRecyclerView.smoothScrollToPosition(item);
        if (isLoadMore) {
            mLayoutManager.smoothScrollToPosition(mRecyclerView, new RecyclerView.State(), item - 1);
            isLoadMore = false;
        }
//        customViewPager.setPagingEnabled(true);
    }

    final String base_url = "https://www.toutiao.com/api/pc/feed/?max_behot_time=%d&category=%s";
    int max_behot_time = 0;
    public JsonObject result;
    public JsonArray news_data;
    ArrayList<NewsDataModel> newsDataModelList = new ArrayList<>();
    private static final String[] category_attr = new String[]{
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

    public void getInitNews() throws IOException, JSONException {
        newsDataModelList.clear();
        dataModelList.clear();
        animationView.setVisibility(View.VISIBLE);
        max_behot_time = 0;
        OkHttpClient client = new OkHttpClient();
        System.out.println(String.format(Locale.ENGLISH, base_url, max_behot_time, category_attr[index]));
        Request request = new Request.Builder()
                .get()
                .url(String.format(Locale.ENGLISH, base_url, max_behot_time, category_attr[index]))
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
//                System.out.println("response body");
                String jsonData = response.body().string();
                responseBody(jsonData);
            }
        });
    }

    public void refreshNews() throws IOException, JSONException {
        isRefresh = true;
        newsDataModelList.clear();
        dataModelList.clear();
//        animationView.setVisibility(View.VISIBLE);
        max_behot_time = 0;
        OkHttpClient client = new OkHttpClient();
        System.out.println(String.format(Locale.ENGLISH, base_url, max_behot_time, category_attr[index]));
        Request request = new Request.Builder()
                .get()
                .url(String.format(Locale.ENGLISH, base_url, max_behot_time, category_attr[index]))
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
//                System.out.println("response body");
                String jsonData = response.body().string();
                responseBody(jsonData);
            }
        });
    }

    public void loadMoreNews() throws IOException, JSONException {
        isLoadMore = true;
        newsDataModelList.clear();
        OkHttpClient client = new OkHttpClient();
        System.out.println(String.format(Locale.ENGLISH, base_url, max_behot_time, category_attr[index]));
        Request request = new Request.Builder()
                .get()
                .url(String.format(Locale.ENGLISH, base_url, max_behot_time, category_attr[index]))
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
//                System.out.println("response body");
                String jsonData = response.body().string();
                responseBody(jsonData);
            }
        });
    }

    public void responseBody(String jsonData) {
        System.out.println("string to JsonObject");
        result = JsonParser.parseString(jsonData).getAsJsonObject();
        System.out.println("result, before max_behot_time " + max_behot_time);
        System.out.println(result.getAsJsonObject("next").has("max_behot_time"));
        max_behot_time = result.getAsJsonObject("next").get("max_behot_time").getAsInt();
        System.out.println("After max_behot_time : " + max_behot_time + " get news object");
        news_data = result.getAsJsonArray("data");
        for (int i = 0; i < news_data.size(); i++) {
            System.out.println("newsObjects " + i);
            try {
                dealWithNewsObject(news_data.get(i).getAsJsonObject());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        runThread();
    }

    private void runThread() {
        Handler handler = new Handler(Looper.getMainLooper());

        new Thread() {
            public void run() {
                handler.post(() -> renderCardList());
            }
        }.start();
    }


    public void dealWithNewsObject(JsonObject object) throws JSONException {
        NewsDataModel temp;
        System.out.println("news_id " + object.get("group_id").getAsString());
        String news_id = object.get("group_id").getAsString();
        System.out.println("news_title " + object.get("title").getAsString());
        String news_title = object.get("title").getAsString();
        String news_abstract = news_title;
        System.out.println("news_abstract " + object.has("abstract"));
        if (object.has("abstract")) {
            news_abstract = object.get("abstract").getAsString();
        }
        System.out.println("news_comments_count " + object.has("comments_count"));
        int news_comments_count = 0;
        if (object.has("comments_count")) {
            news_comments_count = object.get("comments_count").getAsInt();
        }
        System.out.println("news_source " + object.get("source").getAsString());
        String news_source = object.get("source").getAsString();
        String news_media_avatar_url = "https://img.88icon.com/download/jpg/20200901/84083236c883964781afea41f1ea4e9c_512_511.jpg!88bg";
        if (object.has("media_avatar_url")) {
            news_media_avatar_url = "https:" + object.get("media_avatar_url").getAsString();
        }
        System.out.println("news_source_url " + object.get("source_url").getAsString());
        String news_source_url = object.get("source_url").getAsString();

        System.out.println("have image_list " + object.has("image_list"));
        System.out.println("single_mode " + object.get("single_mode").getAsBoolean());
        // three image style
        if (object.has("image_list")) {
            JsonArray image_list = object.get("image_list").getAsJsonArray();
            ArrayList<String> news_three_image = new ArrayList<>();
            for (int i = 0; i < image_list.size(); i++) {
                news_three_image.add("https:" + image_list.get(i).getAsJsonObject().get("url").getAsString());
            }
            temp = new NewsDataModel(
                    NewsDataModel.THREE_IMAGE_TYPE,
                    news_id,
                    news_title,
                    news_abstract,
                    news_comments_count,
                    news_source,
                    news_media_avatar_url,
                    news_source_url,
                    news_three_image
            );
        }
        // one image style
        else if (object.get("single_mode").getAsBoolean()) {
            String middle_image = "https:" + object.get("image_url").getAsString();

            temp = new NewsDataModel(
                    NewsDataModel.ONE_IMAGE_TYPE,
                    news_id,
                    news_title,
                    news_abstract,
                    news_comments_count,
                    news_source,
                    news_media_avatar_url,
                    news_source_url,
                    middle_image
            );
        }
        // no image style
        else {
            temp = new NewsDataModel(
                    NewsDataModel.NO_IMAGE_TYPE,
                    news_id,
                    news_title,
                    news_abstract,
                    news_comments_count,
                    news_source,
                    news_media_avatar_url,
                    news_source_url
            );
        }
        newsDataModelList.add(temp);
    }

}