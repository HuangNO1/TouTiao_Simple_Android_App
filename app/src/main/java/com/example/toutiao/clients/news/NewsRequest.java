package com.example.toutiao.clients.news;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.example.toutiao.models.news.NewsDataModel;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Cache;
import okhttp3.ResponseBody;

import static com.example.toutiao.models.news.NewsDataModel.NO_IMAGE_TYPE;
import static com.example.toutiao.models.news.NewsDataModel.ONE_IMAGE_TYPE;
import static com.example.toutiao.models.news.NewsDataModel.THREE_IMAGE_TYPE;

public class NewsRequest {
    int max_behot_time;
    String category;
    final String base_url = "https://www.toutiao.com/api/pc/feed/?max_behot_time=%d&category=%s";
    Context mContext;

    public NewsRequest(String category, Context mContext) {
        max_behot_time = 0;
        this.category = category;
        this.mContext = mContext;
    }

    public ArrayList<NewsDataModel> getInitNews() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(String.format(Locale.ENGLISH, base_url, max_behot_time, category))
                .build();
        Call call = client.newCall(request);
        Response response;

        ArrayList<NewsDataModel> newsDataModelList = new ArrayList<>();
        try {

            response = call.execute();
            if (response.isSuccessful()) {
                JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                this.max_behot_time = jsonObject.getInt("max_behot_time");
                JSONArray newsObjects = jsonObject.getJSONArray("data");

                for (int i = 0; i < newsObjects.length(); ++i) {
                    newsDataModelList.add(dealWithNewsObject(newsObjects.getJSONObject(i)));
                }
                this.max_behot_time = jsonObject.getInt("max_behot_time");
            }
        } catch (IOException | JSONException e) {
            Log.e("error", "onFailure: " + e.toString());
        }
        return newsDataModelList;
    }

    public void getNextNews() {

    }

    public NewsDataModel dealWithNewsObject(JSONObject object) throws JSONException, IOException {
        NewsDataModel temp;

        String news_id = object.getString("group_id");
        String news_title = object.getString("title");
        String news_abstract = object.getString("abstract");
        int news_comments_count = object.getInt("comments_count");
        String news_source = object.getString("source");
        Bitmap news_media_avatar_url = cacheImage("http:" + object.getString("media_avatar_url"));
        String news_source_url = object.getString("source_url");

        // three image style
        if (object.has("image_list")) {
            JSONArray image_list = object.getJSONArray("image_list");
            ArrayList<Bitmap> news_three_image = new ArrayList<>();
            for (int i = 0; i < image_list.length(); ++i) {
                news_three_image.add(cacheImage("http:" + image_list.getJSONObject(i).getString("url")));
            }
            temp = new NewsDataModel(
                    THREE_IMAGE_TYPE,
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
            Bitmap middle_image = cacheImage(object.getString("middle_image"));

            temp = new NewsDataModel(
                    ONE_IMAGE_TYPE,
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
                    NO_IMAGE_TYPE,
                    news_id,
                    news_title,
                    news_abstract,
                    news_comments_count,
                    news_source,
                    news_media_avatar_url,
                    news_source_url
            );
        }

        return temp;
    }

    public Bitmap cacheImage(String image_url) throws IOException {
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        File cacheDirectory = new File(mContext.getCacheDir(), "http");
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
        } else {
            Log.d("error", "Retrofit onResponse(): CODE = [" + response.code() + "], MESSAGE = [" + response.message() + "]");
        }
        return bitmap;

//        client.newCall(request).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                ResponseBody body = response.body();
//                if (response.isSuccessful() && body != null) {
//                    InputStream is = body.byteStream();
//                    bitmap[0] = BitmapFactory.decodeStream(is);
//
//                } else {
//                    Log.d("error", "Retrofit onResponse(): CODE = [" + response.code() + "], MESSAGE = [" + response.message() + "]");
//                }
//            }
//        });
    }

}
