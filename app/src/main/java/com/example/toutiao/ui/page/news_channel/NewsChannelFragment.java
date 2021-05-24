package com.example.toutiao.ui.page.news_channel;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.example.toutiao.R;
import com.example.toutiao.ui.card.card_test.CardTestAdapter;
import com.example.toutiao.ui.card.card_test.DataModel;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsChannelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsChannelFragment extends Fragment {

    private static final String ARG_SECTION_NUMBER = "section_number";
    private PageViewModel pageViewModel;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    public NewsChannelFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    // TODO: Rename and change types and number of parameters
    public static NewsChannelFragment newInstance(int index) {
        NewsChannelFragment fragment = new NewsChannelFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(ARG_SECTION_NUMBER, index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        pageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        int index = 1;
        if (getArguments() != null) {
            index = getArguments().getInt(ARG_SECTION_NUMBER);
        }
        pageViewModel.setIndex(index);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_channel, container, false);

        TextView textView = (TextView)view.findViewById(R.id.section_label);
        pageViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        renderCardList(view, container);

        // Inflate the layout for this fragment
        return view;
    }

    // render the recycler view card list
    public void renderCardList(View view, ViewGroup container) {
        // cardList
        mRecyclerView = view.findViewById(R.id.recycler_view);

        List<DataModel> dataModelList = new ArrayList<>();
        for (int i = 1; i <= 20; ++i) {
            dataModelList.add(new DataModel(i));
        }

        // use this setting to improve performance if you know that changes

        // in content do not change the layout size of the RecyclerView

        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager

        mLayoutManager = new LinearLayoutManager(container.getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

//        mRecyclerView.setNestedScrollingEnabled(true);

        // specify an adapter and pass in our data model list

        mAdapter = new CardTestAdapter(dataModelList, container.getContext());
        mRecyclerView.setAdapter(mAdapter);
    }
}