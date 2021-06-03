package com.example.toutiao.models.news;

import java.util.ArrayList;

/**
 * Data Model Class for news After requesting
 */

public class NewsDataModel {
    public static final int NO_IMAGE_TYPE = 0;
    public static final int ONE_IMAGE_TYPE = 1;
    public static final int THREE_IMAGE_TYPE = 2;
    // same
    private int mNewsCardStyleType; // three different card style
    private String mNewsId; // id
    private String mNewsTitle; // news title
    private String mNewsAbstract; // news abstract
    private int mNewsCommentsCount; // comments count
    private String mNewsSource; // author name
    private String mNewsMediaAvatarUrl; // author avatar
    private String mNewsSourceUrl; // detail page url
    // different
    private String mNewsImageUrl; // one image card style
    private ArrayList<String> mNewsThreeImage; // three image card style

    // no image style constructor
    public NewsDataModel(
            int newsCardStyleType,
            String newsId,
            String newsTitle,
            String newsAbstract,
            int newsCommentsCount,
            String newsSource,
            String newsMediaAvatarUrl,
            String newsSourceUrl
    ) {
        mNewsCardStyleType = newsCardStyleType;
        mNewsId = newsId;
        mNewsTitle = newsTitle;
        mNewsAbstract = newsAbstract;
        mNewsCommentsCount = newsCommentsCount;
        mNewsSource = newsSource;
        mNewsMediaAvatarUrl = newsMediaAvatarUrl;
        mNewsSourceUrl = newsSourceUrl;
    }

    // one image style constructor
    public NewsDataModel(
            int newsCardStyleType,
            String newsId,
            String newsTitle,
            String newsAbstract,
            int newsCommentsCount,
            String newsSource,
            String newsMediaAvatarUrl,
            String newsSourceUrl,
            String newsImageUrl
    ) {
        mNewsCardStyleType = newsCardStyleType;
        mNewsId = newsId;
        mNewsTitle = newsTitle;
        mNewsAbstract = newsAbstract;
        mNewsCommentsCount = newsCommentsCount;
        mNewsSource = newsSource;
        mNewsMediaAvatarUrl = newsMediaAvatarUrl;
        mNewsSourceUrl = newsSourceUrl;
        mNewsImageUrl = newsImageUrl;
    }

    // three image style constructor
    public NewsDataModel(
            int newsCardStyleType,
            String newsId,
            String newsTitle,
            String newsAbstract,
            int newsCommentsCount,
            String newsSource,
            String newsMediaAvatarUrl,
            String newsSourceUrl,
            ArrayList<String> newsThreeImage
    ) {
        mNewsCardStyleType = newsCardStyleType;
        mNewsId = newsId;
        mNewsTitle = newsTitle;
        mNewsAbstract = newsAbstract;
        mNewsCommentsCount = newsCommentsCount;
        mNewsSource = newsSource;
        mNewsMediaAvatarUrl = newsMediaAvatarUrl;
        mNewsSourceUrl = newsSourceUrl;
        mNewsThreeImage = newsThreeImage;
    }

    public int getNewsCardStyleType() {
        return mNewsCardStyleType;
    }

    public void setNewsCardStyleType(int newsCardStyleType) {
        mNewsCardStyleType = newsCardStyleType;
    }

    public String getNewsId() {
        return mNewsId;
    }

    public void setNewsId(String newsId) {
        mNewsId = newsId;
    }

    public String getNewsTitle() {
        return mNewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        mNewsTitle = newsTitle;
    }

    public String getNewsAbstract() {
        return mNewsAbstract;
    }

    public void setNewsAbstract(String newsAbstract) {
        mNewsAbstract = newsAbstract;
    }

    public int getNewsCommentsCount() {
        return mNewsCommentsCount;
    }

    public void setNewsCommentsCount(int newsCommentsCount) {
        mNewsCommentsCount = newsCommentsCount;
    }

    public String getNewsSource() {
        return mNewsSource;
    }

    public void setNewsSource(String newsSource) {
        mNewsSource = newsSource;
    }

    public String getNewsMediaAvatarUrl() {
        return mNewsMediaAvatarUrl;
    }

    public void setNewsMediaAvatarUrl(String newsMediaAvatarUrl) {
        mNewsMediaAvatarUrl = newsMediaAvatarUrl;
    }

    public String getNewsSourceUrl() {
        return mNewsSourceUrl;
    }

    public void setNewsSourceUrl(String newsSourceUrl) {
        mNewsSourceUrl = newsSourceUrl;
    }

    public String getNewsImageUrl() {
        return mNewsImageUrl;
    }

    public void setNewsImageUrl(String newsImageUrl) {
        mNewsImageUrl = newsImageUrl;
    }

    public ArrayList<String> getNewsThreeImage() {
        return mNewsThreeImage;
    }

    /*
     * add a image to mNewsThreeImage array list
     */
    public void setNewsThreeImage(String newsImageUrl) {
        this.mNewsThreeImage.add(newsImageUrl);
    }
}
