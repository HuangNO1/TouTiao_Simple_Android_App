package com.example.toutiao.ui.card.newsCardList;

import java.util.ArrayList;
import java.util.Locale;

/**
 * CardItemDataModel class: the card item data model in New Channel Fragment
 */

public class NewsCardItemDataModel {
    public static final int NO_IMAGE_TYPE = 0;
    public static final int ONE_IMAGE_TYPE = 1;
    public static final int THREE_IMAGE_TYPE = 2;

    private int mItemType; // cart type
    private String mId; //id
    private String mAvatar; // avatar
    private ArrayList<String> mThreeImageDrawable; // three image
    private String mImageDrawable; // one image
    private String mTitle; // title
    private String mSubTitle; // subtitle
    private String mBottomText; // bottom text
    private String mDetailUrl; // detail text to jump

    // no image style constructor
    public NewsCardItemDataModel(int itemType,
                                 String id,
                                 String newsTitle,
                                 String newsAbstract,
                                 int newsCommentsCount,
                                 String newsSource,
                                 String newsMediaAvatarUrl,
                                 String newsSourceUrl
    ) {
        mItemType = itemType;
        mId = id;
        mAvatar = newsMediaAvatarUrl;
        mTitle = String.format(Locale.CHINESE, "%s", newsTitle);
        mSubTitle = String.format(Locale.CHINESE, "%s", newsAbstract);
        mBottomText = String.format(Locale.CHINESE, "%s  %d 评论", newsSource, newsCommentsCount);
        mDetailUrl = newsSourceUrl;
    }

    // one image style constructor
    public NewsCardItemDataModel(int itemType,
                                 String id,
                                 String newsTitle,
                                 String newsAbstract,
                                 int newsCommentsCount,
                                 String newsSource,
                                 String newsMediaAvatarUrl,
                                 String newsSourceUrl,
                                 String imageDrawable
    ) {
        mItemType = itemType;
        mId = id;
        mAvatar = newsMediaAvatarUrl;
        mTitle = String.format(Locale.CHINESE, "%s", newsTitle);
        mSubTitle = String.format(Locale.CHINESE, "%s", newsAbstract);
        mBottomText = String.format(Locale.CHINESE, "%s  %d 评论", newsSource, newsCommentsCount);
        mDetailUrl = newsSourceUrl;
        // one
        mImageDrawable = imageDrawable;
    }

    // three image style constructor
    public NewsCardItemDataModel(int itemType,
                                 String id,
                                 String newsTitle,
                                 String newsAbstract,
                                 int newsCommentsCount,
                                 String newsSource,
                                 String newsMediaAvatarUrl,
                                 String newsSourceUrl,
                                 ArrayList<String> threeImageDrawable
    ) {
        mItemType = itemType;
        mId = id;
        mAvatar = newsMediaAvatarUrl;
        mTitle = String.format(Locale.CHINESE, "%s", newsTitle);
        mSubTitle = String.format(Locale.CHINESE, "%s", newsAbstract);
        mBottomText = String.format(Locale.CHINESE, "%s  %d 评论", newsSource, newsCommentsCount);
        mDetailUrl = newsSourceUrl;
        // three
        mThreeImageDrawable = threeImageDrawable;
    }

    public int getItemType() {
        return mItemType;
    }

    public void setItemType(int itemType) {
        mItemType = itemType;
    }

    public String getId() {
        return mId;
    }

    public void setId(String id) {
        mId = id;
    }

    public String getAvatar() {
        return mAvatar;
    }

    public void setAvatar(String avatar) {
        mAvatar = avatar;
    }

    public String getBottomText() {
        return mBottomText;
    }

    public void setBottomText(String bottomText) {
        mBottomText = bottomText;
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
    }

    public String getSubTitle() {
        return mSubTitle;
    }

    public void setSubTitle(String subTitle) {
        mSubTitle = subTitle;
    }

    public ArrayList<String> getThreeImageDrawable() {
        return mThreeImageDrawable;
    }

    public void setThreeImageDrawable(ArrayList<String> threeImageDrawable) {
        mThreeImageDrawable = threeImageDrawable;
    }

    public String getImageDrawable() {
        return mImageDrawable;
    }

    public void setImageDrawable(String imageDrawable) {
        mImageDrawable = imageDrawable;
    }

    public String getDetailUrl() {
        return mDetailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        mDetailUrl = detailUrl;
    }
}
