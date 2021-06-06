package com.example.toutiao.models.search;

// https://www.toutiao.com/hot-event/hot-board/?origin=toutiao_pc
public class SearchHotEventDataModel {
    private int mKey; // key
    private String mNewsTitle; // news title
    private String mNewsSourceUrl; // detail page url

    public SearchHotEventDataModel(int key, String newsTitle, String newsSourceUrl) {
        mKey = key;
        mNewsTitle = newsTitle;
        mNewsSourceUrl = newsSourceUrl;
    }

    public int getKey() {
        return mKey;
    }

    public void setKey(int key) {
        mKey = key;
    }

    public String getTitle() {
        return mNewsTitle;
    }

    public void setTitle(String title) {
        mNewsTitle = title;
    }

    public String getNewsSourceUrl() {
        return mNewsSourceUrl;
    }

    public void setNewsSourceUrl(String newsSourceUrl) {
        mNewsSourceUrl = newsSourceUrl;
    }
}
