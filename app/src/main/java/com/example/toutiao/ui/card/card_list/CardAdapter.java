package com.example.toutiao.ui.card.card_list;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import com.example.toutiao.R;
import com.example.toutiao.activity.MainActivity;
import com.example.toutiao.activity.NewsDetailActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private List<CardItemDataModel> dataModelList;
    private Context mContext;

    class NoImageCardViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatarView;
        public TextView titleTextView;
        public TextView subTitleTextView;
        public TextView bottomTextView;
        public TextView sourceUrlTextView;

        public NoImageCardViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarView = itemView.findViewById(R.id.avatar);
            titleTextView = itemView.findViewById(R.id.card_title);
            subTitleTextView = itemView.findViewById(R.id.card_subtitle);
            bottomTextView = itemView.findViewById(R.id.card_bottom_text);
            sourceUrlTextView =  itemView.findViewById(R.id.source_url);

            onClickListener(itemView);
        }

        public void bindData(CardItemDataModel dataModel, Context context) {
            Picasso.get().load(dataModel.getAvatar()).into(avatarView);
            titleTextView.setText(dataModel.getTitle());
            subTitleTextView.setText(dataModel.getSubTitle());
            bottomTextView.setText(dataModel.getBottomText());
            sourceUrlTextView.setText(dataModel.getDetailUrl());
        }
    }

    class OneImageCardViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatarView;
        public ImageView cardImageView;
        public TextView titleTextView;
        public TextView subTitleTextView;
        public TextView bottomTextView;
        public TextView sourceUrlTextView;

        public OneImageCardViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarView = itemView.findViewById(R.id.avatar);
            cardImageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.card_title);
            subTitleTextView = itemView.findViewById(R.id.card_subtitle);
            bottomTextView = itemView.findViewById(R.id.card_bottom_text);
            sourceUrlTextView =  itemView.findViewById(R.id.source_url);

            onClickListener(itemView);
        }

        public void bindData(CardItemDataModel dataModel, Context context) {
            Picasso.get().load(dataModel.getAvatar()).into(avatarView);
            Picasso.get().load(dataModel.getImageDrawable()).into(cardImageView);
            titleTextView.setText(dataModel.getTitle());
            subTitleTextView.setText(dataModel.getSubTitle());
            bottomTextView.setText(dataModel.getBottomText());
            sourceUrlTextView.setText(dataModel.getDetailUrl());
        }
    }

    class ThreeImageCardViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatarView;
        public ImageView cardImageView1;
        public ImageView cardImageView2;
        public ImageView cardImageView3;
        public TextView titleTextView;
        public TextView subTitleTextView;
        public TextView bottomTextView;
        public TextView sourceUrlTextView;

        public ThreeImageCardViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarView = itemView.findViewById(R.id.avatar);
            cardImageView1 = itemView.findViewById(R.id.imageView1);
            cardImageView2 = itemView.findViewById(R.id.imageView2);
            cardImageView3 = itemView.findViewById(R.id.imageView3);
            titleTextView = itemView.findViewById(R.id.card_title);
            subTitleTextView = itemView.findViewById(R.id.card_subtitle);
            bottomTextView = itemView.findViewById(R.id.card_bottom_text);
            sourceUrlTextView =  itemView.findViewById(R.id.source_url);

            onClickListener(itemView);
        }

        public void bindData(CardItemDataModel dataModel, Context context) {
            Picasso.get().load(dataModel.getAvatar()).into(avatarView);
            ArrayList<String> images = dataModel.getThreeImageDrawable();
            Picasso.get().load(images.get(0)).into(cardImageView1);
            Picasso.get().load(images.get(1)).into(cardImageView2);
            Picasso.get().load(images.get(2)).into(cardImageView3);
            titleTextView.setText(dataModel.getTitle());
            subTitleTextView.setText(dataModel.getSubTitle());
            bottomTextView.setText(dataModel.getBottomText());
            sourceUrlTextView.setText(dataModel.getDetailUrl());
        }
    }

    // Click the card item and move to NewsDetailActivity
    public void onClickListener(@NonNull View itemView) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) mContext;
                TextView source_url = itemView.findViewById(R.id.source_url);
                Intent intent = new Intent(activity,NewsDetailActivity.class);
                intent.putExtra("source_url", source_url.getText().toString());
                mContext.startActivity(intent);
                activity.overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
            }
        });
    }

    @Override
    public int getItemViewType(final int position) {
        switch(dataModelList.get(position).getItemType()) {
            case CardItemDataModel.NO_IMAGE_TYPE:
                return CardItemDataModel.NO_IMAGE_TYPE;
            case CardItemDataModel.ONE_IMAGE_TYPE:
                return CardItemDataModel.ONE_IMAGE_TYPE;
            case CardItemDataModel.THREE_IMAGE_TYPE:
                return CardItemDataModel.THREE_IMAGE_TYPE;
            default:
                return -1;
        }
    }

    public CardAdapter(List<CardItemDataModel> modelList, Context context) {
        dataModelList = modelList;
        mContext = context;
    }

    @Override
    public int getItemCount() {
        return dataModelList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case CardItemDataModel.NO_IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.no_image_card_item, parent, false);
                return new NoImageCardViewHolder(view);
            case CardItemDataModel.ONE_IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.one_image_card_item, parent, false);
                return new OneImageCardViewHolder(view);
            case CardItemDataModel.THREE_IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.three_images_card_item, parent, false);
                return new ThreeImageCardViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        CardItemDataModel object = dataModelList.get(position);

        if(object != null) {
            switch (object.getItemType()) {
                case CardItemDataModel.NO_IMAGE_TYPE:
                    NoImageCardViewHolder holder1 = (NoImageCardViewHolder)holder;
                    holder1.bindData(object, mContext);
                    break;

                case CardItemDataModel.ONE_IMAGE_TYPE:
                    OneImageCardViewHolder holder2 = (OneImageCardViewHolder)holder;
                    holder2.bindData(object, mContext);
                    break;
                case CardItemDataModel.THREE_IMAGE_TYPE:
                    ThreeImageCardViewHolder holder3 = (ThreeImageCardViewHolder)holder;
                    holder3.bindData(object, mContext);
                    break;
                default:
                    break;
            }
        }
    }
}
