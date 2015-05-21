package com.example.buaa.mygifengine;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.example.buaa.mygifengine.view.MyGifSurfaceView;


public class MainActivity extends Activity {

    private FrameLayout frameLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Could be the Third way.Parse Xml file to load Gif.
        setContentView(R.layout.activity_main);

//        frameLayout = (FrameLayout) findViewById(R.id.framelayout);
//        //Two ways to dynamic generate Gif
//        //First way
//        MyGifSurfaceView myGifSurfaceView = new MyGifSurfaceView(this,"beauty.gif");
//        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                Gravity.CENTER
//        );
//        myGifSurfaceView.setLayoutParams(layoutParams);
//        frameLayout.addView(myGifSurfaceView);

//        //Second way
//        MyGifSurfaceView myGifSurfaceView_2 = new MyGifSurfaceView(this,R.drawable.gun);
//        FrameLayout.LayoutParams layoutParams_2 = new FrameLayout.LayoutParams(
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT,
//                Gravity.CENTER
//        );
//        myGifSurfaceView_2.setLayoutParams(layoutParams_2);
//        frameLayout.addView(myGifSurfaceView_2);
    }

}
