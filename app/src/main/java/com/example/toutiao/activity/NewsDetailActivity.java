package com.example.toutiao.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.RenderProcessGoneDetail;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

import com.example.toutiao.R;

public class NewsDetailActivity extends AppCompatActivity {

    WebView NewsDetailWebView;
    ProgressBar progressBar;
    Button backButton;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Bundle b = getIntent().getExtras();
        String url = "";
        if(b != null) {
            url = b.getString("source_url");
        }

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(getResources().getColor(R.color.tabbed_bg));

        NewsDetailWebView = findViewById(R.id.webView);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setMax(100);
        progressBar.setProgress(1);
        setNewsDetailWebView("https://m.toutiao.com" + url);

        backButton = findViewById(R.id.backButton);
        backButtonOnClick();

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setNewsDetailWebView(String url) {

        NewsDetailWebView.setWebChromeClient(new WebChromeClient() {
            public void onProgressChanged(WebView view, int progress) {
                progressBar.setProgress(progress);
            }
        });

        NewsDetailWebView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                progressBar.setVisibility(View.VISIBLE);
                return super.shouldOverrideUrlLoading(view, url);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                progressBar.setVisibility(View.GONE);
            }
        });

        // make rendering be faster
        // no cache
        NewsDetailWebView.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
        // hardware acceleration
        NewsDetailWebView.setLayerType(View.LAYER_TYPE_HARDWARE, null);
        // enabling javascript
        NewsDetailWebView.getSettings().setJavaScriptEnabled(true);
        // enable Dom storage
        NewsDetailWebView.getSettings().setDomStorageEnabled(true);

        NewsDetailWebView.loadUrl(url);
    }

    private void backButtonOnClick() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Leave();
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (NewsDetailWebView.canGoBack()) {
            NewsDetailWebView.goBack();
        } else {
            Leave();
        }
    }

    public void Leave() {
        NewsDetailWebView.clearCache(true);
        NewsDetailWebView.clearHistory();
        NewsDetailWebView.clearFormData();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }


}