簡易今日頭條
===

此為**中南大學計算機學院** 軟件工程 18 級 大三 移動應用端開發課程 項目設計。

**指導老師：曾鋒**

項目開發需求指導書：[简易版今日头条](https://bytedance.feishu.cn/docs/doccnKvehaFYqdzDxaoOwkO6cah)

菜鳥入門指南：[Build Your First Android App in Java](https://developer.android.com/codelabs/build-your-first-android-app#0)

> 菜鳥入門指南主要是推薦給入門且**沒有接觸過安卓應用開發**的初學者去熟悉如何開發。

Code Style：[Android程式碼命名規範 - itread01](https://www.itread01.com/content/1549797666.html)、[貢獻者的AOSP Java代碼樣式 - android 官方](https://source.android.com/setup/contribute/code-style#java-language-rules)

> 一個好的項目需要良好的 Code 規範，讓別人能夠讀懂。

**如果喜歡這個作品，麻煩給個 Star ，謝謝，這是給我的動力。**

## 涉及技術點

### 底部 Nav 導覽切換頁面 Fragment

使用 **BottomNavigationView**、**NavController** 與 **NavigationUI.setupWithNavController()** 方法去實現。

現在我需要使用三個子頁面(Fragment)分別是**首頁(home)、視頻(video)、我的(account)**：

> 注意：目錄的正確需要自己去修改

- `layout/activity.xml`

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

- `navigation/mobile_navigation.xml`

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

- `layout/fragment_home.xml`、`layout/fragment_video.xml`、`layout/fragment_account.xml`

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

- `activity/MainActivity.java`

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

- `ui/home/HomeFragment.java`、`ui/video/VideoFragment.java`、`ui/account/AccountFragment.java`

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

### Swipe views with tabs 的多頻道展示

這裡我一開始使用的是**舊的 API 方法(`ViewPagerFragment`、`PagerAdapter`)**，如果你使用的是：

- androidx.viewpager.widget.ViewPager
- androidx.fragment.app.FragmentPagerAdapter

你會得到一個提示是這**兩個 API 是已經棄用(`deprecated`)**的，尤其是在寫 **`FragmentPagerAdapter`** 類的時候，Android Studio 會在父類類名上刪除線(Strikethrough)，所以我們需要切換到新的 API：

- androidx.viewpager2.widget.ViewPager2
- androidx.viewpager2.adapter.FragmentStateAdapter

那我們來開始看具體寫法，可以參考官方文檔[Create swipe views with tabs using ViewPager2 - Android developers](https://developer.android.com/guide/navigation/navigation-swipe-view-2)。

> 註：由於這個的 tabbed 項目需求的頁面是相同的，都是展示新聞卡片，**所以我們就只需要設計一個 Fragment 類**，但是如果我們需要每個新聞頻道 Tab 頁面有不同的設計，就需要設計多個 Fragment 類。

- `layout/fragment_home.xml`

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

- `layout/fragment_news_channel.xml`

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

- `ui/page/newsChannel/newsChannelFragment.java`

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

- `ui/page/newsChannel/PageViewModel.java`

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

- `ui/page/newsChannel/SectionsPagerAdapter.java`

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

- `ui/home/HomeFragment.java`

這是最關鍵的文件，關於設定 `TabLayout`和 `ViewPager2`。

```java
package com.example.toutiao.ui.home;

// ...

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

### RecyclerView 渲染多種不同的新聞卡片

由於一種的渲染方法比較簡單，通常設計者是卡在顯示多種類型的 RecyclerView Item，在多種卡片 item 的設計這裡我只需要設計一種 `DataModel` ，然後在 `DataModel` 中添加 `type` 成員變量判斷是哪種類型的卡片。

以下我舉出實現的 Example，簡化的部份 Code，這裡的 Code 去掉： HTTP 請求的渲染、Pull To ReFresh、Load More。後面會再一一舉例。

- `layout/fragment_news_channel.xml`

在需要渲染列表的地方加上 RecyclerView 組件。

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

    <LinearLayout
        android:id="@+id/linearLayout"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:orientation="vertical"
        tools:ignore="MissingClass,MissingConstraints">

        <TextView
            android:id="@+id/text_view_section_label"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:visibility="gone" />

        <androidx.recyclerview.widget.RecyclerView
            android:id="@+id/recycler_view_card_list"
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            android:layout_marginBottom="5dp"
            android:nestedScrollingEnabled="true"
            android:scrollbars="vertical" />
    </LinearLayout>

</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

- `layout/no_image_card_item.xml`、`layout/one_image_card_item.xml`、`three_images_card_item.xml`

這是我們需要渲染的三種新聞卡片，我這裡就舉出第一種卡片 `no_image_card_item`，其他的可以自己去設計，不多贅述一一放出來。

```xml
<?xml version="1.0" encoding="utf-8"?>

<androidx.cardview.widget.CardView xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:layout_margin="5dp"
    app:cardCornerRadius="10dp"
    app:cardElevation="2dp">

    <TextView
        android:id="@+id/text_view_source_url"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:visibility="gone"/>

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_marginLeft="16dp"
        android:layout_marginTop="16dp"
        android:layout_marginRight="16dp"
        android:layout_marginBottom="8dp"
        android:orientation="vertical">

        <LinearLayout
            android:layout_width="wrap_content"
            android:layout_height="wrap_content">

            <ImageView
                android:id="@+id/image_view_card_avatar"
                android:layout_width="25dp"
                android:layout_height="25dp"
                android:layout_marginEnd="10dp"
                tools:ignore="MissingConstraints"
                tools:srcCompat="@drawable/avatar_1"
                android:contentDescription="@string/avatar" />

            <TextView
                android:id="@+id/text_view_card_title"
                style="@style/TextAppearance.MaterialComponents.Headline6"
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:text="@string/title_goes_here"
                app:layout_constraintBottom_toTopOf="@+id/avatar"
                app:layout_constraintStart_toEndOf="@+id/avatar"
                app:layout_constraintTop_toBottomOf="@+id/avatar"
                tools:ignore="MissingConstraints" />

        </LinearLayout>

        <TextView
            android:id="@+id/text_view_card_subtitle"
            style="@style/TextAppearance.MaterialComponents.Caption"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:text="@string/subtitle_goes_here"
            app:layout_constraintStart_toStartOf="@+id/card_title"
            app:layout_constraintTop_toBottomOf="@+id/card_title" />

        <TextView
            android:id="@+id/text_view_card_bottom_text"
            style="@style/TextAppearance.MaterialComponents.Overline"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:text="@string/comment"
            app:layout_constraintStart_toStartOf="@+id/card_subtitle"
            app:layout_constraintTop_toBottomOf="@+id/card_subtitle" />

    </LinearLayout>
</androidx.cardview.widget.CardView>
```

- `ui/card/newsCardList/NewsCardItemDataModel.java`

我在 `ui/card/newsCardList/` 創建了兩個文件：`NewsCardItemDataModel.java`、`NewsCardAdapter.java`，分別代表卡片的數據模型和 `RecyclerView` 適配器。

我這裡寫了**三種構造類方法對應三種不同類型的卡片所需數據**，並使用 `mItemType` 判斷卡片類型。

```java
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
```

- `ui/card/newsCardList/NewsCardAdapter.java`

這是渲染卡片的 `RecyclerView` 適配器類，**三種卡片類型就會有三種 `ViewHolder` 子類**。

`onCreateViewHolder` 綁定 UI 文件，`onBindViewHolder` 綁定數據。

```java
package com.example.toutiao.ui.card.newsCardList;

// ...

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toutiao.R;
import com.example.toutiao.activity.NewsDetailActivity;
import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A card adapter to help perform to control card item's render in news channel fragment
 */

public class NewsCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<NewsCardItemDataModel> mDataModelList;
    private final Context mContext;

    public NewsCardAdapter(List<NewsCardItemDataModel> modelList, Context context) {
        mDataModelList = modelList;
        mContext = context;
    }
    @Override
    public int getItemViewType(final int position) {
        switch (mDataModelList.get(position).getItemType()) {
            case NewsCardItemDataModel.NO_IMAGE_TYPE:
                return NewsCardItemDataModel.NO_IMAGE_TYPE;
            case NewsCardItemDataModel.ONE_IMAGE_TYPE:
                return NewsCardItemDataModel.ONE_IMAGE_TYPE;
            case NewsCardItemDataModel.THREE_IMAGE_TYPE:
                return NewsCardItemDataModel.THREE_IMAGE_TYPE;
            default:
                return -1;
        }
    }

    /**
     * load more news and add to mDataModelList
     *
     * @param modelList
     */
    public void setDataModelList(List<NewsCardItemDataModel> modelList) {
        mDataModelList.addAll(modelList);
    }

    @Override
    public int getItemCount() {
        return mDataModelList.size();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case NewsCardItemDataModel.NO_IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.no_image_card_item, parent, false);
                return new NoImageCardViewHolder(view);
            case NewsCardItemDataModel.ONE_IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.one_image_card_item, parent, false);
                return new OneImageCardViewHolder(view);
            case NewsCardItemDataModel.THREE_IMAGE_TYPE:
                view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.three_images_card_item, parent, false);
                return new ThreeImageCardViewHolder(view);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(@NotNull final RecyclerView.ViewHolder holder, final int position) {
        NewsCardItemDataModel object = mDataModelList.get(position);

        if (object != null) {
            switch (object.getItemType()) {
                case NewsCardItemDataModel.NO_IMAGE_TYPE:
                    NoImageCardViewHolder holder1 = (NoImageCardViewHolder) holder;
                    holder1.bindData(object, mContext);
                    break;

                case NewsCardItemDataModel.ONE_IMAGE_TYPE:
                    OneImageCardViewHolder holder2 = (OneImageCardViewHolder) holder;
                    holder2.bindData(object, mContext);
                    break;
                case NewsCardItemDataModel.THREE_IMAGE_TYPE:
                    ThreeImageCardViewHolder holder3 = (ThreeImageCardViewHolder) holder;
                    holder3.bindData(object, mContext);
                    break;
                default:
                    break;
            }
        }
    }

    // No image style card view holder
    class NoImageCardViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mAvatarView;
        private final TextView mTitleTextView;
        private final TextView mSubTitleTextView;
        private final TextView mBottomTextView;
        private final TextView mSourceUrlTextView;

        public NoImageCardViewHolder(@NonNull View itemView) {
            super(itemView);
            mAvatarView = itemView.findViewById(R.id.image_view_card_avatar);
            mTitleTextView = itemView.findViewById(R.id.text_view_card_title);
            mSubTitleTextView = itemView.findViewById(R.id.text_view_card_subtitle);
            mBottomTextView = itemView.findViewById(R.id.text_view_card_bottom_text);
            mSourceUrlTextView = itemView.findViewById(R.id.text_view_source_url);
        }

        public void bindData(NewsCardItemDataModel dataModel, Context context) {
            Picasso.get().load(dataModel.getAvatar()).into(mAvatarView);
            // deal with title's length and subtitle's length
            String title = dataModel.getTitle();
            if (title.length() > 15) {
                title = title.substring(0, 16);
                title += "...";
            }
            mTitleTextView.setText(title);
            String subTitle = dataModel.getSubTitle();
            if (subTitle.length() > 70) {
                subTitle = subTitle.substring(0, 69);
                subTitle += "...";
            }
            mSubTitleTextView.setText(subTitle);
            mBottomTextView.setText(dataModel.getBottomText());
            mSourceUrlTextView.setText(dataModel.getDetailUrl());
        }
    }

    // One image style card view holder
    class OneImageCardViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mAvatarView;
        private final TextView mTitleTextView;
        private final TextView mSubTitleTextView;
        private final TextView mBottomTextView;
        private final TextView mSourceUrlTextView;
        private final ImageView mCardImageView;

        public OneImageCardViewHolder(@NonNull View itemView) {
            super(itemView);
            mAvatarView = itemView.findViewById(R.id.image_view_card_avatar);
            mTitleTextView = itemView.findViewById(R.id.text_view_card_title);
            mSubTitleTextView = itemView.findViewById(R.id.text_view_card_subtitle);
            mBottomTextView = itemView.findViewById(R.id.text_view_card_bottom_text);
            mSourceUrlTextView = itemView.findViewById(R.id.text_view_source_url);
            mCardImageView = itemView.findViewById(R.id.image_view_card_image);
        }

        public void bindData(NewsCardItemDataModel dataModel, Context context) {
            Picasso.get().load(dataModel.getAvatar()).into(mAvatarView);
            Picasso.get().load(dataModel.getImageDrawable()).into(mCardImageView);
            String title = dataModel.getTitle();
            // deal with title's length and subtitle's length
            if (title.length() > 15) {
                title = title.substring(0, 14);
                title += "...";
            }
            mTitleTextView.setText(title);
            String subTitle = dataModel.getSubTitle();
            if (subTitle.length() > 70) {
                subTitle = subTitle.substring(0, 69);
                subTitle += "...";
            }
            mSubTitleTextView.setText(subTitle);
            mBottomTextView.setText(dataModel.getBottomText());
            mSourceUrlTextView.setText(dataModel.getDetailUrl());
        }
    }

    // Three image style card view holder
    class ThreeImageCardViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mAvatarView;
        private final TextView mTitleTextView;
        private final TextView mSubTitleTextView;
        private final TextView mBottomTextView;
        private final TextView mSourceUrlTextView;
        private final ImageView mCardImageView1;
        private final ImageView mCardImageView2;
        private final ImageView mCardImageView3;

        public ThreeImageCardViewHolder(@NonNull View itemView) {
            super(itemView);
            mAvatarView = itemView.findViewById(R.id.image_view_card_avatar);
            mTitleTextView = itemView.findViewById(R.id.text_view_card_title);
            mSubTitleTextView = itemView.findViewById(R.id.text_view_card_subtitle);
            mBottomTextView = itemView.findViewById(R.id.text_view_card_bottom_text);
            mSourceUrlTextView = itemView.findViewById(R.id.text_view_source_url);
            mCardImageView1 = itemView.findViewById(R.id.image_view_image_1);
            mCardImageView2 = itemView.findViewById(R.id.image_view_image_2);
            mCardImageView3 = itemView.findViewById(R.id.image_view_image_3);
        }

        public void bindData(NewsCardItemDataModel dataModel, Context context) {
            Picasso.get().load(dataModel.getAvatar()).into(mAvatarView);
            ArrayList<String> images = dataModel.getThreeImageDrawable();
            Picasso.get().load(images.get(0)).into(mCardImageView1);
            Picasso.get().load(images.get(1)).into(mCardImageView2);
            Picasso.get().load(images.get(2)).into(mCardImageView3);
            // deal with title's length and subtitle's length
            String title = dataModel.getTitle();
            if (title.length() > 15) {
                title = title.substring(0, 16);
                title += "...";
            }
            mTitleTextView.setText(title);
            String subTitle = dataModel.getSubTitle();
            if (subTitle.length() > 70) {
                subTitle = subTitle.substring(0, 69);
                subTitle += "...";
            }
            mSubTitleTextView.setText(subTitle);
            mBottomTextView.setText(dataModel.getBottomText());
            mSourceUrlTextView.setText(dataModel.getDetailUrl());
        }
    }
}
```

- `ui/page/newsChannel/newsChannelFragment.java`

在需要渲染的 Fragment，添加渲染 Code。

```java
package com.example.toutiao.ui.page.newsChannel;

// ...

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.toutiao.R;
import com.example.toutiao.ui.card.newsCardList.NewsCardAdapter;
import com.example.toutiao.ui.card.newsCardList.NewsCardItemDataModel;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsChannelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsChannelFragment extends Fragment {
    private String mCategory;
    private int mIndex;
    
    private RecyclerView mCardListRecyclerView;
    private NewsCardAdapter mCardListAdapter;
    private RecyclerView.LayoutManager mCardListLayoutManager;
    private final List<NewsCardItemDataModel> mCardDataModelList = new ArrayList<>();

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
        // ...
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_channel, container, false);
        // ...
        
        for (int i = 0; i < 10; i++) {
            int type = i % 3;
            String newsId = i;
            String newsTitle = "你好";
            String newsAbstract = "我是卡片";
            int newsCommentsCount = 100;
            String newsSource = "https://example.com/e.png";
            String newsMediaAvatarUrl = "https://example.com/e.png";
            String newsSourceUrl = "www.google.com";

            if (type == NO_IMAGE_TYPE) {
                mCardDataModelList.add(new NewsCardItemDataModel(
                        NO_IMAGE_TYPE,
                        newsId,
                        newsTitle,
                        newsAbstract,
                        newsCommentsCount,
                        newsSource,
                        newsMediaAvatarUrl,
                        newsSourceUrl
                ));
            } else if (type == ONE_IMAGE_TYPE) {
                String middleImage = "https://example.com/e.png";
                mCardDataModelList.add(new NewsCardItemDataModel(
                        ONE_IMAGE_TYPE,
                        newsId,
                        newsTitle,
                        newsAbstract,
                        newsCommentsCount,
                        newsSource,
                        newsMediaAvatarUrl,
                        newsSourceUrl,
                        middleImage
                ));

            } else if (type == THREE_IMAGE_TYPE) {
                ArrayList<String> newsThreeImage = new ArrayList()<>;
                newsThreeImage.add("https://example.com/e.png");
                newsThreeImage.add("https://example.com/e.png");
                newsThreeImage.add("https://example.com/e.png");
                mCardDataModelList.add(new NewsCardItemDataModel(
                        THREE_IMAGE_TYPE,
                        newsId,
                        newsTitle,
                        newsAbstract,
                        newsCommentsCount,
                        newsSource,
                        newsMediaAvatarUrl,
                        newsSourceUrl,
                        newsThreeImage
                ));
            }
        }
        // cardList
        mCardListRecyclerView = view.findViewById(R.id.recycler_view_card_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mCardListRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mCardListLayoutManager = new LinearLayoutManager(getContext());
        mCardListRecyclerView.setLayoutManager(mCardListLayoutManager);

        // specify an adapter and pass in our data model list
        mCardListAdapter = new NewsCardAdapter(mCardDataModelList, getContext());
        mCardListRecyclerView.setAdapter(mCardListAdapter);
        return view;
    }
}
```

### 新聞數據請求與卡片渲染

關於卡片渲染細節我已經在 **RecyclerView 顯示多種寫過了**，所以這裡專注於怎麼使用 OkHttp 進行數據請求，與重新回到主 UI Thread 進行卡片渲染。

- `models/news/NewsDataModel.java`

這個類是為了請求回來的數據做一個數據模型，方便後面將數據添入 `NewsCardItemDataModel`，使邏輯更加清晰。這裡一樣有三種構造函數，分別代表三種不同的卡片類型。

```java
package com.example.toutiao.models.news;

import java.util.ArrayList;

/**
 * Data Model Class for news After requesting
 */

public class NewsDataModel {
    public static final int NO_IMAGE_TYPE = 0;
    public static final int ONE_IMAGE_TYPE = 1;
    public static final int THREE_IMAGE_TYPE = 2;
    // same
    private int mNewsCardStyleType; // three different card style
    private String mNewsId; // id
    private String mNewsTitle; // news title
    private String mNewsAbstract; // news abstract
    private int mNewsCommentsCount; // comments count
    private String mNewsSource; // author name
    private String mNewsMediaAvatarUrl; // author avatar
    private String mNewsSourceUrl; // detail page url
    // different
    private String mNewsImageUrl; // one image card style
    private ArrayList<String> mNewsThreeImage; // three image card style

    // no image style constructor
    public NewsDataModel(
            int newsCardStyleType,
            String newsId,
            String newsTitle,
            String newsAbstract,
            int newsCommentsCount,
            String newsSource,
            String newsMediaAvatarUrl,
            String newsSourceUrl
    ) {
        mNewsCardStyleType = newsCardStyleType;
        mNewsId = newsId;
        mNewsTitle = newsTitle;
        mNewsAbstract = newsAbstract;
        mNewsCommentsCount = newsCommentsCount;
        mNewsSource = newsSource;
        mNewsMediaAvatarUrl = newsMediaAvatarUrl;
        mNewsSourceUrl = newsSourceUrl;
    }

    // one image style constructor
    public NewsDataModel(
            int newsCardStyleType,
            String newsId,
            String newsTitle,
            String newsAbstract,
            int newsCommentsCount,
            String newsSource,
            String newsMediaAvatarUrl,
            String newsSourceUrl,
            String newsImageUrl
    ) {
        mNewsCardStyleType = newsCardStyleType;
        mNewsId = newsId;
        mNewsTitle = newsTitle;
        mNewsAbstract = newsAbstract;
        mNewsCommentsCount = newsCommentsCount;
        mNewsSource = newsSource;
        mNewsMediaAvatarUrl = newsMediaAvatarUrl;
        mNewsSourceUrl = newsSourceUrl;
        mNewsImageUrl = newsImageUrl;
    }

    // three image style constructor
    public NewsDataModel(
            int newsCardStyleType,
            String newsId,
            String newsTitle,
            String newsAbstract,
            int newsCommentsCount,
            String newsSource,
            String newsMediaAvatarUrl,
            String newsSourceUrl,
            ArrayList<String> newsThreeImage
    ) {
        mNewsCardStyleType = newsCardStyleType;
        mNewsId = newsId;
        mNewsTitle = newsTitle;
        mNewsAbstract = newsAbstract;
        mNewsCommentsCount = newsCommentsCount;
        mNewsSource = newsSource;
        mNewsMediaAvatarUrl = newsMediaAvatarUrl;
        mNewsSourceUrl = newsSourceUrl;
        mNewsThreeImage = newsThreeImage;
    }

    public int getNewsCardStyleType() {
        return mNewsCardStyleType;
    }

    public void setNewsCardStyleType(int newsCardStyleType) {
        mNewsCardStyleType = newsCardStyleType;
    }

    public String getNewsId() {
        return mNewsId;
    }

    public void setNewsId(String newsId) {
        mNewsId = newsId;
    }

    public String getNewsTitle() {
        return mNewsTitle;
    }

    public void setNewsTitle(String newsTitle) {
        mNewsTitle = newsTitle;
    }

    public String getNewsAbstract() {
        return mNewsAbstract;
    }

    public void setNewsAbstract(String newsAbstract) {
        mNewsAbstract = newsAbstract;
    }

    public int getNewsCommentsCount() {
        return mNewsCommentsCount;
    }

    public void setNewsCommentsCount(int newsCommentsCount) {
        mNewsCommentsCount = newsCommentsCount;
    }

    public String getNewsSource() {
        return mNewsSource;
    }

    public void setNewsSource(String newsSource) {
        mNewsSource = newsSource;
    }

    public String getNewsMediaAvatarUrl() {
        return mNewsMediaAvatarUrl;
    }

    public void setNewsMediaAvatarUrl(String newsMediaAvatarUrl) {
        mNewsMediaAvatarUrl = newsMediaAvatarUrl;
    }

    public String getNewsSourceUrl() {
        return mNewsSourceUrl;
    }

    public void setNewsSourceUrl(String newsSourceUrl) {
        mNewsSourceUrl = newsSourceUrl;
    }

    public String getNewsImageUrl() {
        return mNewsImageUrl;
    }

    public void setNewsImageUrl(String newsImageUrl) {
        mNewsImageUrl = newsImageUrl;
    }

    public ArrayList<String> getNewsThreeImage() {
        return mNewsThreeImage;
    }

    /*
     * add a image to mNewsThreeImage array list
     */
    public void setNewsThreeImage(String newsImageUrl) {
        this.mNewsThreeImage.add(newsImageUrl);
    }
}
```

- `ui/page/newsChannel/newsChannelFragment.java`

這裡是我們需要進行新聞內容請求的 Fragment。我使用的是 OkHttp 的方法，如果不知道 OkHttp 可以去 Github 看看 [square/okhttp - github](https://github.com/square/okhttp)。

引入依賴需要在 `build.gradle` 中加上下面一段話：

```
dependencies {
	// ...
    implementation 'com.squareup.okhttp3:okhttp:4.9.1'
}
```

**注意：為了能夠進行網路請求需要在 `/manifests/AndroidManifest.xml` 中加上  `<uses-permission android:name="android.permission.INTERNET" />` 這段話。**

```xml
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example.toutiao">

    <uses-permission android:name="android.permission.INTERNET" />
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
// ...
```


下面的 Code 中我分別使用 `getUserAgent()` 固定 UA，`getInitNews()` 請求新聞數據，`initRenderCardList()` 做數據渲染，`dealWithResponseBody()` 處理請求回來的 `ResponseBody`，`dealWithNewsObject()` 處理每個新聞列表，`runThread()` 負責請求後回到在主 UI 線程裡面進行渲染（也就是`initRenderCardList()`），這裡的注意點有：

1. 除了固定 UA 外，需要將請求回來的 **`Response` 中 `Header` 的 `Set-Cookie`** 加入下一次請求中的 Cookie，這樣避免了**請求回來的數據與原數據重複性**。

2. 請求必須使用多線程的方式進行異步請求，不能在 UI 主線程請求，這是新的 Android 規定，所以我建議可以使用 **`AsyncTask`** 或是 **Okhttp 的異步回調方法**。我下面的 Example 是使用了後者。

3. OkHttp 中**異步請求的 `response.body().string()` 只能使用一次**，使用完一次後就會將回應 Body 刪除節省資源（我也不知道為什麼要這樣設計），所以需要先存成 `String` 類型，再接著使用。

4. 注意**處理每一次數據，檢查是否有些數據不完整**，或是 ResponseBody 是空的情況都需要考慮，如果沒有考慮到，就會經常在運行項目時**產生 Crash**。

```java
package com.example.toutiao.ui.page.newsChannel;

// ...

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.toutiao.ui.card.newsCardList.NewsCardItemDataModel.NO_IMAGE_TYPE;
import static com.example.toutiao.ui.card.newsCardList.NewsCardItemDataModel.ONE_IMAGE_TYPE;
import static com.example.toutiao.ui.card.newsCardList.NewsCardItemDataModel.THREE_IMAGE_TYPE;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewsChannelFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewsChannelFragment extends Fragment {
    private final static String BASE_URL =
            "https://www.toutiao.com/api/pc/feed/?max_behot_time=%d&category=%s";
    private static final String[] CATEGORY_ATTR = new String[]{
            "__all__",
            "news_tech",
//            "news_image",
            "news_hot",
            "news_entertainment",
            "news_game",
            "news_sports",
            "news_finance",
            "digital"
    };
    private final static String DEFAULT_AVATAR =
            "https://img.88icon.com/download/jpg/20200901/84083236c883964781afea41f1ea4e9c_512_511.jpg!88bg";
    private final static String DEFAULT_IMAGE =
            "https://www.asiapacdigital.com/Zh_Cht/img/ap/services/reseller/TouTiao_1.jpg";
    private ArrayList<NewsDataModel> mNewsDataModelList = new ArrayList<>();
    private PageViewModel mPageViewModel;
    private RecyclerView mCardListRecyclerView;
    private NewsCardAdapter mCardListAdapter;
    private RecyclerView.LayoutManager mCardListLayoutManager;
    private Boolean mIsScrollToTop = false;
    private final List<NewsCardItemDataModel> mCardDataModelList = new ArrayList<>();
    private String mCategory;
    private int mIndex;
    private boolean mIsRefresh = false;
    private boolean mIsLoadMore = false;
    private boolean mIsLoadingFail = false;
    private int mMaxBehotTime = 0;
    private String mCookie;

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
        // cardList
        mCardListRecyclerView = view.findViewById(R.id.recycler_view_card_list);

        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mCardListRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mCardListLayoutManager = new LinearLayoutManager(getContext());
        mCardListRecyclerView.setLayoutManager(mCardListLayoutManager);

        // specify an adapter and pass in our data model list
        mCardListAdapter = new NewsCardAdapter(mCardDataModelList, getContext());
        mCardListRecyclerView.setAdapter(mCardListAdapter);

        TextView mSectionLabelTextView = view.findViewById(R.id.text_view_section_label);

            try {
                // init
                getInitNews();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

        mPageViewModel.getCategory().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                mSectionLabelTextView.setText(s);
            }
        });

        return view;
    }

    // render the recycler view card list when init and refreshing
    public void initRenderCardList() {
        Log.v("start init render", "render init card, news list size: " + mNewsDataModelList.size());
        for (int i = 0; i < mNewsDataModelList.size(); i++) {
            int type = mNewsDataModelList.get(i).getNewsCardStyleType();
            String newsId = mNewsDataModelList.get(i).getNewsId();
            String newsTitle = mNewsDataModelList.get(i).getNewsTitle();
            String newsAbstract = mNewsDataModelList.get(i).getNewsAbstract();
            int newsCommentsCount = mNewsDataModelList.get(i).getNewsCommentsCount();
            String newsSource = mNewsDataModelList.get(i).getNewsSource();
            String newsMediaAvatarUrl = mNewsDataModelList.get(i).getNewsMediaAvatarUrl();
            String newsSourceUrl = mNewsDataModelList.get(i).getNewsSourceUrl();

            if (type == NO_IMAGE_TYPE) {
                mCardDataModelList.add(new NewsCardItemDataModel(
                        NO_IMAGE_TYPE,
                        newsId,
                        newsTitle,
                        newsAbstract,
                        newsCommentsCount,
                        newsSource,
                        newsMediaAvatarUrl,
                        newsSourceUrl
                ));
            } else if (type == ONE_IMAGE_TYPE) {
                String middleImage = mNewsDataModelList.get(i).getNewsImageUrl();
                mCardDataModelList.add(new NewsCardItemDataModel(
                        ONE_IMAGE_TYPE,
                        newsId,
                        newsTitle,
                        newsAbstract,
                        newsCommentsCount,
                        newsSource,
                        newsMediaAvatarUrl,
                        newsSourceUrl,
                        middleImage
                ));

            } else if (type == THREE_IMAGE_TYPE) {
                ArrayList<String> newsThreeImage = mNewsDataModelList.get(i).getNewsThreeImage();
                mCardDataModelList.add(new NewsCardItemDataModel(
                        THREE_IMAGE_TYPE,
                        newsId,
                        newsTitle,
                        newsAbstract,
                        newsCommentsCount,
                        newsSource,
                        newsMediaAvatarUrl,
                        newsSourceUrl,
                        newsThreeImage
                ));
            }
        }
        mLoadingAnimationView.setVisibility(View.GONE);
        mScreenMaskView.setVisibility(View.GONE);
        mCardListRefreshLayout.finishRefreshing();
        mCardListAdapter = new NewsCardAdapter(mCardDataModelList, getContext());
        mCardListRecyclerView.setAdapter(mCardListAdapter);

        mIsRefresh = false;
    }

    private String getUserAgent() {
        String userAgent = "";
        try {
            userAgent = WebSettings.getDefaultUserAgent(getContext());
        } catch (Exception e) {
            userAgent = System.getProperty("https.agent");
        }
        StringBuffer sb = new StringBuffer();
        for (int i = 0, length = userAgent.length(); i < length; i++) {
            char c = userAgent.charAt(i);
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", (int) c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * a methods to init card list
     *
     * @throws IOException
     * @throws JSONException
     */
    public void getInitNews() throws IOException, JSONException {
        // init data
        mNewsDataModelList.clear();
        mCardDataModelList.clear();
        mLoadingAnimationView.setVisibility(View.VISIBLE);
        mMaxBehotTime = 0;
        // setting request
        OkHttpClient client = new OkHttpClient();
        Log.v("request url", String.format(Locale.ENGLISH, BASE_URL, mMaxBehotTime, CATEGORY_ATTR[mIndex]));
        Request request = new Request.Builder()
                .get()
                .url(String.format(Locale.ENGLISH, BASE_URL, mMaxBehotTime, CATEGORY_ATTR[mIndex]))
                .addHeader("User-Agent", getUserAgent())
                .build();
        Call call = client.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull final Response response) throws IOException {
                Log.v("json status", " " + response.code());
                Log.v("json body", " " + (response.body() != null));
                String jsonData = response.body().string();
                // deal with request body
                dealWithResponseBody(jsonData);
                mCookie = response.header("Set-Cookie");
                Log.v("cookie", "set cookie: " + mCookie);
            }
        });
    }

    /**
     * @param jsonData
     */
    public void dealWithResponseBody(String jsonData) {
        // avoid that jsonData is null
        if(jsonData.length() < 1) {
            mIsLoadingFail = true;
            // run on main ui thread
            runThread();
            return;
        }
        Log.v("deal with response", "string to JsonObject");
        Log.v("deal with response", "json data\n" + jsonData);
        JsonObject result = JsonParser.parseString(jsonData).getAsJsonObject();
        Log.v("deal with response", "result, before max_behot_time " + mMaxBehotTime);
        Log.v("deal with response", String.valueOf(result.getAsJsonObject("next").has("max_behot_time")));
        mMaxBehotTime = result.getAsJsonObject("next").get("max_behot_time").getAsInt();
        Log.v("deal with response", "After max_behot_time : " + mMaxBehotTime + " get news object");
        JsonArray newsData = result.getAsJsonArray("data");
        for (int i = 0; i < newsData.size(); i++) {
            Log.v("deal with response", "newsObjects " + i);
            try {
                dealWithNewsObject(newsData.get(i).getAsJsonObject());
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        runThread();
    }

    /**
     * running on main UI thread to render card list
     */
    private void runThread() {
        Handler handler = new Handler(Looper.getMainLooper());

        new Thread() {
            public void run() {
                // init & refresh
                handler.post(() -> initRenderCardList());
            }
        }.start();
    }

    /**
     * a methods to transfer JsonObject to NewsDataModel
     *
     * @param object
     * @throws JSONException
     */
    public void dealWithNewsObject(JsonObject object) throws JSONException {
        NewsDataModel temp;
        // id
        Log.v("deal with news object", "news_id " + object.get("group_id").getAsString());
        String newsId = object.get("group_id").getAsString();
        // title
        Log.v("deal with news object", "news_title " + object.get("title").getAsString());
        String newsTitle = object.get("title").getAsString();
        // remove \r \n \t
        newsTitle = newsTitle.replaceAll("\r|\n|\t", "");
        // abstract
        String newsAbstract = newsTitle;
        Log.v("deal with news object", "news_abstract " + object.has("abstract"));
        if (object.has("abstract")) {
            newsAbstract = object.get("abstract").getAsString();
            // remove \r \n \t
            newsAbstract = newsAbstract.replaceAll("\r|\n|\t", "");
        }
        // comments count
        Log.v("deal with news object", "news_comments_count " + object.has("comments_count"));
        int newsCommentsCount = 0;
        if (object.has("comments_count")) {
            newsCommentsCount = object.get("comments_count").getAsInt();
        }
        // news source
        Log.v("deal with news object", "news_source " + object.get("source").getAsString());
        String newsSource = object.get("source").getAsString();
        // news media avatar url
        String newsMediaAvatarUrl = DEFAULT_AVATAR;
        if (object.has("media_avatar_url")) {
            newsMediaAvatarUrl = "https:" + object.get("media_avatar_url").getAsString();
        }
        // news source url
        Log.v("deal with news object", "news_source_url " + object.get("source_url").getAsString());
        String newsSourceUrl = object.get("source_url").getAsString();

        Log.v("deal with news object", "have image_list " + object.has("image_list"));
        Log.v("deal with news object", "single_mode " + object.get("single_mode").getAsBoolean());
        // three image style
        if (object.has("image_list")) {
            JsonArray imageList = object.get("image_list").getAsJsonArray();
            ArrayList<String> newsThreeImage = new ArrayList<>();

            // the Json Array is not null
            if(imageList.size() < 3) {
                for (int i = 0; i < 3; i++) {
                    // avoid url is null
                    newsThreeImage.add(DEFAULT_IMAGE);
                }
            } else {
                for (int i = 0; i < 3; i++) {
                    // avoid url is null
                    String url = imageList.get(i).getAsJsonObject().get("url").getAsString();
                    if(url.length() == 0) {
                        url = DEFAULT_IMAGE;
                    } else {
                        url = "https:" + url;
                    }
                    newsThreeImage.add(url);
                }
            }
            temp = new NewsDataModel(
                    NewsDataModel.THREE_IMAGE_TYPE,
                    newsId,
                    newsTitle,
                    newsAbstract,
                    newsCommentsCount,
                    newsSource,
                    newsMediaAvatarUrl,
                    newsSourceUrl,
                    newsThreeImage
            );
        }
        // one image style
        else if (object.get("single_mode").getAsBoolean()) {
            String middleImage = "https:" + object.get("image_url").getAsString();

            temp = new NewsDataModel(
                    NewsDataModel.ONE_IMAGE_TYPE,
                    newsId,
                    newsTitle,
                    newsAbstract,
                    newsCommentsCount,
                    newsSource,
                    newsMediaAvatarUrl,
                    newsSourceUrl,
                    middleImage
            );
        }
        // no image style
        else {
            temp = new NewsDataModel(
                    NewsDataModel.NO_IMAGE_TYPE,
                    newsId,
                    newsTitle,
                    newsAbstract,
                    newsCommentsCount,
                    newsSource,
                    newsMediaAvatarUrl,
                    newsSourceUrl
            );
        }
        mNewsDataModelList.add(temp);
    }
}
```

### 圖片緩存與懶加載

圖片的緩存最直接的方法就是將圖片下載到本地緩存，然後顯示到圖片，然而網上有一個 API 是 [ square/picasso - Github](https://github.com/square/picasso)，實現原理可以看 [Picasso从使用到原理详解 - JasonWang's Blog](http://sniffer.site/2017/04/20/Picasso%E4%BB%8E%E4%BD%BF%E7%94%A8%E5%88%B0%E5%8E%9F%E7%90%86%E8%AF%A6%E8%A7%A3/)，這篇文章講的很好。**Picasso 可以將我們的圖片進行二級緩存**，並檢查內存是否有這張圖片，如果有就無需下載，沒有就進行 OkHttp 在線程池的子線程請求（也就是異步請求回調函數使用的線程）。

我是將圖片緩存寫到了 `ui/card/newsCardList/NewsCardItemDataModel.java` 中的 `RecyclerView.ViewHolder` 子類中 `bindData()`，這裡就只寫一下簡單的使用方式。

```java
Picasso.get().load(dataModel.getAvatar()).into(mAvatarView);
```

### Pull To Refresh 刷新新聞內容

刷新的部份我是推薦使用 AndroidX 官方的方法： `Swiperefreshlayout`，具體說明可參考官方的 [Swiperefreshlayout - Android Developers](https://developer.android.com/jetpack/androidx/releases/swiperefreshlayout)，雖然我一開始有考慮過網上一堆酷炫的第三方刷新頭，但是那些東西其實很多都是過時的，並不支持 AndroidX，使用上也有很多問題，或許你覺得 `Swiperefreshlayout` 很丑？但是包括 Bilibili 等手機應用大都是使用這個組件。而我目前使用的是 Github 上別人的開源項目 [tuesda/CircleRefreshLayout - Github](https://github.com/tuesda/CircleRefreshLayout)，因為這項目是已經過時的，所以我自己將該項目下載下來自己修改過時的 API，將其套用在自己的項目上，然而這項目依然有一些問題存在，但作者似乎也沒打算更新？

這裡就介紹如何用 Swiperefreshlayout。也可以看 [Implementing Pull to Refresh Guide - CODEPATH](https://guides.codepath.com/android/implementing-pull-to-refresh-guide)

首先要加上依賴

```
dependencies {
    implementation "androidx.swiperefreshlayout:swiperefreshlayout:1.1.0"
}
```

接著在 xml 中需要刷新渲染的 `RecyclerView` 或是 `ListView` 加上該組件。

```xml
<!-- ... -->
<androidx.swiperefreshlayout.widget.SwipeRefreshLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/swiperefresh"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

    <androidx.recyclerview.widget.RecyclerView
        android:id="@android:id/list"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</androidx.swiperefreshlayout.widget.SwipeRefreshLayout>
<!-- ... -->
```

下拉刷新組件都會有個**監聽刷新動作的方法**讓你使用，你可以在組件的相關說明文檔找到。在你的刷新地方進行刷新監聽。

```java
public class TimelineActivity extends Activity {
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Only ever call `setContentView` once right at the top
        setContentView(R.layout.activity_main);
        // Lookup the swipe container view
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                refreshData();
            } 

        });

        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright, 
                android.R.color.holo_green_light, 
                android.R.color.holo_orange_light, 
                android.R.color.holo_red_light);
    }
}
```

### Load More 加載更多

這裡需要使用 `RecyclerView` 的監聽滾動方法，當無法在往下滾動的時候，就調用加載更多的方法。

以下是 `RecyclerView` 設置滾動監聽的參考案例：

這裡使用了 `addOnScrollListener` 方法做滾動監聽，接著在 `onScrolled` 方法中做處理，`!recyclerView.canScrollVertically(1)` 代表 `recyclerView` 已經無法繼續往下滾了。

```java
// cardList
mCardListRecyclerView = view.findViewById(R.id.recycler_view_card_list);
mCardListRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
    @Override
    public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);
    }

    @Override
    public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (!recyclerView.canScrollVertically(1)) { // 1 for down
            try {
                loadMoreNews();
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
        }
    }
});
```

### 點擊新聞卡片進入新聞詳情頁與 WebView 顯示

這裡我是在新聞卡片的適配器裡面去做，將 View 設置點擊事件，然後新增一個新聞活動頁面。

- `ui/card/newsCardList/NewsCardAdapter.java`

下面的 Code 中我舉出 `NoImageCardViewHolder` 子類，裡面的構造函數中去做 `itemView` 的點擊事件，點擊事件涉及到了傳參部份，傳參使用 `intent.putExtra()` 方法，然後在 `NewsDetailActivity` 做接收參數。

```java
public class NewsCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final List<NewsCardItemDataModel> mDataModelList;
    private final Context mContext;
    
    // ...
    
    /**
     * Click the card item and move to NewsDetailActivity
     *
     * @param itemView
     */
    public void onClickListener(@NonNull View itemView) {
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Activity activity = (Activity) mContext;
                TextView mSourceUrl = itemView.findViewById(R.id.text_view_source_url);
                Intent intent = new Intent(activity, NewsDetailActivity.class);
                intent.putExtra("source_url", mSourceUrl.getText().toString());
                mContext.startActivity(intent);
                activity.overridePendingTransition(R.animator.slide_in_right, R.animator.slide_out_left);
            }
        });
    }
    
    // No image style card view holder
    class NoImageCardViewHolder extends RecyclerView.ViewHolder {
        private final ImageView mAvatarView;
        private final TextView mTitleTextView;
        private final TextView mSubTitleTextView;
        private final TextView mBottomTextView;
        private final TextView mSourceUrlTextView;

        public NoImageCardViewHolder(@NonNull View itemView) {
            super(itemView);
            mAvatarView = itemView.findViewById(R.id.image_view_card_avatar);
            mTitleTextView = itemView.findViewById(R.id.text_view_card_title);
            mSubTitleTextView = itemView.findViewById(R.id.text_view_card_subtitle);
            mBottomTextView = itemView.findViewById(R.id.text_view_card_bottom_text);
            mSourceUrlTextView = itemView.findViewById(R.id.text_view_source_url);
            // set onClick
            onClickListener(itemView);
        }

        public void bindData(NewsCardItemDataModel dataModel, Context context) {
            // ...
        }
    }
    
}
```

- `layout/activity_news_detail.xml`

這個是 `NewsDetailActivity` 的 `xml` Layout 文件。

```xml
<?xml version="1.0" encoding="utf-8"?>
<androidx.coordinatorlayout.widget.CoordinatorLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".activity.NewsDetailActivity">
    <com.google.android.material.appbar.AppBarLayout
        android:id="@+id/app_bar"
        android:layout_height="40dp"
        android:layout_width="match_parent"
        android:background="@color/tabbed_bg">

        <com.google.android.material.appbar.CollapsingToolbarLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            app:toolbarId="@+id/toolbar"
            app:layout_scrollFlags="scroll|exitUntilCollapsed">

            <androidx.appcompat.widget.Toolbar
                android:id="@+id/tool_bar"
                android:layout_height="40dp"
                android:layout_width="match_parent">

                <androidx.appcompat.widget.LinearLayoutCompat
                    android:layout_width="wrap_content"
                    android:layout_height="wrap_content">
                    <Button
                        android:id="@+id/button_back"
                        android:layout_width="25dp"
                        android:layout_height="25dp"
                        android:layout_margin="1dp"
                        android:backgroundTint="@color/white"
                        app:layout_constraintStart_toStartOf="parent"
                        app:layout_constraintBottom_toBottomOf="parent"
                        android:background="@drawable/ic_baseline_arrow_back_ios_24"/>
                </androidx.appcompat.widget.LinearLayoutCompat>

            </androidx.appcompat.widget.Toolbar>
        </com.google.android.material.appbar.CollapsingToolbarLayout>
    </com.google.android.material.appbar.AppBarLayout>
    <com.airbnb.lottie.LottieAnimationView
        android:id="@+id/animation_view_loading"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:scaleX="0.7"
        android:scaleY="0.7"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:lottie_autoPlay="true"
        app:lottie_loop="true" />
    <androidx.core.widget.NestedScrollView
        android:id="@+id/nested_scroll_view_web"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        app:layout_behavior="com.google.android.material.appbar.AppBarLayout$ScrollingViewBehavior">

        <FrameLayout
            android:layout_width="match_parent"
            android:layout_height="match_parent"
            tools:context=".ui.page.news_detail.NewsDetailFragment">

            <WebView
                android:id="@+id/web_view"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                app:layout_constraintLeft_toLeftOf="parent"
                app:layout_constraintRight_toRightOf="parent" />

            <ProgressBar
                android:id="@+id/progress_bar_loading"
                style="@style/Widget.AppCompat.ProgressBar.Horizontal"
                android:layout_width="match_parent"
                android:layout_height="2dp"
                app:layout_constraintTop_toTopOf="parent"/>

        </FrameLayout>

    </androidx.core.widget.NestedScrollView>

    <com.google.android.material.floatingactionbutton.FloatingActionButton
        android:id="@+id/fab_scroll_to_top"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_gravity="bottom|end"
        android:layout_marginEnd="10dp"
        android:layout_marginBottom="30dp"
        android:backgroundTint="@color/red_200"
        android:clickable="true"
        android:contentDescription="@string/scrolltotop"
        android:focusable="true"
        android:isScrollContainer="false"
        android:src="@drawable/ic_baseline_keyboard_arrow_up_24"
        android:tint="@color/white"
        app:borderWidth="0dp" />
</androidx.coordinatorlayout.widget.CoordinatorLayout>
```

- `activity/NewsDetailActivity.java`

在 `onCreate()` 中用 `Bundle args = getIntent().getExtras();` 獲取傳來的 `source_url`，並設置 `WebView` 顯示該連結。

```java
package com.example.toutiao.activity;

// ...

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.NestedScrollView;

import com.airbnb.lottie.LottieAnimationView;
import com.example.toutiao.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

/**
 * A Activity to be showed news detail.
 */

public class NewsDetailActivity extends AppCompatActivity {

    private WebView mNewsDetailWebView;
    private ProgressBar mProgressBar;
    private Button mBackButton;
    private LottieAnimationView mLoadingAnimationView;
    private FloatingActionButton mScrollToTopFAB;
    private NestedScrollView mWebNestedScrollView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Bundle args = getIntent().getExtras();
        String url = "";
        if (args != null) {
            url = args.getString("source_url");
        }

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.tabbed_bg));

        mLoadingAnimationView = findViewById(R.id.animation_view_loading);
        mLoadingAnimationView.setAnimation("load-animation.json");
        mLoadingAnimationView.setSpeed(1);
        mLoadingAnimationView.playAnimation();

        mNewsDetailWebView = findViewById(R.id.web_view);
        mProgressBar = findViewById(R.id.progress_bar_loading);
        mProgressBar.setMax(100);
        mProgressBar.setProgress(1);
        // avoid the url has "http"
        if (url.contains("http")) {
            setNewsDetailWebView(url);
        } else {
            setNewsDetailWebView("https://m.toutiao.com" + url);
        }


        mBackButton = findViewById(R.id.button_back);
        setBackButtonOnClick();

        mWebNestedScrollView = findViewById(R.id.nested_scroll_view_web);
        mWebNestedScrollView.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY == 0) {
                    // hide FAB when NestedScrollView is at the top
                    mScrollToTopFAB.hide();
                } else {
                    mScrollToTopFAB.show();
                }
            }
        });

        mScrollToTopFAB = findViewById(R.id.fab_scroll_to_top);
        mScrollToTopFAB.hide();
        mScrollToTopFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // scroll to top
                mWebNestedScrollView.smoothScrollTo(0, 0);
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setNewsDetailWebView(String url) {

        // TODO: Make WebView Faster

        mNewsDetailWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                mProgressBar.setProgress(progress);
            }
        });

        mNewsDetailWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                mProgressBar.setVisibility(View.VISIBLE);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                mLoadingAnimationView.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.GONE);
            }
        });

        mNewsDetailWebView.getSettings().setAppCacheEnabled(true);
        mNewsDetailWebView.getSettings().setLoadsImagesAutomatically(true);
        mNewsDetailWebView.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        // hardware acceleration
        mNewsDetailWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        // enabling javascript
        mNewsDetailWebView.getSettings().setJavaScriptEnabled(true);
        // enable Dom storage
        mNewsDetailWebView.getSettings().setDomStorageEnabled(true);

        mNewsDetailWebView.loadUrl(url);
    }

    private void setBackButtonOnClick() {
        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Leave();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (mNewsDetailWebView.canGoBack()) {
            mNewsDetailWebView.goBack();
        } else {
            Leave();
        }
    }

    /**
     * back to MainActivity
     */
    public void Leave() {
//        mNewsDetailWebView.clearCache(true);
//        mNewsDetailWebView.clearHistory();
//        mNewsDetailWebView.clearFormData();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }
}
```

## 項目心得

由於這次的項目因為我本身是安卓零基礎，而我卻使用了一週的時間將整體的 Code 邏輯架構寫好了，這是因為我會使用 Google 用關鍵字搜索，StackOverFlow 大多數時候都能給我比較好的答案，在做項目之前我頂多是有了做過 Qt C++ 的基礎，所以了解了一下安卓的組件生命週期還有哪些控件和布局，我就直接邊做邊學，我覺得這樣子對我的成長和動手能力提升很多，也謝謝字節跳動客戶端開發的大哥們一直提點我的不足。最終得以完成這個項目。

## License

[The MIT License](https://github.com/HuangNO1/TouTiao_Simple_Android_App/blob/master/LICENSE)





