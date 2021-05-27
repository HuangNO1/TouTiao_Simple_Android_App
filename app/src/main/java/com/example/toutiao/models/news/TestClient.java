package com.example.toutiao.models.news;

import android.content.Context;
import android.util.Log;

import com.example.toutiao.clients.news.NewsRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Locale;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class TestClient {
    public static void main(String[] args) {
        String base_url = "https://www.toutiao.com/api/pc/feed/?max_behot_time=%d&category=%s";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(String.format(Locale.ENGLISH, base_url, 0, "__all__"))
                .build();
        Call call = client.newCall(request);
        Response response;

        ArrayList<NewsDataModel> newsDataModelList = new ArrayList<>();

        try {
            response = call.execute();
            if (response.isSuccessful()) {
                JSONObject jsonObject = new JSONObject(new Gson().toJson(response.body()));
                System.out.println(jsonObject.toString(4));

            }
        } catch (IOException | JSONException e) {
            Log.e("error", "onFailure: " + e.toString());
        }

    }
}
