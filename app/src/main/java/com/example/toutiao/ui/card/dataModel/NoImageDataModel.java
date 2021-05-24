package com.example.toutiao.ui.card.dataModel;

import com.example.toutiao.R;
import com.example.toutiao.ui.card.card_test.DataModel;

import java.util.Locale;

public class NoImageDataModel {
    private int avatar;
    private String title;
    private String subTitle;
    private String bottomText;

    public NoImageDataModel(int id) {
        avatar = R.drawable.avatar_1;
        title = String.format(Locale.CHINESE, "Title %d Goes Here", id);
        subTitle = String.format(Locale.CHINESE, "Sub title %d goes here", id);
        bottomText = String.format(Locale.CHINESE, "%d Comments", id);
    }

    public int getAvatar() {
        return avatar;
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
