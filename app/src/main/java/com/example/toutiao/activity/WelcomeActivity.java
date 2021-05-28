package com.example.toutiao.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;

import com.airbnb.lottie.LottieAnimationView;
import com.example.toutiao.R;

public class WelcomeActivity extends AppCompatActivity {

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        Window window = this.getWindow();
        window.getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN| View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);

        LottieAnimationView animationView = findViewById(R.id.animation_view);
        animationView.setAnimation("news-icon-animation.json");
        animationView.setSpeed(2);
        animationView.playAnimation();
        mHandler.sendEmptyMessageDelayed(GOTO_MAIN_ACTIVITY, 1450); // 1.45 秒跳轉
    }

    private static final int GOTO_MAIN_ACTIVITY = 0;
    private Handler mHandler = new Handler(new Handler.Callback() {
        public boolean handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case GOTO_MAIN_ACTIVITY:
                    Intent intent = new Intent();
                    //將原本 Activity 的換成 MainActivity
                    intent.setClass(WelcomeActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    break;

                default:
                    break;
            }
            return true;
        }
    });
}