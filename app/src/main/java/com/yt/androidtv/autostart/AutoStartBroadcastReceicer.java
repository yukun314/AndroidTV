package com.yt.androidtv.autostart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.yt.androidtv.SplashActivity;

/**
 * 开机自动启动
 * Created by zyk on 2016/3/15.
 */
public class AutoStartBroadcastReceicer extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
//        Intent i = new Intent();
//        i.setClass(context, SplashActivity.class);
//        i.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
//        context.startActivity(i);
        //后边的XXX.class就是要启动的服务
//        Intent service = new Intent(context, XXXclass);
//        context.startService(service);
//        Log.v("TAG", "开机自动服务自动启动.....");
        //启动应用，参数为需要自动启动的应用的包名
        Intent i = context.getPackageManager().getLaunchIntentForPackage("com.yt.androidtv");

        context.startActivity(i);

    }
}
