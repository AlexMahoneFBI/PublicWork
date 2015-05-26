package com.example.buaa.nobodydie_2dgame.Roles;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

/**
 * Created by Alex on 2015/5/25.
 */
public class Role {
    //Current Bitmap
    private Bitmap bitMap;
    //An array of Bitmaps to complete a action
    private Bitmap[] bitmaps;

    //Coordinate
    private long x, y;
    //Speed
    private long speedX, speedY;
    //Width and Height
    private int width, height;

    private long lastTime;
    private int index;

    public boolean isJump() {
        return isJump;
    }

    public void setIsJump(boolean isJump) {
        this.isJump = isJump;
    }

    private boolean isJump;

    public Role(Bitmap[] bitmaps) {
        this.bitmaps = bitmaps;
        this.bitMap = bitmaps[0];
        this.width = bitMap.getWidth();
        this.height = bitMap.getHeight();
    }

    public Bitmap getBitMap() {
        return bitMap;
    }

    public void setBitMap(Bitmap bitMap) {
        this.bitMap = bitMap;
    }

    public Bitmap[] getBitmaps() {
        return bitmaps;
    }

    public void setBitmaps(Bitmap[] bitmaps) {
        this.bitmaps = bitmaps;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public long getSpeedX() {
        return speedX;
    }

    public void setSpeedX(long speedX) {
        this.speedX = speedX;
    }

    public long getSpeedY() {
        return speedY;
    }

    public void setSpeedY(long speedY) {
        this.speedY = speedY;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public long getX() {
        return x;
    }

    public void setX(long x) {
        this.x = x;
    }

    public long getY() {
        return y;
    }

    public void setY(long y) {
        this.y = y;
    }

    /**
     * Animation of change bitmap.
     *
     * @param span time span
     */
    private void animateRloe(long span) {
        //Each Span to change bitmap.
        if ((System.currentTimeMillis() - lastTime) >= span) {

            index++;
            if (index == bitmaps.length) {
                index = 0;
            }
            bitMap = bitmaps[index];
            lastTime = System.currentTimeMillis();
        }
    }

    /**
     * Draw Role sele to the Canvas
     */
    public void drawSelf(Canvas canvas) {
        animateRloe(200);

        //Draw self
        canvas.drawBitmap(bitMap, this.getX(), this.getY(), null);
    }

    /**
     * Get Rect depends on Role
     */
    public Rect getRectByRole() {
        Rect rect = new Rect();
        rect.left = (int) this.getX();
        rect.right = (int) (this.getX() + this.getWidth());
        rect.top = (int) this.getY();
        rect.bottom = (int) (this.getY() + this.getHeight());
        return rect;
    }
}
