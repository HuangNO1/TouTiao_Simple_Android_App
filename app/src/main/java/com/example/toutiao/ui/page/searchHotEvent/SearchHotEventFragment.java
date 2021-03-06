package com.example.toutiao.ui.page.searchHotEvent;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toutiao.R;
import com.example.toutiao.models.search.SearchHotEventDataModel;
import com.example.toutiao.ui.card.searchHotCardList.SearchHotCardAdapter;
import com.example.toutiao.ui.card.searchHotCardList.SearchHotCardItemDataModel;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SearchHotEventFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SearchHotEventFragment extends Fragment {

    private static final String BASE_URL =
            "https://www.toutiao.com/hot-event/hot-board/?origin=toutiao_pc";
    private static final String RES_BODY_TAG = "deal with response";
    private static final String NEWS_OBJECT_TAG = "deal with news object";
    private static final int INIT_LIST = 0;
    private static final int LOAD_FAIL = 1;

    private RecyclerView mCardListRecyclerView;
    private SearchHotCardAdapter mCardListAdapter;
    private RecyclerView.LayoutManager mCardListLayoutManager;
    private final ArrayList<SearchHotEventDataModel> mNewsDataModelList = new ArrayList<>();
    private final List<SearchHotCardItemDataModel> mCardDataModelList = new ArrayList<>();
    private Boolean mIsLoadingFail = false;

    public SearchHotEventFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchHotEventFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchHotEventFragment newInstance(String param1, String param2) {
        SearchHotEventFragment fragment = new SearchHotEventFragment();
        Bundle args = new Bundle();
        // args.putString(ARG_PARAM1, param1);
        // args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    private final Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            if (msg.what == INIT_LIST) {
                initRenderCardList();
            } else if (msg.what == LOAD_FAIL) {
                loadingFail();
            }
            return false;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_hot_event, container, false);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mCardListRecyclerView = view.findViewById(R.id.recycler_view_card_list);
        mCardListRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mCardListLayoutManager = new LinearLayoutManager(container.getContext());
        mCardListRecyclerView.setLayoutManager(mCardListLayoutManager);

        // specify an adapter and pass in our data model list
        mCardListAdapter = new SearchHotCardAdapter(mCardDataModelList, container.getContext());
        mCardListRecyclerView.setAdapter(mCardListAdapter);

        getInitNews();

        return view;
    }

    /**
     * a methods to init card list
     */
    public void getInitNews() {
        // init data
        mNewsDataModelList.clear();
        mCardDataModelList.clear();
        // setting request
        OkHttpClient client = new OkHttpClient();
        Log.v("request url", BASE_URL);
        Request request = new Request.Builder()
                .get()
                .url(BASE_URL)
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
            }
        });
    }

    public void dealWithResponseBody(String jsonData) {
        // avoid that jsonData is null
        if (jsonData.length() < 1) {
            mIsLoadingFail = true;
            // run on main ui thread
            // runThread();
            mHandler.sendEmptyMessage(LOAD_FAIL);
            return;
        }

        Log.v(RES_BODY_TAG, "string to JsonObject");
        Log.v(RES_BODY_TAG, "json data\n" + jsonData);
        JsonObject result = JsonParser.parseString(jsonData).getAsJsonObject();
        JsonArray newsData = result.getAsJsonArray("data");
        for (int i = 0; i < newsData.size(); i++) {
            Log.v(RES_BODY_TAG, "newsObjects " + i);
            dealWithNewsObject(i, newsData.get(i).getAsJsonObject());
        }
        // runThread();
        mHandler.sendEmptyMessage(INIT_LIST);
    }

    /**
     * a methods to transfer JsonObject to NewsDataModel
     *
     * @param object
     */
    public void dealWithNewsObject(int key, JsonObject object) {
        SearchHotEventDataModel temp;
        // title
        Log.v(NEWS_OBJECT_TAG, "title " + object.get("Title").getAsString());
        String newsTitle = object.get("Title").getAsString();
        // news source url
        Log.v(NEWS_OBJECT_TAG, "Url " + object.get("Url").getAsString());
        String newsSourceUrl = object.get("Url").getAsString();

        temp = new SearchHotEventDataModel(key + 1, newsTitle, newsSourceUrl);
        mNewsDataModelList.add(temp);
    }

    /**
     * running on main UI thread to render card list
     */
//    private void runThread() {
//        Handler handler = new Handler(Looper.getMainLooper());
//
//        new Thread() {
//            public void run() {
//                if (mIsLoadingFail) {
//                    // fail
//                    handler.post(() -> loadingFail());
//                } else {
//                    // init
//                    handler.post(() -> initRenderCardList());
//                }
//            }
//        }.start();
//    }
    private void initRenderCardList() {
        Log.v("start init render", "render init card, news list size: " + mNewsDataModelList.size());
        for (int i = 0; i < mNewsDataModelList.size(); i++) {
            int key = mNewsDataModelList.get(i).getKey();
            String title = mNewsDataModelList.get(i).getTitle();
            String newsSourceUrl = mNewsDataModelList.get(i).getNewsSourceUrl();
            mCardDataModelList.add(new SearchHotCardItemDataModel(
                    key,
                    title,
                    newsSourceUrl
            ));
        }
        mCardListAdapter = new SearchHotCardAdapter(mCardDataModelList, getContext());
        mCardListRecyclerView.setAdapter(mCardListAdapter);
    }

    /**
     * when loading fail (request body is null), show toast
     */
    private void loadingFail() {
        Toast showFailToast = Toast.makeText(getActivity(), "????????????????????????", Toast.LENGTH_LONG);
        showFailToast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 200);
        showFailToast.show();
        mIsLoadingFail = false;
    }


}