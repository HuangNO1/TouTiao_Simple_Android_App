簡易今日頭條
===

此為**中南大學計算機學院** 軟件工程 18 級 大三 移動應用端開發課程 項目設計。

**指導老師：曾鋒**
製作人：黃柏曛

項目開發需求指導書：[简易版今日头条](https://bytedance.feishu.cn/docs/doccnKvehaFYqdzDxaoOwkO6cah)

Code Style：[Android程式碼命名規範 - itread01](https://www.itread01.com/content/1549797666.html)、[貢獻者的AOSP Java代碼樣式 - android 官方](https://source.android.com/setup/contribute/code-style#java-language-rules)

**如果喜歡這個作品，麻煩給個 Star ，謝謝，這是給我的動力。**

## 涉及技術點

### 底部 Nav 導覽切換頁面 Fragment

使用 **BottomNavigationView**、**NavController** 與 **NavigationUI.setupWithNavController()** 方法去實現。

現在我需要使用三個子頁面(Fragment)分別是**首頁(home)、視頻(video)、我的(account)**：

> 注意：目錄的正確需要自己去修改

- layout/activity.xml

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:id="@+id/main_activity"
    tools:context=".activity.MainActivity">

    <com.google.android.material.bottomnavigation.BottomNavigationView
        android:id="@+id/nav_view"
        android:layout_width="0dp"
        android:layout_height="wrap_content"
        android:layout_marginStart="0dp"
        android:layout_marginEnd="0dp"
        android:background="?android:attr/windowBackground"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:menu="@menu/bottom_nav_menu" />

    <fragment
        android:id="@+id/nav_host_fragment_activity_main"
        android:name="androidx.navigation.fragment.NavHostFragment"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:defaultNavHost="true"
        app:layout_constraintBottom_toTopOf="@id/nav_view"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:navGraph="@navigation/mobile_navigation" />

</androidx.constraintlayout.widget.ConstraintLayout>
```

- navigation/mobile_navigation.xml

**navigation** 是可以設計**導覽圖**的一個組件也包括 Fragment 頁面的跳轉動。

使用可以參考官方文檔 [Navigation 组件使用入门 - Android 开发者](https://developer.android.com/guide/navigation/navigation-getting-started?hl=zh-cn)。

```xml
<?xml version="1.0" encoding="utf-8"?>
<navigation xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/mobile_navigation"
    app:startDestination="@+id/navigation_home">

    <fragment
        android:id="@+id/navigation_home"
        android:name="com.example.toutiao.ui.home.HomeFragment"
        android:label="@string/title_home"
        tools:layout="@layout/fragment_home" />

    <fragment
        android:id="@+id/navigation_video"
        android:name="com.example.toutiao.ui.video.VideoFragment"
        android:label="@string/title_video"
        tools:layout="@layout/fragment_video" />

    <fragment
        android:id="@+id/navigation_account"
        android:name="com.example.toutiao.ui.account.AccountFragment"
        android:label="@string/title_notifications"
        tools:layout="@layout/fragment_account" />
</navigation>
```

- layout/fragment_home、layout/fragment_video、layout/fragment_account

三個 Fragment 需要創建，內容都一樣，自行替換裡面的參數。

```xml
<?xml version="1.0" encoding="utf-8"?>
<FrameLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".ui.home.HomeFragment">

    <!-- TODO: Update blank fragment layout -->
    <TextView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:text="@string/hello_home_fragment" />

</FrameLayout>
```

- activity/MainActivity.java

```java
package com.example.toutiao.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import com.example.toutiao.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navView = findViewById(R.id.nav_view);

        // binding the bottom_nav and fragment to transfer
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_activity_main);
        NavigationUI.setupWithNavController(navView, navController);
    }
}
```

- ui/home/HomeFragment.java、ui/video/VideoFragment.java、ui/account/AccountFragment.java

這三個類文件都一致，自己去做調整。以下取 `VideoFragment.java` 作為參考。

```java
package com.example.toutiao.ui.video;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.toutiao.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link VideoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class VideoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public VideoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment VideoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static VideoFragment newInstance(String param1, String param2) {
        VideoFragment fragment = new VideoFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_video, container, false);
    }
}
```

## Swipe views with tabs 的多頻道展示

這裡我一開始使用的是**舊的 API 方法(`ViewPagerFragment`、`PagerAdapter`)**，如果你使用的是：

- androidx.viewpager.widget.ViewPager
- androidx.fragment.app.FragmentPagerAdapter

你會得到一個提示是這**兩個 API 是已經棄用(deprecated)**的，尤其是在寫 **FragmentPagerAdapter** 類的時候，Android Studio 會在父類類名上刪除線(Strikethrough)，所以我們需要切換到新的 API：

- androidx.viewpager2.widget.ViewPager2
- androidx.viewpager2.adapter.FragmentStateAdapter

那我們來開始看具體寫法，可以參考官方文檔[Create swipe views with tabs using ViewPager2 - Android developers](https://developer.android.com/guide/navigation/navigation-swipe-view-2)。

> 註：由於這個的 tabbed 項目需求的頁面是相同的，都是展示新聞卡片，**所以我們就只需要設計一個 Fragment 類**，但是如果我們需要每個新聞頻道 Tab 頁面有不同的設計，就需要設計多個 Fragment 類。

- layout/fragment_home.xml

我需要在 Home Fragment 展示我的 Tabs，然後在 **NewsChannelFragment** 顯示各頁面內容，這裡我們有 `TabLayout` 和最新的 `ViewPager2`。

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <com.google.android.material.appbar.AppBarLayout
        android:id="@+id/app_bar"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:theme="@style/Theme.PopupOverlay"
        android:background="@color/tabbed_bg">

        <androidx.appcompat.widget.Toolbar
            android:layout_width="match_parent"
            android:layout_height="?attr/actionBarSize"
            android:layout_marginEnd="10dp"
            app:layout_scrollFlags="scroll|enterAlways">

            <com.example.toutiao.ui.searchBar.SearchView
                android:id="@+id/search_view_news"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:clickable="true"
                android:focusable="true"
                android:layout_marginEnd="30dp" />

            <Button
                android:layout_width="30dp"
                android:layout_height="30dp"
                android:layout_gravity="right|center_vertical"
                android:background="@drawable/ic_baseline_add_circle_24"
                android:backgroundTint="@color/white"
                android:clickable="true"
                android:focusable="true"
                tools:ignore="RtlHardcoded" />

        </androidx.appcompat.widget.Toolbar>
        <com.google.android.material.tabs.TabLayout
            android:id="@+id/tabs_channel"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            app:tabIndicatorColor="#FFFFFF"
            app:tabMode="scrollable"
            app:tabRippleColor="#00FFFFFF"
            app:tabSelectedTextColor="@color/white"
            app:tabTextColor="#D1D1D1">

        </com.google.android.material.tabs.TabLayout>
    </com.google.android.material.appbar.AppBarLayout>

    <androidx.viewpager2.widget.ViewPager2
        android:id="@+id/view_pager_channel"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="@string/appbar_scrolling_view_behavior"/>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:context=".ui.home.HomeFragment"/>

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

- layout/fragment_news_channel.xml

這裡就沒有比較需要注意的東西。

```xml
<?xml version="1.0" encoding="utf-8"?>

<androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/fragment_news_channel"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    tools:context=".ui.page.newsChannel.NewsChannelFragment">

	<!-- ... -->

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

- ui/page/newsChannel/newsChannelFragment.java

我在 `ui/page/newsChannel` 目錄下加了三個文件，分別是 `newsChannelFragment.java`、`PageViewModel.java`、`SectionsPagerAdapter.java`。代表我們需要的 Fragment 頻道展示頁面、Fragment 的 ViewModel、ViewPager 的適配器。

這裡的注意點是在 `public static NewsChannelFragment newInstance(String category, int index)` 方法中返回一個自己的 Fragment 實例對象，還有 `onCreate` 中的傳參數獲取。

```java
package com.example.toutiao.ui.page.newsChannel;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsChannelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsChannelFragment extends Fragment {
    private String mCategory;
    private int mIndex;

    public NewsChannelFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     */
    public static NewsChannelFragment newInstance(String category, int index) {
        NewsChannelFragment fragment = new NewsChannelFragment();
        Bundle bundle = new Bundle();
        bundle.putString("category", category);
        bundle.putInt("index", index);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPageViewModel = new ViewModelProvider(this).get(PageViewModel.class);
        if (getArguments() != null) {
            mCategory = getArguments().getString("category");
            mIndex = getArguments().getInt("index");
        }
        mPageViewModel.setCategory(mCategory);
        mPageViewModel.setIndex(mIndex);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_channel, container, false);
        // ...
        return view;
    }
}
```

- ui/page/newsChannel/PageViewModel.java

```java
package com.example.toutiao.ui.page.newsChannel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

/**
 * A [ViewModel] for NewsChannelFragment
 */

public class PageViewModel extends ViewModel {
    private final MutableLiveData<Integer> mIndex = new MutableLiveData<>();
    private final MutableLiveData<String> mCategory = new MutableLiveData<>();

    public MutableLiveData<String> getCategory() {
        return mCategory;
    }

    public void setCategory(String category) {
        mCategory.setValue(category);
    }

    public MutableLiveData<Integer> getIndex() {
        return mIndex;
    }

    public void setIndex(int index) {
        mIndex.setValue(index);
    }
}
```

- ui/page/newsChannel/SectionsPagerAdapter.java

這裡使用的是最新的 API：`FragmentStateAdapter`。成員變量 `mArrayList` 是我們的 `Fragment` 數組，我們渲染多少頻道就有多大。`addFragment` 方法添加新的 `fragment`。

```java
package com.example.toutiao.ui.page.newsChannel;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A [FragmentStateAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentStateAdapter {

    private final ArrayList<Fragment> mArrayList = new ArrayList<>();

    public SectionsPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    public void addFragment(Fragment fragment) {
        mArrayList.add(fragment);
    }

    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        // return your fragment that corresponds to this 'position'
        return mArrayList.get(position);
    }

    @Override
    public int getItemCount() {
        // Show total pages.
        return mArrayList.size();
    }
}
```

- ui/home/HomeFragment.java

這是最關鍵的文件，關於設定 TabLayout 和 ViewPager2。

```java
package com.example.toutiao.ui.home;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.toutiao.R;
import com.example.toutiao.activity.SearchActivity;
import com.example.toutiao.ui.page.newsChannel.NewsChannelFragment;
import com.example.toutiao.ui.page.newsChannel.SectionsPagerAdapter;
import com.example.toutiao.ui.searchBar.SearchView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link com.example.toutiao.ui.home.HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {
    private SectionsPagerAdapter mSectionsPagerAdapter;
    @StringRes
    private static final int[] TAB_TITLES = new int[]{
            R.string.title__all__,
            R.string.title_news_tech,
//        R.string.title_news_image,
            R.string.title_news_hot,
            R.string.title_news_entertainment,
            R.string.title_news_game,
            R.string.title_news_sports,
            R.string.title_news_finance,
            R.string.title_digital,
    };
    private ViewPager2 mViewPagerChannel;
    private SearchView mNewsSearchView;
    private TabLayout mTabsChannel;

//    // TODO: Rename parameter arguments, choose names that match
//
//    // TODO: Rename and change types of parameters

    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomeFragment newInstance() {
        HomeFragment fragment = new HomeFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

        Context context = container.getContext();

        // setting tabbed
        mSectionsPagerAdapter = new SectionsPagerAdapter(getChildFragmentManager(), getLifecycle());
        mViewPagerChannel = view.findViewById(R.id.view_pager_channel);

        // add Fragments in your ViewPagerFragmentAdapter class
        for (int i = 0; i < TAB_TITLES.length; i++) {
            String category = context.getString(TAB_TITLES[i]);
            mSectionsPagerAdapter.addFragment(NewsChannelFragment.newInstance(category, i));
        }
        // setting switch Orientation
        mViewPagerChannel.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        // setting adapter to view pager
        mViewPagerChannel.setAdapter(mSectionsPagerAdapter);
        mTabsChannel = view.findViewById(R.id.tabs_channel);
        // setting tabbed
        new TabLayoutMediator(mTabsChannel, mViewPagerChannel,
                (tab, position) -> tab.setText(context.getString(TAB_TITLES[position]))
        ).attach();

        return view;
    }
}
```

## RecyclerView 渲染多種不同的新聞卡片

由於一種的渲染方法比較簡單，通常是卡在顯示多種類型的 RecyclerView Item，在多種卡片 item 的設計這裡我只需要設計一種 DataModel ，然後在 DataModel 中判斷


> 待更新...








