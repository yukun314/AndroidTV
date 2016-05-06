package com.yt.androidtv.activity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.UserHandle;
import android.os.UserManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.yt.androidtv.R;
import com.yt.androidtv.app.BaseActivity;
import com.yt.androidtv.widget.SmoothGridView;
import com.yt.androidtv.widget.ViewBorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by zyk on 2016/4/29
 * 我的应用 显示用户安装的应用.
 */
public class MyAppActivity extends BaseActivity {

    private LayoutInflater mInflater;
    private PackageManager mPackageManager;
    private SmoothGridView mGridView;
    private ViewBorder mViewBorder;
    private MyAdapter mAdapter;
    private List<PackageInfo> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myapp);
        mInflater = LayoutInflater.from(getApplicationContext());
        mData = new ArrayList<PackageInfo>();
        init();
    }

    private void init() {
        mViewBorder = (ViewBorder) findViewById(R.id.activity_myapp_viewborder);
        mViewBorder.setUpRectResource(R.drawable.white_light_10);
        mViewBorder.setShadowResource(R.drawable.item_shadow);
        mViewBorder.setDrawUpRectPadding(10);
        mViewBorder.setDrawUpRectEnabled(true);


        mGridView = (SmoothGridView) findViewById(R.id.activity_myapp_gridview);
        mAdapter = new MyAdapter(mGridView);
        mGridView.setAdapter(mAdapter);
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                PackageInfo info = mData.get(position);
                Intent intent = getStartAppIntent(info);
                if( intent == null){
                    Toast.makeText(MyAppActivity.this,"\""+mPackageManager.getApplicationLabel(info.applicationInfo)+"\""+"没有指定启动的Activity,无法启动",Toast.LENGTH_SHORT).show();
                    return ;
                }
                startActivity(intent);
            }
        });

        mGridView.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                mViewBorder.setFocusView(newFocus,oldFocus,1.2f);
            }
        });

        mGridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        initData();
    }

    //启动应用的Intent
    private Intent getStartAppIntent(PackageInfo info) {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_LAUNCHER);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);

        String className = getClassName(intent, info.packageName);
        if(className == null){
            return null;
        }
        intent.setComponent(new ComponentName(info.packageName, className));

        long serialNumber = -1;
        //17
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            UserManager mUserManager = (UserManager)getSystemService(Context.USER_SERVICE);
            mUserManager.getSerialNumberForUser(android.os.Process.myUserHandle());
        }
        intent.putExtra("profile", serialNumber);
        return intent;
    }

    //卸载应用的Intent
    private Intent  getUninstallAppIntent(PackageInfo info) {
        if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0) {
            // System applications cannot be installed. For now, show a toast explaining that.
            // We may give them the option of disabling apps this way.
            Toast.makeText(this, "这是系统应用，无法卸载", Toast.LENGTH_SHORT).show();
            return null;
        } else {
            Intent intent = new Intent(Intent.ACTION_DELETE);
            String className = getClassName(intent, info.packageName);
            intent = new Intent(
                    Intent.ACTION_DELETE, Uri.fromParts("package", info.packageName, className));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
                    Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                intent.putExtra("android.intent.extra.USER", android.os.Process.myUserHandle());
            }
            return intent;
        }
    }

    private String getClassName(Intent intent, String packageName){
        String mainAct = null;
        List<ResolveInfo> list = mPackageManager.queryIntentActivities(intent,PackageManager.MATCH_ALL);
        int count = list.size();
        for(int i = 0;i<count;i++){
            ResolveInfo rinfo = list.get(i);
            if (rinfo.activityInfo.packageName.equals(packageName)) {
                mainAct = rinfo.activityInfo.name;
                break;
            }
        }
        return mainAct;
    }

    private void initData(){
        mData.clear();
        mPackageManager = getPackageManager();
        List<PackageInfo> list = mPackageManager.getInstalledPackages(PackageManager.GET_ACTIVITIES);
        int count = list.size();
        for(int i = 0; i<count;i++) {
            PackageInfo info = list.get(i);
            if ((info.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
                mData.add(info);
            }
        }
        if(mData.size() > 0) {
            mAdapter.notifyDataSetChanged();
        }
    }

    private class MyAdapter extends BaseAdapter {

        private final SmoothGridView mGridview;
        MyAdapter(SmoothGridView sgv){
            this.mGridview = sgv;
        }

        @Override
        public int getCount() {
            return mData.size();
        }

        @Override
        public Object getItem(int position) {
            return mData.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = mInflater.inflate(R.layout.activity_myapp_item, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.activity_myapp_item_name);
//                holder.icon = (ImageView) convertView.findViewById(R.id.);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mGridview.setSelection(position);
                        mGridview.performItemClick(v,position,getItemId(position));
                    }
                });

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            PackageInfo info = mData.get(position);

            holder.title.setText(mPackageManager.getApplicationLabel(info.applicationInfo));
            Drawable drawable = mPackageManager.getApplicationIcon(info.applicationInfo);
            //drawable 的大小设成固定值，按照下面的方式设置会导致各个应用的图标大小不一样(不那么美观了)
            drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());
            holder.title.setCompoundDrawables(null, drawable, null, null);
            return convertView;
        }

        private class ViewHolder {
            public ImageView icon;
            public TextView title;
        }
    }
}
