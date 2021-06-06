package com.example.toutiao.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.example.toutiao.R;
import com.example.toutiao.activity.SearchActivity;
import com.example.toutiao.ui.page.newsChannel.NewsChannelFragment;
import com.example.toutiao.ui.page.newsChannel.SectionsPagerAdapter;
import com.example.toutiao.ui.search.SearchView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.toutiao.ui.home.HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPagerChannel;
    private TabLayout tabsChannel;
//    private NewsChannelFragment mCurrentNCF;
    private SearchView mNewsSearchView;
//    private FloatingActionButton mScrollToTopFab;
//    private int mFabState = 0;

//    // TODO: Rename parameter arguments, choose names that match
//    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
//    private static final String ARG_PARAM1 = "param1";
//    private static final String ARG_PARAM2 = "param2";
//
//    // TODO: Rename and change types of parameters
//    private String mParam1;
//    private String mParam2;

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static com.example.toutiao.ui.account.AccountFragment newInstance(String param1, String param2) {
        com.example.toutiao.ui.account.AccountFragment fragment = new com.example.toutiao.ui.account.AccountFragment();
        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // setting status bar's color
        Window mWindow = getActivity().getWindow();
        mWindow.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        mWindow.setStatusBarColor(getResources().getColor(R.color.tabbed_bg));
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // setting tabbed
        mSectionsPagerAdapter = new SectionsPagerAdapter(container.getContext(), getChildFragmentManager());
        mViewPagerChannel = view.findViewById(R.id.view_pager_channel);
        mViewPagerChannel.setAdapter(mSectionsPagerAdapter);
        tabsChannel = view.findViewById(R.id.tabs_channel);
        tabsChannel.setupWithViewPager(mViewPagerChannel);

        // search view
        mNewsSearchView = view.findViewById(R.id.search_view_news);
        mNewsSearchView.setEditTextFocusable(false);
        // set onClickListener to Search Activity

        mNewsSearchView.mSearchEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = getContext();
                Activity activity = (Activity) context;
                Intent intent = new Intent(activity, SearchActivity.class);
                context.startActivity(intent);
                activity.overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
            }
        });
//
//        mScrollToTopFab = view.findViewById(R.id.fab_scroll_top);
//
//        mViewPagerChannel.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                mFabState = state;
//            }
//        });
//
//        mScrollToTopFab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                mCurrentNCF = (NewsChannelFragment) mSectionsPagerAdapter.getItem(mFabState);
//
//            }
//        });

        return view;
    }


}