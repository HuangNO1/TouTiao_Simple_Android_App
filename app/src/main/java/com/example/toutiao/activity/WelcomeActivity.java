package com.example.toutiao.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.airbnb.lottie.LottieAnimationView;
import com.example.toutiao.R;

/**
 * WelcomeActivity is a activity showing welcome message, and then jump to MainActivity.
 */

public class WelcomeActivity extends AppCompatActivity {

    private static final int GOTO_MAIN_ACTIVITY = 0;
    /**
     * A Handler to refresh UI (Jump to MainActivity)
     */
    private final Handler mHandler = new Handler(new Handler.Callback() {
        public boolean handleMessage(android.os.Message msg) {
            if (msg.what == GOTO_MAIN_ACTIVITY) {
                Intent intent = new Intent();
                // 將原本 Activity 的換成 MainActivity
                intent.setClass(WelcomeActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }
            return true;
        }
    });

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Window mWindow = this.getWindow();
        mWindow.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        LottieAnimationView mLoadingAnimationView = findViewById(R.id.animation_view_loading);
        mLoadingAnimationView.setAnimation("news-icon-animation.json");
        mLoadingAnimationView.setSpeed(2);
        mLoadingAnimationView.playAnimation();
        mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 1450); // 1.45 秒跳轉
    }
}