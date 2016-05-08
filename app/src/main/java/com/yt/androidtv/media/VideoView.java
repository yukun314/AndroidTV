package com.yt.androidtv.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

/**
 * Created by zyk on 2016/5/7.
 * 对 Vidwo 及 MediaController 等 的再次封装
 */
public class VideoView extends RelativeLayout {
    private final Context mContext;
    public Video mVideo;

    public VideoView(Context context) {
        super(context);
        mContext = context;
        init();
    }

    public VideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        init();
    }

    public VideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public VideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext = context;
        init();
    }

    private void init(){
        mVideo = new Video(mContext);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        addView(mVideo, lp);
    }

}
