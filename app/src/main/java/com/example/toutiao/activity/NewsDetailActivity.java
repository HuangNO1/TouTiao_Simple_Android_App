package com.example.toutiao.activity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

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
        if (url.substring(0, 3) == "http") {
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