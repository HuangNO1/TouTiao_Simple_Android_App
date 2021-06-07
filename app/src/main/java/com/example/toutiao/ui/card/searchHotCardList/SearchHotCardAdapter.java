package com.example.toutiao.ui.card.searchHotCardList;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toutiao.R;
import com.example.toutiao.activity.NewsDetailActivity;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SearchHotCardAdapter extends RecyclerView.Adapter<SearchHotCardAdapter.SearchHotCardHolder> {

    private final List<SearchHotCardItemDataModel> mDataModelList;
    private final Context mContext;

    public SearchHotCardAdapter(List<SearchHotCardItemDataModel> modelList, Context context) {
        mDataModelList = modelList;
        mContext = context;
    }

    /**
     * Click the card item and move to NewsDetailActivity
     *
     * @param itemView
     */
    public void onClickListener(@NonNull View itemView) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) mContext;
                TextView mSourceUrl = itemView.findViewById(R.id.text_view_source_url);
                Intent intent = new Intent(activity, NewsDetailActivity.class);
                intent.putExtra("source_url", mSourceUrl.getText().toString());
                mContext.startActivity(intent);
                activity.overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
            }
        });
    }

    @NonNull
    @NotNull
    @Override
    public SearchHotCardHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        // Inflate out card list item
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.search_hot_card_item, parent, false);
        // Return a new view holder
        return new SearchHotCardHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull SearchHotCardHolder holder, int position) {
        // Bind data for the item at position
        holder.bindData(mDataModelList.get(position), mContext);

        // TODO: Make TOP3 item text be red
    }

    @Override
    public int getItemCount() {
        // Return the total number of items
        return mDataModelList.size();
    }

    // View holder class whose objects represent each list item
    public class SearchHotCardHolder extends RecyclerView.ViewHolder {
        private final TextView mKeyTextView;
        private final TextView mTitleTextView;
        private final TextView mSourceUrlTextView;

        public SearchHotCardHolder(@NonNull @NotNull View itemView) {
            super(itemView);
            mKeyTextView = itemView.findViewById(R.id.text_view_key);
            mTitleTextView = itemView.findViewById(R.id.text_view_card_title);
            mSourceUrlTextView = itemView.findViewById(R.id.text_view_source_url);

            onClickListener(itemView);
        }

        @SuppressLint({"SetTextI18n"})
        public void bindData(SearchHotCardItemDataModel dataModel, Context context) {
            int key = dataModel.getKey();
            mKeyTextView.setText(Integer.toString(key));
            mTitleTextView.setText(dataModel.getTitle());
            mSourceUrlTextView.setText(dataModel.getDetailUrl());
        }
    }
}
