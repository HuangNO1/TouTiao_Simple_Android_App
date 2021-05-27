package com.example.toutiao.ui.card.card_list;

import android.graphics.Bitmap;

import com.example.toutiao.R;

import java.util.ArrayList;
import java.util.Locale;

public class CardItemDataModel {
    public static final int NO_IMAGE_TYPE = 0;
    public static final int ONE_IMAGE_TYPE = 1;
    public static final int THREE_IMAGE_TYPE = 2;

    private int itemType;
    private String id;
    private Bitmap avatar;
    private ArrayList<Bitmap> threeImageDrawable;
    private Bitmap imageDrawable;
    private String title;
    private String subTitle;
    private String bottomText;
    private String detailUrl;

    // no image style
    public CardItemDataModel(int itemType,
                             String id,
                             String news_title,
                             String news_abstract,
                             int news_comments_count,
                             String news_source,
                             Bitmap news_media_avatar_url,
                             String news_source_url
    ) {
        this.itemType = itemType;
        this.id = id;
        avatar = news_media_avatar_url;
        title = String.format(Locale.CHINESE, "%s", news_title);
        subTitle = String.format(Locale.CHINESE, "%s", news_abstract);
        bottomText = String.format(Locale.CHINESE, "%s  %d 评论", news_source, news_comments_count);
        detailUrl = news_source_url;
    }


    // one image style
    public CardItemDataModel(int itemType,
                             String id,
                             String news_title,
                             String news_abstract,
                             int news_comments_count,
                             String news_source,
                             Bitmap news_media_avatar_url,
                             String news_source_url,
                             Bitmap imageDrawable
    ) {
        this.itemType = itemType;
        this.id = id;
        avatar = news_media_avatar_url;
        title = String.format(Locale.CHINESE, "%s", news_title);
        subTitle = String.format(Locale.CHINESE, "%s", news_abstract);
        bottomText = String.format(Locale.CHINESE, "%s  %d 评论", news_source, news_comments_count);
        detailUrl = news_source_url;
        // one
        this.imageDrawable = imageDrawable;
    }

    // three image style
    public CardItemDataModel(int itemType,
                             String id,
                             String news_title,
                             String news_abstract,
                             int news_comments_count,
                             String news_source,
                             Bitmap news_media_avatar_url,
                             String news_source_url,
                             ArrayList<Bitmap> threeImageDrawable
    ) {
        this.itemType = itemType;
        this.id = id;
        avatar = news_media_avatar_url;
        title = String.format(Locale.CHINESE, "%s", news_title);
        subTitle = String.format(Locale.CHINESE, "%s", news_abstract);
        bottomText = String.format(Locale.CHINESE, "%s  %d 评论", news_source, news_comments_count);
        detailUrl = news_source_url;
        // three
        this.threeImageDrawable = threeImageDrawable;
    }

    public String getId() {
        return id;
    }

    public Bitmap getAvatar() {
        return avatar;
    }

    public String getBottomText() {
        return bottomText;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }

    public int getItemType() {
        return itemType;
    }

    public ArrayList<Bitmap> getThreeImageDrawable() {
        return threeImageDrawable;
    }

    public Bitmap getImageDrawable() {
        return imageDrawable;
    }

    public String getDetailUrl() {
        return detailUrl;
    }
}
