package com.example.toutiao.ui.page.news_channel;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.toutiao.R;
import com.example.toutiao.clients.news.NewsRequest;
import com.example.toutiao.models.news.NewsDataModel;
import com.example.toutiao.ui.card.card_list.CardAdapter;
import com.example.toutiao.ui.card.card_list.CardItemDataModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

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

    private List<CardItemDataModel> dataModelList = new ArrayList<>();
    private String category;
    private int index;
    static public ArrayList<Boolean> isRendered;

    public NewsChannelFragment() {
        // Required empty public constructor
        isRendered = new ArrayList<>(9);
        for(int i = 0; i < isRendered.size(); ++i) {
            isRendered.set(i, false);
        }
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

        TextView textView = (TextView)view.findViewById(R.id.section_label);
        new GetNewsItemTask().execute();

        pageViewModel.getCategory().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
                new GetNewsItemTask().execute();
            }
        });
        // Inflate the layout for this fragment
        return view;
    }

    // render the recycler view card list
    public void renderCardList() {
        for (int i = 0; i < newsDataModelList.size(); ++i) {
            int type = newsDataModelList.get(i).getNews_card_style_type();
            String news_id = newsDataModelList.get(i).getNews_id();
            String news_title = newsDataModelList.get(i).getNews_title();
            String news_abstract = newsDataModelList.get(i).getNews_abstract();
            int news_comments_count = newsDataModelList.get(i).getNews_comments_count();
            String news_source = newsDataModelList.get(i).getNews_source();
            Bitmap news_media_avatar_url = newsDataModelList.get(i).getNews_media_avatar_url();
            String news_source_url = newsDataModelList.get(i).getNews_source_url();

            if(type == NO_IMAGE_TYPE) {
                dataModelList.add(new CardItemDataModel(
                        ONE_IMAGE_TYPE,
                        news_id,
                        news_title,
                        news_abstract,
                        news_comments_count,
                        news_source,
                        news_media_avatar_url,
                        news_source_url
                ));
            } else if (type == ONE_IMAGE_TYPE) {
                Bitmap middle_image = newsDataModelList.get(i).getNews_image_url();
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
                ArrayList<Bitmap> news_three_image = newsDataModelList.get(i).getNews_three_image();
                dataModelList.add(new CardItemDataModel(
                        ONE_IMAGE_TYPE,
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
    }

    final String base_url = "https://www.toutiao.com/api/pc/feed/?max_behot_time=%d&category=%s";
    int max_behot_time = 0;
    public JSONObject result;
    ArrayList<NewsDataModel> newsDataModelList = new ArrayList<>();
    private static final String[] category_attr = new String[] {
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

    public void getInitNews() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(String.format(Locale.ENGLISH, base_url, max_behot_time, category_attr[index]))
                .build();
        Call call = client.newCall(request);
        Response response;

        try {
            response = call.execute();
            if (response.isSuccessful()) {
                System.out.println("response body");
                System.out.println(response.body().string());
                String jsonData = response.body().string();
                result = new JSONObject(jsonData);
                System.out.println("result");
                System.out.println(result.toString(2));
                max_behot_time = result.getInt("max_behot_time");
                JSONArray newsObjects = result.getJSONArray("data");
                for (int i = 0; i < newsObjects.length(); ++i) {
                    dealWithNewsObject(newsObjects.getJSONObject(i));
                }
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    public void dealWithNewsObject(JSONObject object) throws JSONException {
        NewsDataModel temp;

        String news_id = object.getString("group_id");
        String news_title = object.getString("title");
        String news_abstract = object.getString("abstract");
        int news_comments_count = object.getInt("comments_count");
        String news_source = object.getString("source");
        Bitmap news_media_avatar_url = null;
        String news_source_url = object.getString("source_url");

        // three image style
        if (object.has("image_list")) {
            JSONArray image_list = object.getJSONArray("image_list");
            ArrayList<Bitmap> news_three_image = new ArrayList<>();
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
        else if (object.getBoolean("single_mode")) {
            Bitmap middle_image = null;

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

    public void cacheImage(String image_url, int index, int type) throws IOException {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File cacheDirectory = new File(getContext().getCacheDir(), "http");
        Cache cache = new Cache(cacheDirectory, cacheSize);
        OkHttpClient client = new OkHttpClient.Builder().cache(cache).build();
        Bitmap bitmap = null;

        Request request = new Request.Builder()
                .get()
                .url(String.format(Locale.ENGLISH, "http://%s", image_url))
                .build();
        Call call = client.newCall(request);
        Response response;
        response = call.execute();
        ResponseBody body = response.body();
        if (response.isSuccessful() && body != null) {
            InputStream is = body.byteStream();
            bitmap = BitmapFactory.decodeStream(is);
            switch(type) {
                case 0:
                    newsDataModelList.get(index).setNews_media_avatar_url(bitmap);
                    break;
                case 1:
                    newsDataModelList.get(index).setNews_image_url(bitmap);
                    break;
                case 2:
                    newsDataModelList.get(index).setNews_three_image(bitmap);
                    break;
                default:
                    break;
            }
        }
    }

    public class GetNewsItemTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                getInitNews();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            new getImagesTask().execute();
        }
    }

    public class getImagesTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                JSONArray newsObjects = result.getJSONArray("data");
                for (int i = 0; i < newsObjects.length(); ++i) {
                    JSONObject object = newsObjects.getJSONObject(i);
                    cacheImage("http:" + object.getString("media_avatar_url"), i, 0);
                    if (object.has("image_list")) {
                        JSONArray image_list = object.getJSONArray("image_list");
                        for(int j = 0; j < image_list.length(); ++j) {
                            cacheImage("http:" + image_list.getJSONObject(i).getString("url"), i, 2);
                        }
                    }
                    // one image style
                    else if (object.getBoolean("single_mode")) {
                        cacheImage("http:" + object.getString("media_avatar_url"), i, 1);
                    }
                }
            } catch (JSONException | IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            renderCardList();
        }
    }

}