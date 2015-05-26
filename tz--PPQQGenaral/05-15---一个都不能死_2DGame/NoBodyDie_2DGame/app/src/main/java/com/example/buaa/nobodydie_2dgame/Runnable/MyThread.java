package com.example.buaa.nobodydie_2dgame.Runnable;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;
import android.view.SurfaceHolder;

import com.example.buaa.nobodydie_2dgame.R;
import com.example.buaa.nobodydie_2dgame.Roles.Role;

import java.util.Random;

/**
 * Created by Alex on 2015/5/25.
 */
public class MyThread extends Thread {
    private Context context;
    private SurfaceHolder holder;
    private int w, h;
    private int gameType;
    private Paint paint;

    private boolean isStart;

    private Bitmap[] bitmaps;
    private int[] path;
    /**
     * Game Start Time
     */
    private long gameStartTime;
    /**
     * Last time of Game over
     */
    private long lastGameOverTime;

    public boolean isStart() {
        return isStart;
    }

    public void setIsStart(boolean isStart) {
        this.isStart = isStart;
    }

    public Role[] roles;
    private Rect[] rects;

    private int gameStatus;

    public static final int GAMERUNNING = 0;
    public static final int GAMESTANDOFF = 1;
    public static final int GAMEOVER = 2;
    //Span of each game zone
    private int gameSpan;

    public int getGameStatus() {
        return gameStatus;
    }
    public void setGameStatus(int gameStatus) {
        this.gameStatus = gameStatus;
    }
    public MyThread(Context context, SurfaceHolder holder, int w, int h, int gameType){
        this.context = context;
        this.holder  = holder;
        this.w = w;
        this.h = h;
        this.gameType = gameType;

        paint = new Paint();
        isStart =true;

        //初始化数组
        path = new int[]{
                R.drawable.role1_00,
                R.drawable.role1_01,
                R.drawable.role1_02,
                R.drawable.role1_03,
                R.drawable.role1_04,
                R.drawable.role1_05
        };
        bitmaps = new Bitmap[path.length];
        for(int i =0; i< bitmaps.length;i++){
            Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),path[i]);
            bitmaps[i] = bitmap;
        }
        //计算游戏区间高度
//        gameSpan = h*(1f - 1/10 - 1/10)/gameType;
        gameSpan = h*4/(5*gameType);
//        role = new Role(bitmaps);
//        role.setX(200);
//        role.setY(400);
        roles = new Role[gameType];
        rects = new Rect[gameType];

        initSprite();
    }

    /**
     * Create all sprites and initiate attributes to them
     */
    public void initSprite() {
        gameStartTime = System.currentTimeMillis();

        //创建role人物
        for(int i =0;i<gameType;i++){
            //第i根线的y坐标
            int lineY = h/10 + (i+1)*gameSpan;
            //创建一个role
            Role role = new Role(bitmaps);
            role.setX(w/8);//目测屏幕宽度的1/8
            role.setY(lineY - role.getHeight());
            roles[i] = role;

            //创建障碍物
            Rect rect = new Rect();
            //随机的宽度，1/4 ~ 6/4人物的宽高
            int randomWidth = (int) (role.getWidth()*(Math.random()*5+1)/4);
            int randomHeight = (int) (role.getHeight()*(Math.random()*5+1)/4);
            rect.left = w*3/2;
            rect.right = rect.left + randomWidth;
            rect.bottom = lineY;
            rect.top = rect.bottom - randomHeight;
            //添加数组
            rects[i] = rect;
        }
    }

    @Override
    public void run() {
        super.run();
        //执行画画任务
        while(isStart){
            //开始获取画布 来绘制
            Canvas canvas = null;

            try{
                //将获取的的画布绑定给画板
                canvas = holder.lockCanvas();
                if(canvas != null){
                    //最后确认画板OK了。可以画画了
                    //说明游戏根据阶段不同，所要绘制的东西不同
//                    canvas.drawColor(Color.WHITE);
//                    canvas.drawText("Too hot",200,200,paint);
//                    //Draw Role
//                    role.drawSelf(canvas);

                    switch(gameStatus){
                        case GAMERUNNING:
                            //draw gameRunning View
                            drawRunningView(canvas);
                            break;
                        case GAMESTANDOFF:
                            //draw gameStandoff View
                            drawStandoffView(canvas);
                            break;
                        case GAMEOVER:
                            //draw gameOver View
                            drawGameOverView(canvas);
                            break;
                        default:
                            break;
                    }
                }
            }catch (Exception e){
                    e.printStackTrace();
            }finally {
                if(canvas != null){
                    holder.unlockCanvasAndPost(canvas);
                }
            }
        }
    }

    /**
     * Draw GameOverView by canvasdepends on GameStatus
     * @param canvas canvas
     */
    private void drawGameOverView(Canvas canvas) {
        //Game over
        canvas.drawColor(Color.RED);
        //绘制文字
        String[] gameModes = new String[]{
                "普通",
                "噩梦",
                "地狱",
                "炼狱"
        };
        String gameMode = gameModes[gameType-2];
        String scopeType = gameMode+ ":" +(lastGameOverTime - gameStartTime)/1000f +"\'";
        String back = "返回";
        String restart = "重来";
        //绘制三个文本
        drawText(canvas,scopeType,h/2,28);
        drawText(canvas,back,h*4/6,24);
        drawText(canvas,restart,h*5/6,24);

    }

    /**
     * 绘制文本
     * @param canvas canvas
     * @param text text
     * @param y y coordinate
     * @param textSize textSize
     */
    private void drawText(Canvas canvas, String text, float y,int textSize) {
        paint.setTextSize(textSize);
        //文本的宽度
        float textWidth = paint.measureText(text);
        canvas.drawText(text,(w - textWidth)/2,y,paint);
    }

    /**
     * Draw GameStandOffView by canvas depends on GameStatus
     * @param canvas canvas
     */
    private void drawStandoffView(Canvas canvas) {
//        canvas.drawColor(Color.WHITE);//解决背景的抽风问题
        if((System.currentTimeMillis() - lastGameOverTime) >= 1000){
            //如果在game standoff阶段停留的时间大于 1000毫秒，进入GameOver阶段
            gameStatus = GAMEOVER;
        }else{
            //绘制线
            for(int i=0; i<gameType; i++){
                //底i根线的y坐标
                int lineY = h/10 + (i+1)*gameSpan;
                //画线
                canvas.drawLine(0, lineY, w, lineY, paint);
                //画人
                roles[i].drawSelf(canvas);
                //每画一个人，就画一个障碍物
                canvas.drawRect(rects[i], paint);
            }
        }
    }

    /**
     * Draw GameRunningView by canvas depends on GameStatus
     * @param canvas canvas
     */
    private void drawRunningView(Canvas canvas) {
        //画背景
        canvas.drawColor(Color.WHITE);

        //绘制右上角的得分文本
        String score = (System.currentTimeMillis() - gameStartTime)/1000f + "\'";
        paint.setTextSize(20);
        float score_w = paint.measureText(score);
        canvas.drawText(score,w-score_w,-paint.ascent(),paint);

        //绘制左下角的作者文本
        String logo = "作者：Alex";
        canvas.drawText(logo,0,h*9/10 + (h/10- (paint.descent()-paint.ascent()))/2-paint.ascent(),paint);

        //绘制线
        for(int i=0; i<gameType; i++){
            //底i根线的y坐标
            int lineY = h/10 + (i+1)*gameSpan;

            //每画一根线，就画一个人
            //设置人的向下加速度
            roles[i].setSpeedY(roles[i].getSpeedY() +  (long)(h/750f));
            roles[i].setY(roles[i].getY() + roles[i].getSpeedY());
            //判断人是否掉下相应的底线了
            if(roles[i].getY() + roles[i].getHeight() >= lineY){
                roles[i].setY(lineY - roles[i].getHeight());
                roles[i].setSpeedY(0);
                //掉到地上，设为false
                roles[i].setIsJump(false);
            }

            //首先移动障碍物
            rects[i].left = rects[i].left - h/150;
            rects[i].right = rects[i].right - h/150;
            if(rects[i].right <= 0){
                //当障碍物向左移除屏幕时，把它重新回到右侧
                int randomWidth = (int) (roles[i].getWidth()*(Math.random()*5+1)/4);
                int randomHeight = (int) (roles[i].getHeight()*(Math.random()*5+1)/4);
                int startLeft = (int) (w*(new Random().nextFloat())/2);
                rects[i].left = w*3/2 + startLeft;
                rects[i].right = rects[i].left + +randomWidth;
                rects[i].top = rects[i].bottom - randomHeight;
            }

            if(rects[i].intersect(roles[i].getRectByRole())){
                //障碍物和人相撞了
                //改变游戏状态.进入僵持状态
                gameStatus = GAMESTANDOFF;

                lastGameOverTime = System.currentTimeMillis();
            }


            //画线
            canvas.drawLine(0, lineY, w, lineY,paint);
            //画人
            roles[i].drawSelf(canvas);
            //每画一个人，就画一个障碍物
            canvas.drawRect(rects[i],paint);
            Log.i("INFO","第"+i+1+"个人和障碍物已经绘制出来");
        }
    }
}
