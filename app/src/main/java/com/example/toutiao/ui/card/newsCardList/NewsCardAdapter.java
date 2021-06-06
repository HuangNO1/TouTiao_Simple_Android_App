package com.example.toutiao.ui.card.newsCardList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.toutiao.R;
import com.example.toutiao.activity.NewsDetailActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A card adapter to help perform to control card item's render in news channel fragment
 */

public class NewsCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<NewsCardItemDataModel> mDataModelList;
    private final Context mContext;

    // No image style card view holder
    class NoImageCardViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mAvatarView;
        private final TextView mTitleTextView;
        private final TextView mSubTitleTextView;
        private final TextView mBottomTextView;
        private final TextView mSourceUrlTextView;

        public NoImageCardViewHolder(@NonNull View itemView) {
            super(itemView);
            mAvatarView = itemView.findViewById(R.id.image_view_card_avatar);
            mTitleTextView = itemView.findViewById(R.id.text_view_card_title);
            mSubTitleTextView = itemView.findViewById(R.id.text_view_card_subtitle);
            mBottomTextView = itemView.findViewById(R.id.text_view_card_bottom_text);
            mSourceUrlTextView =  itemView.findViewById(R.id.text_view_source_url);

            onClickListener(itemView);
        }

        public void bindData(NewsCardItemDataModel dataModel, Context context) {
            Picasso.get().load(dataModel.getAvatar()).into(mAvatarView);
            // deal with title's length and subtitle's length
            String title = dataModel.getTitle();
            if(title.length() > 15) {
                title = title.substring(0, 16);
                title += "...";
            }
            mTitleTextView.setText(title);
            String subTitle = dataModel.getSubTitle();
            if(subTitle.length() > 70) {
                subTitle = subTitle.substring(0, 69);
                subTitle += "...";
            }
            mSubTitleTextView.setText(subTitle);
            mBottomTextView.setText(dataModel.getBottomText());
            mSourceUrlTextView.setText(dataModel.getDetailUrl());
        }
    }

    // One image style card view holder
    class OneImageCardViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mAvatarView;
        private final TextView mTitleTextView;
        private final TextView mSubTitleTextView;
        private final TextView mBottomTextView;
        private final TextView mSourceUrlTextView;
        private final ImageView mCardImageView;

        public OneImageCardViewHolder(@NonNull View itemView) {
            super(itemView);
            mAvatarView = itemView.findViewById(R.id.image_view_card_avatar);
            mTitleTextView = itemView.findViewById(R.id.text_view_card_title);
            mSubTitleTextView = itemView.findViewById(R.id.text_view_card_subtitle);
            mBottomTextView = itemView.findViewById(R.id.text_view_card_bottom_text);
            mSourceUrlTextView =  itemView.findViewById(R.id.text_view_source_url);
            mCardImageView =  itemView.findViewById(R.id.image_view_card_image);

            onClickListener(itemView);
        }

        public void bindData(NewsCardItemDataModel dataModel, Context context) {
            Picasso.get().load(dataModel.getAvatar()).into(mAvatarView);
            Picasso.get().load(dataModel.getImageDrawable()).into(mCardImageView);
            String title = dataModel.getTitle();
            // deal with title's length and subtitle's length
            if(title.length() > 15) {
                title = title.substring(0, 14);
                title += "...";
            }
            mTitleTextView.setText(title);
            String subTitle = dataModel.getSubTitle();
            if(subTitle.length() > 70) {
                subTitle = subTitle.substring(0, 69);
                subTitle += "...";
            }
            mSubTitleTextView.setText(subTitle);
            mBottomTextView.setText(dataModel.getBottomText());
            mSourceUrlTextView.setText(dataModel.getDetailUrl());
        }
    }

    // Three image style card view holder
    class ThreeImageCardViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mAvatarView;
        private final TextView mTitleTextView;
        private final TextView mSubTitleTextView;
        private final TextView mBottomTextView;
        private final TextView mSourceUrlTextView;
        private final ImageView mCardImageView1;
        private final ImageView mCardImageView2;
        private final ImageView mCardImageView3;

        public ThreeImageCardViewHolder(@NonNull View itemView) {
            super(itemView);
            mAvatarView = itemView.findViewById(R.id.image_view_card_avatar);
            mTitleTextView = itemView.findViewById(R.id.text_view_card_title);
            mSubTitleTextView = itemView.findViewById(R.id.text_view_card_subtitle);
            mBottomTextView = itemView.findViewById(R.id.text_view_card_bottom_text);
            mSourceUrlTextView =  itemView.findViewById(R.id.text_view_source_url);
            mCardImageView1 = itemView.findViewById(R.id.image_view_image_1);
            mCardImageView2 = itemView.findViewById(R.id.image_view_image_2);
            mCardImageView3 = itemView.findViewById(R.id.image_view_image_3);

            onClickListener(itemView);
        }

        public void bindData(NewsCardItemDataModel dataModel, Context context) {
            Picasso.get().load(dataModel.getAvatar()).into(mAvatarView);
            ArrayList<String> images = dataModel.getThreeImageDrawable();
            Picasso.get().load(images.get(0)).into(mCardImageView1);
            Picasso.get().load(images.get(1)).into(mCardImageView2);
            Picasso.get().load(images.get(2)).into(mCardImageView3);
            // deal with title's length and subtitle's length
            String title = dataModel.getTitle();
            if(title.length() > 15) {
                title = title.substring(0, 16);
                title += "...";
            }
            mTitleTextView.setText(title);
            String subTitle = dataModel.getSubTitle();
            if(subTitle.length() > 70) {
                subTitle = subTitle.substring(0, 69);
                subTitle += "...";
            }
            mSubTitleTextView.setText(subTitle);
            mBottomTextView.setText(dataModel.getBottomText());
            mSourceUrlTextView.setText(dataModel.getDetailUrl());
        }
    }

    /**
     * Click the card item and move to NewsDetailActivity
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

    @Override
    public int getItemViewType(final int position) {
        switch(mDataModelList.get(position).getItemType()) {
            case NewsCardItemDataModel.NO_IMAGE_TYPE:
                return NewsCardItemDataModel.NO_IMAGE_TYPE;
            case NewsCardItemDataModel.ONE_IMAGE_TYPE:
                return NewsCardItemDataModel.ONE_IMAGE_TYPE;
            case NewsCardItemDataModel.THREE_IMAGE_TYPE:
                return NewsCardItemDataModel.THREE_IMAGE_TYPE;
            default:
                return -1;
        }
    }

    public NewsCardAdapter(List<NewsCardItemDataModel> modelList, Context context) {
        mDataModelList = modelList;
        mContext = context;
    }

    /**
     * load more news and add to mDataModelList
     * @param modelList
     */
    public void setDataModelList(List<NewsCardItemDataModel> modelList) {
        mDataModelList.addAll(modelList);
    }

    @Override
    public int getItemCount() {
        return mDataModelList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case NewsCardItemDataModel.NO_IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.no_image_card_item, parent, false);
                return new NoImageCardViewHolder(view);
            case NewsCardItemDataModel.ONE_IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.one_image_card_item, parent, false);
                return new OneImageCardViewHolder(view);
            case NewsCardItemDataModel.THREE_IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.three_images_card_item, parent, false);
                return new ThreeImageCardViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NotNull final RecyclerView.ViewHolder holder, final int position) {
        NewsCardItemDataModel object = mDataModelList.get(position);

        if(object != null) {
            switch (object.getItemType()) {
                case NewsCardItemDataModel.NO_IMAGE_TYPE:
                    NoImageCardViewHolder holder1 = (NoImageCardViewHolder)holder;
                    holder1.bindData(object, mContext);
                    break;

                case NewsCardItemDataModel.ONE_IMAGE_TYPE:
                    OneImageCardViewHolder holder2 = (OneImageCardViewHolder)holder;
                    holder2.bindData(object, mContext);
                    break;
                case NewsCardItemDataModel.THREE_IMAGE_TYPE:
                    ThreeImageCardViewHolder holder3 = (ThreeImageCardViewHolder)holder;
                    holder3.bindData(object, mContext);
                    break;
                default:
                    break;
            }
        }
    }
}
