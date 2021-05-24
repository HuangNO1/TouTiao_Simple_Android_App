package com.example.toutiao.ui.card.card_test;

import com.example.toutiao.R;

import java.util.Locale;

public class DataModel {
    private int imageDrawable;
    private String title;
    private String subTitle;

    public DataModel(int id) {
        imageDrawable = R.drawable.rem_blog;
        title = String.format(Locale.ENGLISH, "Title %d Goes Here", id);
        subTitle = String.format(Locale.ENGLISH, "Sub title %d goes here", id);
    }

    public DataModel() {

    }

    public int getImageDrawable() {
        return imageDrawable;
    }

    public String getTitle() {
        return title;
    }

    public String getSubTitle() {
        return subTitle;
    }
}
