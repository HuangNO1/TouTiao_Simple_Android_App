package com.example.toutiao.ui.card.cardTest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toutiao.R;

import java.util.List;

public class CardTestAdapter extends RecyclerView.Adapter<CardTestAdapter.MyViewHolder>{
    private final List<DataModel> mDataModelList;
    private final Context mContext;

    // View holder class whose objects represent each list item

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView mCardImageView;
        public TextView mTitleTextView;
        public TextView mSubTitleTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            mCardImageView = itemView.findViewById(R.id.image_view_image);
            mTitleTextView = itemView.findViewById(R.id.text_view_card_title);
            mSubTitleTextView = itemView.findViewById(R.id.text_view_card_subtitle);
        }

        public void bindData(DataModel dataModel, Context context) {
            mCardImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.rem_blog));
            mTitleTextView.setText(dataModel.getTitle());
            mSubTitleTextView.setText(dataModel.getSubTitle());
        }
    }
    public CardTestAdapter(List<DataModel> modelList, Context context) {
        mDataModelList = modelList;
        mContext = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate out card list item

        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.news_card_item_test, parent, false);
        // Return a new view holder

        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Bind data for the item at position

        holder.bindData(mDataModelList.get(position), mContext);
    }

    @Override
    public int getItemCount() {
        // Return the total number of items

        return mDataModelList.size();
    }
}
