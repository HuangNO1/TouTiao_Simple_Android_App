package com.example.toutiao.ui.card.cardTest;

import com.example.toutiao.R;

import java.util.Locale;

public class DataModel {
    private int mImageDrawable;
    private String mTitle;
    private String mSubTitle;

    public DataModel(int id) {
        mImageDrawable = R.drawable.rem_blog;
        mTitle = String.format(Locale.ENGLISH, "Title %d Goes Here", id);
        mSubTitle = String.format(Locale.ENGLISH, "Sub title %d goes here", id);
    }

    public DataModel() {

    }

    public int getImageDrawable() {
        return mImageDrawable;
    }

    public String getTitle() {
        return mTitle;
    }

    public String getSubTitle() {
        return mSubTitle;
    }
}
