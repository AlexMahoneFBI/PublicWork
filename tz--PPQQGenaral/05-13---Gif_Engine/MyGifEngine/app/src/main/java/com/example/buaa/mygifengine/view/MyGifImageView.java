package com.example.buaa.mygifengine.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.example.buaa.mygifengine.R;

/**
 * Created by Alex on 2015/5/21.
 * Note:SDK 4.0以上的，需要在AndroidManifest.xml文件中的<application>标签下加入
 * android:hardwareAccelerated="false"属性
 */
public class MyGifImageView extends ImageView {

    private Movie movie;
    private long mMovieStart;
    public MyGifImageView(Context context, AttributeSet attrs) {
        super(context, attrs);


        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MyGifImageView);
        int srcId = typedArray.getResourceId(R.styleable.MyGifImageView_android_src, 0);
        if(srcId != 0){

            movie = Movie.decodeStream(context.getResources().openRawResource(srcId));
        }
        Log.i("INFO","srcId is ---"+srcId);
        typedArray.recycle();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        long now = System.currentTimeMillis();
        if(mMovieStart == 0){
            mMovieStart = now;
        }
        int duration = movie.duration();
        synchronized (canvas){
            if(canvas != null){
                if(duration == 0){
                    duration = 1000;
                }

                movie.draw(canvas,0,0);
                movie.setTime((int) (now % duration));
                invalidate();
            }
        }
    }

//    //主要的工作是重载onDraw
//    @Override
//    protected void onDraw(Canvas canvas) {
//        // TODO Auto-generated method stub
//        //super.onDraw(canvas);
//
//        //当前时间
//        long now = android.os.SystemClock.uptimeMillis();
//        //如果第一帧，记录起始时间
//        if (mMovieStart == 0) {   // first time
//            mMovieStart = now;
//        }
//        if (movie != null) {
//            //取出动画的时长
//            int dur = movie.duration();
//            if (dur == 0) {
//                dur = 1000;
//            }
//            //算出需要显示第几帧
//            int relTime = (int)((now - mMovieStart) % dur);
//
//            //设置要显示的帧，绘制即可
//            movie.setTime(relTime);
//            movie.draw(canvas,0,0);
//            invalidate();
//        }
//    }
}
