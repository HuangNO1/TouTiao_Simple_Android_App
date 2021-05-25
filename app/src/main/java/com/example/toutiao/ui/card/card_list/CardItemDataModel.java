package com.example.toutiao.ui.card.card_list;

import com.example.toutiao.R;

import java.util.ArrayList;
import java.util.Locale;

public class CardItemDataModel {
    public static final int NO_IMAGE_TYPE = 0;
    public static final int ONE_IMAGE_TYPE = 1;
    public static final int THREE_IMAGE_TYPE = 2;

    private int itemType;
    private int avatar;
    private ArrayList<Integer> threeImageDrawable;
    private int imageDrawable;
    private String title;
    private String subTitle;
    private String bottomText;

    public CardItemDataModel(int id, int itemType) {
        this.itemType = itemType;
        switch (itemType) {
            case NO_IMAGE_TYPE:
                break;
            case ONE_IMAGE_TYPE:
                imageDrawable = R.drawable.rem_blog;
                break;
            case THREE_IMAGE_TYPE:
                threeImageDrawable = new ArrayList<>();
                threeImageDrawable.add(R.drawable.rem_blog);
                threeImageDrawable.add(R.drawable.rem_blog);
                threeImageDrawable.add(R.drawable.rem_blog);
                break;
            default:
                break;
        }
        avatar = R.drawable.avatar_1;
        title = String.format(Locale.CHINESE, "Title %d Goes Here", id);
        subTitle = String.format(Locale.CHINESE, "Sub title %d goes here", id);
        bottomText = String.format(Locale.CHINESE, "%d Comments", id);
    }

    public int getAvatar() {
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

    public ArrayList<Integer> getThreeImageDrawable() {
        return threeImageDrawable;
    }

    public int getImageDrawable() {
        return imageDrawable;
    }
}
