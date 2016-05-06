package com.yt.androidtv.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.yt.androidtv.R;
import com.yt.androidtv.app.BaseActivity;
import com.yt.androidtv.widget.SmoothListView;
import com.yt.androidtv.widget.ViewBorder;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zyk on 2016/3/17.
 */
public class TestListView extends BaseActivity{

    private ViewBorder mViewBorder;
    private List<String> data;
    private LayoutInflater mInflater;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_listview);
        mViewBorder = (ViewBorder) findViewById(R.id.activity_test_listview_viewborder);
        SmoothListView mListView = (SmoothListView) findViewById(R.id.activity_test_listview_list);
        mInflater = LayoutInflater.from(getApplicationContext());
        initData();
        mListView.setAdapter(new MyAdapter(mListView));
        mListView.getViewTreeObserver().addOnGlobalFocusChangeListener(new ViewTreeObserver.OnGlobalFocusChangeListener() {
            @Override
            public void onGlobalFocusChanged(View oldFocus, View newFocus) {
                if (newFocus != null) {
                    mViewBorder.setFocusView(newFocus, 1.2f);
                }
                if (oldFocus != null) {
                    mViewBorder.setUnFocusView(oldFocus);
                }
                System.out.println("oldFocus is null:" + (oldFocus == null) + "   newFocus is null:" + (newFocus == null));
//                mViewBorder.setFocusView(newFocus, oldFocus, 1.2f);
            }
        });

        mListView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (view != null)
                    mViewBorder.setFocusView(view, 1.2f);
                System.out.println("onItemSelected:"+position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("onNothingSelected");
            }
        });

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("onitemClickListenter:"+position);
            }
        });

    }

    public void initData() {
        data = new ArrayList<String>();
        for (int i = 0; i < 105; i++) {
            String text = "item" + i;
            data.add(text);
        }
    }

    public class MyAdapter extends BaseAdapter {

        private final ListView mListView;

        MyAdapter(ListView lv){
            mListView = lv;
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
                convertView = mInflater.inflate(R.layout.item, null);
                holder.title = (TextView) convertView.findViewById(R.id.textView);
                convertView.setTag(holder);
                convertView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mListView.setSelection(position);
                        mListView.performItemClick(v,position,getItemId(position));
                    }
                });
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.title.setText(data.get(position));

            return convertView;
        }

        public class ViewHolder {
            public TextView title;
        }
    }
}
