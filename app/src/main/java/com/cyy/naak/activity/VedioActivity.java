package com.cyy.naak.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.cyy.naak.beans.HistoricalBean;
import com.cyy.naak.db.HistoricalDAO;
import com.cyy.naak.fragmentdemo.R;

import java.util.ArrayList;
import java.util.List;

import io.vov.vitamio.LibsChecker;
import io.vov.vitamio.MediaPlayer;
import io.vov.vitamio.widget.MediaController;
import io.vov.vitamio.widget.VideoView;

/**
 * @Description: ${todo}<调节音量和亮度>
 */

public class VedioActivity extends Activity implements MediaPlayer.OnCompletionListener,MediaPlayer.OnInfoListener{

    private int mVolume = -1;//当前声音
    private int mMaxVolume;//最大音量
    private float mBrightness = -1f;//当前亮度
    private VideoView mVideoView;
    private View mVolumeBrightnessLayout;
    private ImageView mOperationBg,mOperationPercent;
    private AudioManager mAudioManager;
    private int mLayout = VideoView.VIDEO_LAYOUT_ZOOM;//当前缩放模式
    private GestureDetector mGestureDetector;
    private MediaController mMediaController;
    private int count = 1; //一集中总共分为几段（从1开始）

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_vedio);

        //获取要加载的url数据
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        //获取播放地址的数组
        final String[] stringArray = bundle.getStringArray("stringArray");
        final int max = stringArray.length;
        //点击每一个part都从当前的部分开始播放
        int position = bundle.getInt("position");

        mVideoView = (VideoView)findViewById(R.id.surface_view);
        mVolumeBrightnessLayout = findViewById(R.id.operation_volume_brightness);
        mOperationBg = (ImageView)findViewById(R.id.operation_bg);
        mOperationPercent = (ImageView)findViewById(R.id.operation_percent);
        mAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        mMaxVolume = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        mMediaController = new MediaController(this);
        mGestureDetector = new GestureDetector(this,new MyGestureListener());

        //利用Vitamio加载视频
        if (!LibsChecker.checkVitamioLibs(this))
            return;

        //绑定事件
        mVideoView.setOnCompletionListener(VedioActivity.this);
        mVideoView.setOnInfoListener(VedioActivity.this);
        MediaController controller = new MediaController(VedioActivity.this);
        mVideoView.setMediaController(controller);
        //设置播放地址
        mVideoView.setVideoPath(stringArray[position]);
        mVideoView.setVideoLayout(2, 0);//播放画面拉伸全屏
        mVideoView.start();
        mVideoView.requestFocus();
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener(){
            @Override
            public void onCompletion(MediaPlayer mp) {
                //分段数+1，初始值为0
                count++;
                if (count < max){ //判断是否为最后一个视频分段，如果是则进行下一段的播放操作
                    mVideoView.setVideoPath(stringArray[count]);
                    MediaController controller = new MediaController(VedioActivity.this);
                    mVideoView.setMediaController(controller);
                    mVideoView.setVideoLayout(2, 0);
                    mVideoView.start();
                    mVideoView.requestFocus();
                }
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mGestureDetector.onTouchEvent(event))
            return true;

        //处理手势结束
        switch (event.getAction() & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_UP:
                 endGesture();
                 break;
        }
        return super.onTouchEvent(event);
    }

    //手势结束
    private void endGesture(){
        mVolume = -1;
        mBrightness = -1f;
        //隐藏
        mDismissHandler.removeMessages(0);
        mDismissHandler.sendEmptyMessageDelayed(0, 500);
    }

    @Override
    public void onCompletion(MediaPlayer mp) { }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    private class MyGestureListener extends GestureDetector.SimpleOnGestureListener{

        //滑动
        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            float mOldX = e1.getX(), mOldY = e1.getY();
            int y = (int) e2.getRawY();
            Display disp = getWindowManager().getDefaultDisplay();
            int windowWidth = disp.getWidth();
            int windowHeight = disp.getHeight();

            if (mOldX > windowWidth * 4.0 / 5)// 右边滑动
                onVolumeSlide((mOldY - y) / windowHeight);
            else if (mOldX < windowWidth / 5.0)// 左边滑动
                onBrightnessSlide((mOldY - y) / windowHeight);

            return super.onScroll(e1, e2, distanceX, distanceY);
        }

        //双击
        @Override
        public boolean onDoubleTap(MotionEvent e) {
            if (mLayout == VideoView.VIDEO_LAYOUT_ZOOM){
                mLayout = VideoView.VIDEO_LAYOUT_ORIGIN;
            }else{
                mLayout++;
            }
            if (mVideoView !=null)
                mVideoView.setVideoLayout(mLayout,0);

            return true;
        }
    }

    //定时隐藏
    private Handler mDismissHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            mVolumeBrightnessLayout.setVisibility(View.GONE);
        }
    };

    //滑动改变声音大小
    private void onVolumeSlide(float percent) {
        if (mVolume == -1) {
            mVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
            if (mVolume < 0)
                mVolume = 0;

            // 显示
            mOperationBg.setImageResource(R.mipmap.video_volumn_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }

        int index = (int) (percent * mMaxVolume) + mVolume;
        if (index > mMaxVolume)
            index = mMaxVolume;
        else if (index < 0)
            index = 0;

        // 变更声音
        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, index, 0);

        // 变更进度条
        ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        lp.width = findViewById(R.id.operation_full).getLayoutParams().width * index / mMaxVolume;
        mOperationPercent.setLayoutParams(lp);
    }

    //滑动改变亮度
    private void onBrightnessSlide(float percent) {
        if (mBrightness < 0) {
            mBrightness = getWindow().getAttributes().screenBrightness;
            if (mBrightness <= 0.00f)
                mBrightness = 0.50f;
            if (mBrightness < 0.01f)
                mBrightness = 0.01f;

            // 显示
            mOperationBg.setImageResource(R.mipmap.video_brightness_bg);
            mVolumeBrightnessLayout.setVisibility(View.VISIBLE);
        }
        WindowManager.LayoutParams lpa = getWindow().getAttributes();
        lpa.screenBrightness = mBrightness + percent;
        if (lpa.screenBrightness > 1.0f)
            lpa.screenBrightness = 1.0f;
        else if (lpa.screenBrightness < 0.01f)
            lpa.screenBrightness = 0.01f;
        getWindow().setAttributes(lpa);

        ViewGroup.LayoutParams lp = mOperationPercent.getLayoutParams();
        lp.width = (int) (findViewById(R.id.operation_full).getLayoutParams().width * lpa.screenBrightness);
        mOperationPercent.setLayoutParams(lp);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        if (mVideoView != null)
            mVideoView.setVideoLayout(mLayout,0);
        super.onConfigurationChanged(newConfig);
    }
    //设置默认播放就是横屏
    @Override
    protected void onResume() {
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }
}
