package com.example.buaa.nobodydie_2dgame;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;

import com.example.buaa.nobodydie_2dgame.Runnable.MyThread;


public class MainActivity extends Activity implements View.OnClickListener, SurfaceHolder.Callback, View.OnTouchListener {

    private ImageView normal;
    private ImageView nightmare;
    private ImageView hell;
    private ImageView purgatory;
    private int gameType;
    private SurfaceView mSurfaceView;
    private MyThread myThread;
    //surfaceView's width and height
    private int h,w;
    private int gameSpan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initMeunView();
    }

    /**
     * Init Meun View
     */
    private void initMeunView() {
        setContentView(R.layout.activity_main);
//        setContentView(R.layout.custom_view);

        normal = (ImageView) findViewById(R.id.normal);
        nightmare = (ImageView) findViewById(R.id.nightmare);
        hell = (ImageView) findViewById(R.id.hell);
        purgatory = (ImageView) findViewById(R.id.purgatory);

        normal.setOnClickListener(this);
        nightmare.setOnClickListener(this);
        hell.setOnClickListener(this);
        purgatory.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.normal:
                gameType = 2;
                break;
            case R.id.nightmare:
                gameType = 3;
                break;
            case R.id.hell:
                gameType = 4;
                break;
            case R.id.purgatory:
                gameType = 5;
                break;
            default:
                break;
        }
        //根据游戏难度，开启游戏视图，开始玩游戏
        startGameView();
    }

    /**
     * Start Game View
     */
    private void startGameView() {
        mSurfaceView = new SurfaceView(this);
        mSurfaceView.getHolder().addCallback(this);

        //给画板添加onTouch监听事件
        mSurfaceView.setOnTouchListener(this);

//        gameSpan = h*4/(5*gameType);//设置在这里，span是0，原因是他还没有及时的crea出来
        setContentView(mSurfaceView);

        Log.i("INFO", "Game View IS Loaded...");
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //画板已经创建好
        w = mSurfaceView.getWidth();//画板宽
        h = mSurfaceView.getHeight();//画板高

        gameSpan = h*4/(5*gameType);
        //画板已经准备好，可以开始画画
        myThread = new MyThread(this,holder,w,h,gameType);
        myThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
        //画板改变
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //画板销毁
        myThread.setIsStart(false);
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        //ontouch事件，根据游戏模式来选择相应的区域跳起
        switch(myThread.getGameStatus()){
            case MyThread.GAMERUNNING:
                //游戏正在运行，找到对应的role，并让他跳起来
                confirmJump(event);
                break;
            case MyThread.GAMESTANDOFF:
                break;
            case MyThread.GAMEOVER:
                //游戏结束，返回或者重来
                backOrRestart(event);
                break;
            default:
                break;
        }
        return false;
    }

    /**
     * 根据手指按下的y坐标，来判断是否返回，或者是重来
     */
    private void backOrRestart(MotionEvent event) {
        float y = event.getY();
        if(y >= h/2 && y< h*3/4){
            //返回
            initMeunView();
        }else if(y >= h*3/4){
            //重来
            restartGameView();
        }
    }

    /**
     * 重来的gameview
     */
    private void restartGameView() {
        //设置游戏的状态,回到正常状态
        myThread.setGameStatus(MyThread.GAMERUNNING);
        //初始化精灵
        myThread.initSprite();
    }

    /**
     * 确定被点击的区域
     * @param event
     */
    private void confirmJump(MotionEvent event) {
        int action = event.getAction();
        switch(action & MotionEvent.ACTION_MASK){
            case MotionEvent.ACTION_DOWN:
                //第一根手指按下来
                float y1 = event.getY();
                jump(y1);
                break;
            case MotionEvent.ACTION_POINTER_DOWN:
                //其他手指陆续按下来
                float y2 = event.getY(event.getPointerCount() - 1);
                jump(y2);
                break;
            default:
                break;
        }
    }

    /**
     * 根据手指y坐标获取对应的人物，然后让此role跳起来
     * @param y
     */
    private void jump(float y) {
        for(int i =0; i< gameType; i++){
            //遍历所有的游戏区域，判断y坐标落在哪个游戏区域
            int line_down = h/10 + (i+1)*gameSpan;
            int line_up = h/10 +i*gameSpan;
            if(y >= line_up && y < line_down && !myThread.roles[i].isJump()){
                //这个区域被点到了，第i个区域
                //设置向上的速度
                myThread.roles[i].setSpeedY(-h/55);
                myThread.roles[i].setIsJump(true);
            }
        }
    }
}
