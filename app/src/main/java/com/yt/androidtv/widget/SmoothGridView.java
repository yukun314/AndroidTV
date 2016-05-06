package com.yt.androidtv.widget;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by zyk on 2016/3/17.
 * 在布局文件中设置android：focusable=“false”不知道为什么不起作用
 * item的各种事件要自己实现,即Adapter要自己实现 convertView 添加与item想对应的事件，
 * 在converView的事件中使用perform...触发对应的item事件。更直接些在convertView的事件中直接实现功能
 */
public class SmoothGridView extends GridView {
    public SmoothGridView(Context context) {
        super(context);
        init();
    }

    public SmoothGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SmoothGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public SmoothGridView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init(){
        setFocusable(false);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
