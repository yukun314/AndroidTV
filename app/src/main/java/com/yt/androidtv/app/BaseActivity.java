package com.yt.androidtv.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.List;

/**
 * Created by zyk on 2016/3/15.
 */
public class BaseActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
        window.setAttributes(params);
        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        /*
//         * 隐藏运行Android 4.0以上系统的平板的屏幕下方的状态栏
//		 */
//        try {
//            String ProcID = "79";
//            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH)
//                ProcID = "42"; // ICS
//            // 需要root 权限
//            Process proc = Runtime.getRuntime().exec(new String[]{"su", "-c", "service call activity " + ProcID + " s16 com.android.systemui"}); // WAS
//            proc.waitFor();
//        } catch (Exception ex) {
//            Toast.makeText(getApplicationContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
//        }
//        IntentFilter filter = new IntentFilter();
//        filter.addAction("android.intent.action.MAIN");
//        filter.addCategory("android.intent.category.HOME");
//        filter.addCategory("android.intent.category.DEFAULT");
//        Context context = getApplicationContext();
//        ComponentName component = new ComponentName(context.getPackageName(), BaseActivity.class.getName());
//        ComponentName[] components = new ComponentName[] {new ComponentName("com.example.launcher", "com.example.launcher.Launcher"), component};
//        PackageManager pm = getPackageManager();
//        pm.clearPackagePreferredActivities("com.example.launcher");
//        pm.addPreferredActivity(filter, IntentFilter.MATCH_CATEGORY_EMPTY, components, component);
//
//        Intent intent=new Intent(Intent.ACTION_MAIN);
//        intent.addCategory(Intent.CATEGORY_HOME);
//        List<ResolveInfo> resolveInfoList = pm.queryIntentActivities(intent, 0);


    }

    @Override
    protected void onDestroy() {
            /*
		 * 恢复运行Android 4.0以上系统的平板的屏幕下方的状态栏
		 */
        try {
            Process proc = Runtime.getRuntime().exec(new String[]{"am", "startservice", "-n", "com.android.systemui/.SystemUIService"});
            proc.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }
}
