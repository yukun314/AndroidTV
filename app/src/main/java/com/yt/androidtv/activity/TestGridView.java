package com.yt.androidtv.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.HapticFeedbackConstants;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.yt.androidtv.R;
import com.yt.androidtv.app.BaseActivity;
import com.yt.androidtv.widget.SmoothGridView;
import com.yt.androidtv.widget.ViewBorder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by zyk on 2016/3/17.
 */
public class TestGridView extends BaseActivity {
    private ViewBorder mViewBorder;
    private List<Map<String,Object>> data;
    private LayoutInflater mInflater;
    private SmoothGridView gridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_gridview);
        mInflater = LayoutInflater.from(getApplicationContext());

        mViewBorder = (ViewBorder) findViewById(R.id.activity_test_gridview_viewborder);
        gridView = (SmoothGridView) findViewById(R.id.activity_test_gridview_grid);

        mViewBorder.setUpRectResource(R.drawable.white_light_10);
        mViewBorder.setShadowResource(R.drawable.item_shadow);

        mViewBorder.setDrawUpRectPadding(10);
        mViewBorder.setDrawUpRectEnabled(true);

        getData();

        String[] from = { "text" };
        int[] to = { R.id.textView };

//        SimpleAdapter simpleAdapter = new SimpleAdapter(this, data, R.layout.item, from, to);
//        gridView.setAdapter(simpleAdapter);
        MyAdapter adapter = new MyAdapter(gridView);
        gridView.setAdapter(adapter);
        //
        gridView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                mViewBorder.setFocusView(view, 1.2f);
                System.out.println("onItemSelected");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("onNothingSelected");
            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("onItemClick position:" + position);
            }
        });

//        View view = findViewById(R.id.linearLayout);
        gridView.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                mViewBorder.setFocusView(newFocus,oldFocus,1.2f);
//                if (newFocus != null) {
//                    mViewBorder.setFocusView(newFocus, 1.2f);
//                }
//                if (oldFocus != null) {
//                    mViewBorder.setUnFocusView(oldFocus);
//                }
            }
        });
    }

    public List<Map<String, Object>> getData() {
        data = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < 100; i++) {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("text", "item" + i);
            data.add(map);
        }
        return data;
    }

    public class MyAdapter extends BaseAdapter {
        private final SmoothGridView mSmoothGridView;

        MyAdapter(SmoothGridView sgv){
            mSmoothGridView = sgv;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public Object getItem(int position) {
            return data.get(position);
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
                convertView = mInflater.inflate(R.layout.item, parent, false);
                holder.title = (TextView) convertView.findViewById(R.id.textView);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        System.out.println("convertView 的 onClickedListener.position="+position);
                        mSmoothGridView.setSelection(position);
                        mSmoothGridView.performItemClick(v,position,getItemId(position));
                    }
                });

                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            Map<String,Object> map = data.get(position);
            holder.title.setText(map.get("text").toString());
            return convertView;
        }

        public class ViewHolder {
            public TextView title;
        }
    }
}
