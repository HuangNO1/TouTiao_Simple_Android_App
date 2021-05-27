package com.example.toutiao.models.news;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class NewsDataModel {
    public static final int NO_IMAGE_TYPE = 0;
    public static final int ONE_IMAGE_TYPE = 1;
    public static final int THREE_IMAGE_TYPE = 2;
    // same
    private int news_card_style_type; // three different card style
    private String news_id; // id
    private String news_title; // news title
    private String news_abstract; // news abstract
    private int news_comments_count; // comments count
    private String news_source; // author name
    private Bitmap news_media_avatar_url; // author avatar
    private String news_source_url; // detail page url
    // different
    private Bitmap news_image_url; // one image card style
    private ArrayList<Bitmap> news_three_image; // three image card style

    // no image style constructor
    public NewsDataModel(
            int news_card_style_type,
            String news_id,
            String news_title,
            String news_abstract,
            int news_comments_count,
            String news_source,
            Bitmap news_media_avatar_url,
            String news_source_url
    ) {
        this.news_card_style_type = news_card_style_type;
        this.news_id = news_id;
        this.news_title = news_title;
        this.news_abstract = news_abstract;
        this.news_comments_count = news_comments_count;
        this.news_source = news_source;
        this.news_media_avatar_url = news_media_avatar_url;
        this.news_source_url = news_source_url;
    }

    public NewsDataModel(
            int news_card_style_type,
            String news_id,
            String news_title,
            String news_abstract,
            int news_comments_count,
            String news_source,
            Bitmap news_media_avatar_url,
            String news_source_url,
            Bitmap news_image_url
    ) {
        this.news_card_style_type = news_card_style_type;
        this.news_id = news_id;
        this.news_title = news_title;
        this.news_abstract = news_abstract;
        this.news_comments_count = news_comments_count;
        this.news_source = news_source;
        this.news_media_avatar_url = news_media_avatar_url;
        this.news_source_url = news_source_url;
        this.news_image_url = news_image_url;
    }

    public NewsDataModel(
            int news_card_style_type,
            String news_id,
            String news_title,
            String news_abstract,
            int news_comments_count,
            String news_source,
            Bitmap news_media_avatar_url,
            String news_source_url,
            ArrayList<Bitmap> news_three_image
    ) {
        this.news_card_style_type = news_card_style_type;
        this.news_id = news_id;
        this.news_title = news_title;
        this.news_abstract = news_abstract;
        this.news_comments_count = news_comments_count;
        this.news_source = news_source;
        this.news_media_avatar_url = news_media_avatar_url;
        this.news_source_url = news_source_url;
        this.news_three_image = news_three_image;
    }

    public int getNews_card_style_type() {
        return news_card_style_type;
    }

    public String getNews_id() {
        return news_id;
    }

    public String getNews_title() {
        return news_title;
    }

    public String getNews_abstract() {
        return news_abstract;
    }

    public int getNews_comments_count() {
        return news_comments_count;
    }

    public String getNews_source() {
        return news_source;
    }

    public Bitmap getNews_media_avatar_url() {
        return news_media_avatar_url;
    }

    public String getNews_source_url() {
        return news_source_url;
    }

    public Bitmap getNews_image_url() {
        return news_image_url;
    }

    public ArrayList<Bitmap> getNews_three_image() {
        return news_three_image;
    }

    public void setNews_media_avatar_url(Bitmap news_media_avatar_url) {
        this.news_media_avatar_url = news_media_avatar_url;
    }

    public void setNews_image_url(Bitmap news_image_url) {
        this.news_image_url = news_image_url;
    }

    public void setNews_three_image(Bitmap news_image_url) {
        this.news_three_image.add(news_image_url);
    }
}
