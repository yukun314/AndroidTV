package com.yt.androidtv;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.yt.androidtv.activity.MainActivity;
import com.yt.androidtv.testjni.MathKit;
import com.yt.androidtv.testjni.StringKit;

/**
 * Created by zyk on 2016/3/15.
 */
public class SplashActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Intent intent = new Intent();
        intent.setClass(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}
