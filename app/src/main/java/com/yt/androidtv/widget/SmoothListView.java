package com.yt.androidtv.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.ListView;

/**
 * Created by zyk on 2016/3/17.
 * bug：getViewTreeObserver().addOnGlobalFocusChangeListener监听不到事件，所以不能使用控件放大
 * item可以正常接收 onItemClickedListener 等事件。这两点正好和SmoothGridView相反
 */
public class SmoothListView extends ListView {
    public SmoothListView(Context context) {
        super(context);
        setFocusable(false);
    }

    public SmoothListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(false);
    }

    public SmoothListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setFocusable(false);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SmoothListView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setFocusable(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
