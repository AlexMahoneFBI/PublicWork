package com.example.buaa.mygifengine.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.os.Handler;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.example.buaa.mygifengine.R;

/**
 * Created by Alex on 2015/5/20.
 */
public class MyGifSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

    private Movie movie;
    private float scale_h;
    private float scale_w;
    private SurfaceHolder holder;
    private static final int FRAME_DURATION = 20;

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            //Execute draw
            draw();
            handler.postDelayed(runnable, FRAME_DURATION);
        }
        /**
         * Draw Gif
         */
        private void draw() {
            //Lock canvas
            Canvas canvas = holder.lockCanvas();
            synchronized (canvas){
                if(canvas != null){
                    canvas.save();
                    canvas.scale(scale_w, scale_h);

                    //Draw
                    movie.draw(canvas, 0, 0);

                    canvas.restore();
                    //Unlock
                    holder.unlockCanvasAndPost(canvas);
                }
            }
            //Reset Duration
            movie.setTime((int) (System.currentTimeMillis() % movie.duration()));
        }
    };

    /**
     * Draw gif depends on gif file of assets' directory
     * @param context Context
     * @param path file in the assets' directory
     */
    public MyGifSurfaceView(Context context, String path) {
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);
        try {
            movie = Movie.decodeStream(context.getAssets().open(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * Draw gif depends on gif file int  drawable directory
     * @param context Context
     * @param scrId file's ID in the drawable directory
     */
    public MyGifSurfaceView(Context context,int scrId){
        super(context);
        holder = this.getHolder();
        holder.addCallback(this);

        movie = Movie.decodeStream(context.getResources().openRawResource(scrId));
    }
    /**
     * Draw gif depends on xml file
     * @param context Context
     * @param attributeSet set of attributes
     */
    public MyGifSurfaceView(Context context,AttributeSet attributeSet){
        super(context,attributeSet);
        holder = this.getHolder();
        holder.addCallback(this);

        TypedArray typedArray = context.obtainStyledAttributes(attributeSet,R.styleable.MyGifImageView);
        int gifId = typedArray.getResourceId(R.styleable.MyGifImageView_gif_id, 0);

        Log.i("INFO","gifID==="+gifId);
        movie = Movie.decodeStream(context.getResources().openRawResource(gifId));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        int modeHeight = MeasureSpec.getMode(heightMeasureSpec);

        int gifWidth = movie.width();
        int gifHeight = movie.height();

        int measuredWidth = (modeWidth == MeasureSpec.EXACTLY)?sizeWidth:gifWidth;
        int measuredHeight = (modeHeight == MeasureSpec.EXACTLY)?sizeHeight:gifHeight;

//        scale_w = (float)this.getWidth() / gifWidth;
        scale_w = (float)measuredWidth / gifWidth;
        scale_h = (float)measuredHeight / gifHeight;

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Draw Gif Animation.
        handler.post(runnable);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //Destroy callback
        handler.removeCallbacks(runnable);
    }
}