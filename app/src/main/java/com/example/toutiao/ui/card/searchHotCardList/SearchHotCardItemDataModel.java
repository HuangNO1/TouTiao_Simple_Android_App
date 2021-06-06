package com.example.toutiao.ui.card.searchHotCardList;

public class SearchHotCardItemDataModel {
    private int mKey; // key
    private String mTitle; // title
    private String mDetailUrl; // detail text to jump

    public SearchHotCardItemDataModel(int key, String title, String detailUrl) {
        mKey = key;
        mTitle = title;
        mDetailUrl = detailUrl;
    }

    public int getKey() {
        return mKey;
    }

    public void setKey(int key) {
        mKey = key;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getDetailUrl() {
        return mDetailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        mDetailUrl = detailUrl;
    }
}
