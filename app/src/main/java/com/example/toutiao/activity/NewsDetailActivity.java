package com.example.toutiao.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;

import com.example.toutiao.R;

public class NewsDetailActivity extends AppCompatActivity {

    WebView NewsDetailWebView;
    Button backButton;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        NewsDetailWebView = findViewById(R.id.webView);
        setNewsDetailWebView("https://juejin.cn/post/6844903487050874887");

        backButton = findViewById(R.id.backButton);
        backButtonOnClick();

    }

    @SuppressLint("SetJavaScriptEnabled")
    private void setNewsDetailWebView(String url) {
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
                finish();
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
    }

}