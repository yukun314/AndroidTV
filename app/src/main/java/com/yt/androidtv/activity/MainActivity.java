/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */

package com.yt.androidtv.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;

import com.yt.androidtv.R;
import com.yt.androidtv.app.BaseActivity;
import com.yt.androidtv.utils.UpdateManager;
import com.yt.androidtv.widget.ViewBorder;

/*
 * MainActivity class that loads MainFragment
 */
public class MainActivity extends BaseActivity {
    /**
     * Called when the activity is first created.
     */
    private ViewBorder mViewBorder;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        setContentView(R.layout.activity_main_test);
        View view = findViewById(R.id.main_lay);
        view.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                newFocus.bringToFront(); // 防止放大的view被压在下面.
                mViewBorder.setFocusView(newFocus, oldFocus, 1.2f);
                mViewBorder.setDrawUpRectEnabled(true);
                mViewBorder.bringToFront();
                if (newFocus != null) {
//					testTopDemo(newFocus);
                }
            }
        });
        mViewBorder = (ViewBorder) findViewById(R.id.mainUpView1);
        mViewBorder.setUpRectResource(R.drawable.test_rectangle);
        mViewBorder.setShadowResource(R.drawable.item_shadow);
        mViewBorder.setDrawUpRectEnabled(true);

        findViewById(R.id.activity_main_test_listview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MyAppActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        findViewById(R.id.activity_main_test_videoview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestMediaActivity.class);
                MainActivity.this.startActivity(intent);
            }
        });

        View view1 = findViewById(R.id.activity_main_test_gridview);
        view1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, TestGridView.class);
                MainActivity.this.startActivity(intent);
            }
        });

//        View view3 = findViewById(R.id.activity_main_test_center);
//        view3.requestFocusFromTouch();

//        this.openOrCreateDatabase("",MODE_PRIVATE,null);
        new UpdateManager(this).checkUpdate();
    }
}
