package com.yt.androidtv.activity;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
//import android.widget.MediaController;
import android.widget.ProgressBar;
//import android.widget.VideoView;

import com.yt.androidtv.R;
import com.yt.androidtv.media.MediaController;
import com.yt.androidtv.media.Video;
import com.yt.androidtv.media.VideoView;

public class TestMediaActivity extends Activity implements MediaPlayer.OnCompletionListener, MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener{

    /**
     * View播放
     */
    private VideoView mVideoView;

    /**
     * 加载预览进度条
     */
    private ProgressBar progressBar;

    /**
     * 设置view播放控制条
     */
    private MediaController mediaController;

    /**
     * 标记当视频暂停时播放位置
     */
    private int intPositionWhenPause=-1;

    /**
     * 设置窗口模式下的videoview的宽度
     */
    private int videoWidth;

    /**
     * 设置窗口模式下videoview的高度
     */
    private int videoHeight;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_videoview);
        initVideoView();

//        Uri uri=Uri.parse("http://123.150.52.227/0725695b00000000-1415769042-1960016430/data5/vkplx.video.qq.com/flv/74/164/a0015193bxf.p203.1.mp4");
//        Intent intent=new Intent(Intent.ACTION_VIEW);
//        intent.setDataAndType(uri,"video/*");
//        startActivity(intent);
    }
    /**
     *初始化videoview播放
     */
    public void initVideoView(){
        //初始化进度条
        progressBar= (ProgressBar) findViewById(R.id.progressBar);
        //初始化VideoView
        mVideoView = (VideoView) findViewById(R.id.videoView);
        //初始化videoview控制条
        mediaController=new MediaController(this);
        //设置videoview的控制条
        mVideoView.mVideo.setMediaController(mediaController);
        //设置显示控制条
        mediaController.show(0);
        //设置播放完成以后监听
        mVideoView.mVideo.setOnCompletionListener(this);
        //设置发生错误监听，如果不设置videoview会向用户提示发生错误
        mVideoView.mVideo.setOnErrorListener(this);
        //设置在视频文件在加载完毕以后的回调函数
        mVideoView.mVideo.setOnPreparedListener(this);
        //设置videoView的点击监听
//        videoView.setOnTouchListener(this);
        //设置网络视频路径
//        Uri uri=Uri.parse("http://zhibo.uotocom.com:9000/hls/3012/index.m3u8");
        Uri uri = Uri.parse("rtsp://184.72.239.149/vod/mp4://BigBuckBunny_175k.mov");
        mVideoView.mVideo.setVideoURI(uri);
        //设置为全屏模式播放
        setVideoViewLayoutParams(1);
    }
    @Override
    protected void onStart() {
        super.onStart();
        //启动视频播放
        mVideoView.mVideo.start();
        //设置获取焦点
        mVideoView.mVideo.setFocusable(true);

    }
    /**
     * 设置videiview的全屏和窗口模式
     * @param paramsType 标识 1为全屏模式 2为窗口模式
     */
    public void setVideoViewLayoutParams(int paramsType){

//        if(1==paramsType) {
//            RelativeLayout.LayoutParams LayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_TOP);
//            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_LEFT);
//            LayoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//            videoView.setLayoutParams(LayoutParams);
//        }else{
//            //动态获取宽高
//            DisplayMetrics DisplayMetrics=new DisplayMetrics();
//            this.getWindowManager().getDefaultDisplay().getMetrics(DisplayMetrics);
//            videoHeight=DisplayMetrics.heightPixels-50;
//            videoWidth=DisplayMetrics.widthPixels-50;
//            RelativeLayout.LayoutParams LayoutParams = new RelativeLayout.LayoutParams(videoWidth,videoHeight);
//            LayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
//            videoView.setLayoutParams(LayoutParams);
//        }

    }
    /**
     * 视频播放完成以后调用的回调函数
     */
    @Override
    public void onCompletion(MediaPlayer mp) {

    }
    /**
     * 视频播放发生错误时调用的回调函数
     */
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        switch (what){
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.e("text","发生未知错误");

                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Log.e("text","媒体服务器死机");
                break;
            default:
                Log.e("text","onError+"+what);
                break;
        }
        switch (extra){
            case MediaPlayer.MEDIA_ERROR_IO:
                //io读写错误
                Log.e("text","文件或网络相关的IO操作错误");
                break;
            case MediaPlayer.MEDIA_ERROR_MALFORMED:
                //文件格式不支持
                Log.e("text","比特流编码标准或文件不符合相关规范");
                break;
            case MediaPlayer.MEDIA_ERROR_TIMED_OUT:
                //一些操作需要太长时间来完成,通常超过3 - 5秒。
                Log.e("text","操作超时");
                break;
            case MediaPlayer.MEDIA_ERROR_UNSUPPORTED:
                //比特流编码标准或文件符合相关规范,但媒体框架不支持该功能
                Log.e("text","比特流编码标准或文件符合相关规范,但媒体框架不支持该功能");
                break;
            default:
                Log.e("text","onError+"+extra);
                break;
        }
        //如果未指定回调函数， 或回调函数返回假，VideoView 会通知用户发生了错误。
        return false;
    }

    /**
     * 视频文件加载文成后调用的回调函数
     * @param mp
     */
    @Override
    public void onPrepared(MediaPlayer mp) {
        //如果文件加载成功,隐藏加载进度条
        progressBar.setVisibility(View.GONE);

    }

    /**
     * 页面暂停效果处理
     */
    @Override
    protected  void onPause() {
        super.onPause();
        //如果当前页面暂定则保存当前播放位置，并暂停
        intPositionWhenPause= mVideoView.mVideo.getCurrentPosition();
        //停止回放视频文件
        mVideoView.mVideo.stopPlayback();
    }

    /**
     * 页面从暂停中恢复
     */
    @Override
    protected void onResume() {
        super.onResume();
        //跳转到暂停时保存的位置
        if(intPositionWhenPause>=0){
            mVideoView.mVideo.seekTo(intPositionWhenPause);
            //初始播放位置
            intPositionWhenPause=-1;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null!= mVideoView.mVideo){
            mVideoView.mVideo =null;
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
