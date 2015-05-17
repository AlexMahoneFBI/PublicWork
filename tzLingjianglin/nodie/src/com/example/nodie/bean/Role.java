package com.example.nodie.bean;

import android.graphics.Bitmap;
import android.graphics.Canvas;

public class Role {
	private Bitmap bitmap;//��ǰͼƬ
	private Bitmap[] bms;//���һ������������ͼƬ������
	float x,y;//ͼƬ�����Ƶ�λ��
	float speedX,speedY;//�ٶ�
	int width,height;//ͼƬ���ƵĿ��
	private long lastTime;
	private int index;
	public Role(Bitmap[] bms){
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
	
	/**
	 * �л�ͼƬ��������
	 * @param span
	 */
	private void animateRole(long span){
		//ÿ��һС��ʱ����л���ǰͼƬ
		if(System.currentTimeMillis() - lastTime>=span){
			//˵��ʱ�䵽�ˣ�����ͼ��
			index ++;
			if(index ==bms.length){
				index = 0;
			}
			bitmap = bms[index];
			lastTime = System.currentTimeMillis();
		}
	}
	/**
	 * ����������Ƶ�������
	 */
	public void drawRole(Canvas canvas){
		animateRole(200);
		//�����Լ�
		canvas.drawBitmap(bitmap, this.getX(),this.getY(),null);
	}
}
