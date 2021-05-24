package com.example.toutiao.ui.card.dataModel;

import com.example.toutiao.R;
import com.example.toutiao.ui.card.card_test.DataModel;

import java.util.Locale;

public class OneImageDataModel{
    private int imageDrawable;
    private int avatar;
    private String title;
    private String subTitle;
    private String bottomText;

    public OneImageDataModel(int id) {
        avatar = R.drawable.avatar_1;
        imageDrawable = R.drawable.rem_blog;
        title = String.format(Locale.CHINESE, "Title %d Goes Here", id);
        subTitle = String.format(Locale.CHINESE, "Sub title %d goes here", id);
        bottomText = String.format(Locale.CHINESE, "%d Comments", id);
    }

    public int getAvatar() {
        return avatar;
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

    public String getBottomText() {
        return bottomText;
    }
}