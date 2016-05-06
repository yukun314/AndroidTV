package com.yt.androidtv.media;

import android.annotation.TargetApi;
import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Environment;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;

/**
 * Created by zyk on 2016/4/27.
 */
public class MediaSurface extends SurfaceView implements SurfaceHolder.Callback ,
        MediaPlayer.OnPreparedListener,MediaPlayer.OnCompletionListener{

    private MediaPlayer mPlayer = null;

    public MediaSurface(Context context) {
        super(context);
        init();
    }

    public MediaSurface(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MediaSurface(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public MediaSurface(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //FIXME catch 该为dialog提示 并做相应的处理
        try {
            mPlayer = new MediaPlayer();
            mPlayer.reset();
            File file = new File(Environment.getExternalStorageDirectory().getPath(),"abc.mkv");
            Toast.makeText(this.getContext(),"MediaSurface :"+Environment.getExternalStorageDirectory()+"\nfile is exists:"+file.exists(),Toast.LENGTH_LONG).show();
            if(file.exists()){
                mPlayer.setDataSource(file.getAbsolutePath());
            }else {
                mPlayer.setDataSource("http://zhibo.uotocom.com:9000/hls/3012/index.m3u8");
            }

            mPlayer.setDisplay(this.getHolder());
            mPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            //设置音量
            mPlayer.setVolume(80, 100);
            //设置播放预备侦听器
            mPlayer.setOnPreparedListener(this);
            //设置播放完成侦听器
            mPlayer.setOnCompletionListener(this);
            mPlayer.prepare();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        if(mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }

    //播放完毕
    @Override
    public void onCompletion(MediaPlayer mp) {
        if (mPlayer != null) {
            mPlayer.stop();
            mPlayer.release();
            mPlayer = null;
        }
    }

    //播放已准备就绪
    @Override
    public void onPrepared(MediaPlayer mp) {
        if (mPlayer != null) {
            mPlayer.start();
        }
    }

    private void init() {
        this.getHolder().addCallback(this);
        this.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

    }


}
