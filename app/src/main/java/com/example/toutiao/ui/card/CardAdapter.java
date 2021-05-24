package com.example.toutiao.ui.card;

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
import com.example.toutiao.ui.card.dataModel.NoImageDataModel;
import com.example.toutiao.ui.card.dataModel.OneImageDataModel;
import com.example.toutiao.ui.card.dataModel.ThreeImageDataModel;

import org.jetbrains.annotations.NotNull;

public class CardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    class NoImageCardViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatarView;
        public TextView titleTextView;
        public TextView subTitleTextView;
        public TextView bottomTextView;

        public NoImageCardViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarView = itemView.findViewById(R.id.avatar);
            titleTextView = itemView.findViewById(R.id.card_title);
            subTitleTextView = itemView.findViewById(R.id.card_subtitle);
            bottomTextView = itemView.findViewById(R.id.card_bottom_text);
        }

        public void bindData(NoImageDataModel dataModel, Context context) {
            avatarView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.avatar_1));
            titleTextView.setText(dataModel.getTitle());
            subTitleTextView.setText(dataModel.getSubTitle());
            bottomTextView.setText(dataModel.getBottomText());
        }
    }

    class OneImageCardViewHolder extends RecyclerView.ViewHolder {
        public ImageView avatarView;
        public ImageView cardImageView;
        public TextView titleTextView;
        public TextView subTitleTextView;
        public TextView bottomTextView;

        public OneImageCardViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarView = itemView.findViewById(R.id.avatar);
            cardImageView = itemView.findViewById(R.id.imageView);
            titleTextView = itemView.findViewById(R.id.card_title);
            subTitleTextView = itemView.findViewById(R.id.card_subtitle);
            bottomTextView = itemView.findViewById(R.id.card_bottom_text);
        }

        public void bindData(OneImageDataModel dataModel, Context context) {
            avatarView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.avatar_1));
            cardImageView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.rem_blog));
            titleTextView.setText(dataModel.getTitle());
            subTitleTextView.setText(dataModel.getSubTitle());
            bottomTextView.setText(dataModel.getBottomText());
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

        public ThreeImageCardViewHolder(@NonNull View itemView) {
            super(itemView);
            avatarView = itemView.findViewById(R.id.avatar);
            cardImageView1 = itemView.findViewById(R.id.imageView1);
            cardImageView2 = itemView.findViewById(R.id.imageView2);
            cardImageView3 = itemView.findViewById(R.id.imageView3);
            titleTextView = itemView.findViewById(R.id.card_title);
            subTitleTextView = itemView.findViewById(R.id.card_subtitle);
            bottomTextView = itemView.findViewById(R.id.card_bottom_text);
        }

        public void bindData(ThreeImageDataModel dataModel, Context context) {
            avatarView.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.avatar_1));
            cardImageView1.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.rem_blog));
            cardImageView2.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.rem_blog));
            cardImageView3.setImageDrawable(ContextCompat.getDrawable(context, R.drawable.rem_blog));
            titleTextView.setText(dataModel.getTitle());
            subTitleTextView.setText(dataModel.getSubTitle());
            bottomTextView.setText(dataModel.getBottomText());
        }
    }

    @Override
    public int getItemViewType(int position) {
        // Just as an example, return 0 or 2 depending on position
        // Note that unlike in ListView adapters, types don't have to be contiguous
        return position % 3;
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case 0:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.no_image_card_item, parent, false);
                return new NoImageCardViewHolder(view);
            case 1:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.one_image_card_item, parent, false);
                return new OneImageCardViewHolder(view);
            case 2:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.three_images_card_item, parent, false);
                return new ThreeImageCardViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        switch (holder.getItemViewType()) {
            case 0:
                ViewHolder0 viewHolder0 = (ViewHolder0)holder;
                ...
                break;

            case 2:
                ViewHolder2 viewHolder2 = (ViewHolder2)holder;
                ...
                break;
        }
    }
}
