package com.example.game;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Role {
	private Bitmap bitmap;//��ǰͼƬ
	private Bitmap[] bms;//���һ��������ͼƬ����
	private float x,y;//����
	private float speedX,speedY;//�ٶ�
	private  int width,height;//���
	private long lastTime;
	private int index;
	public Role(Bitmap[] bms) {
		super();
		this.bms = bms;
		this.bitmap = bms[0];
		this.width = bitmap.getWidth();
		this.height = bitmap.getHeight();
	}
	public Bitmap getBitmap() {
		return bitmap;
	}
	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}
	public Bitmap[] getBms() {
		return bms;
	}
	public void setBms(Bitmap[] bms) {
		this.bms = bms;
	}
	public float getX() {
		return x;
	}
	public void setX(float x) {
		this.x = x;
	}
	public float getY() {
		return y;
	}
	public void setY(float y) {
		this.y = y;
	}
	public float getSpeedX() {
		return speedX;
	}
	public void setSpeedX(float speedX) {
		this.speedX = speedX;
	}
	public float getSpeedY() {
		return speedY;
	}
	public void setSpeedY(float speedY) {
		this.speedY = speedY;
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	public long getLastTime() {
		return lastTime;
	}
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	/**
	 * �л����� 
	 */
	private void animateRole(long span){
		if (System.currentTimeMillis()-lastTime>=span) {
			index++;
			if (index==bms.length) {
				index=0;
			}
			bitmap=bms[index];
			lastTime=System.currentTimeMillis();
		}
	}
	/**
	 * ������滭��������
	 * 
	 */
	public void drawSelf(Canvas canvas){
		animateRole(200);
		canvas.drawBitmap(bitmap, this.getX(), this.getY(),null);
	}
	
}
