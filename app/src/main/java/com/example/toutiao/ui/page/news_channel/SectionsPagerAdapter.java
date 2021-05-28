package com.example.toutiao.ui.page.news_channel;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.toutiao.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[] {
        R.string.title__all__,
        R.string.title_news_tech
//        R.string.title_news_image,
//        R.string.title_news_hot,
//        R.string.title_news_entertainment,
//        R.string.title_news_game,
//        R.string.title_news_sports,
//        R.string.title_news_finance,
//        R.string.title_digital,
    };
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // getItem is called to instantiate the fragment for the given page.
        // Return a PlaceholderFragment (defined as a static inner class below).
        String category = mContext.getString(TAB_TITLES[position]);
        return NewsChannelFragment.newInstance(category, position);
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show total pages.
        return TAB_TITLES.length;
    }
}

