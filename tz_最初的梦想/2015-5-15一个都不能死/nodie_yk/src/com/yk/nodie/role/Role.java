package com.yk.nodie.role;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * С��
 * @author yk
 *
 */
public class Role {
	/**
	 * ��Ⱥ͸߶�
	 */
	private int width,heith;
	/**
	 * ����
	 */
	private float x,y;
	/**
	 * �ٶ�
	 */
	private int speedX,speedY;
	/**
	 * ��ǰ��ʾ��ͼƬ
	 */
	private Bitmap bitmap;
	/**
	 * ��ǰͼƬλ��
	 */
	private int index;
	/**
	 * ͼƬ����
	 */
	private Bitmap[] bms;
	/**
	 * ��һ��ʱ���
	 */
	private long lastTime;
	
	public Role(Bitmap[] bms) {
		this.bms = bms;
		//��ʾ��һ��ͼƬ
		this.bitmap=bms[0];
		this.width=bitmap.getWidth();
		this.heith=bitmap.getHeight();
	}
	/**
	 * ����ָ��ʱ�����л�С��ͼƬ
	 * @param span ʱ����
	 */
	private void switchRole(int span){
		if(System.currentTimeMillis()-lastTime>span){
			index++;
			if(index==bms.length){
				index=0;
			}
			bitmap=bms[index];
			lastTime=System.currentTimeMillis();
		}
	}
	
	public void drawSelf(Canvas canvas){
		switchRole(50);
		//�����Լ�
		canvas.drawBitmap(bitmap, this.getX(), this.getY(),null);
	}
	
	
	

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeith() {
		return heith;
	}

	public void setHeith(int heith) {
		this.heith = heith;
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

	public int getSpeedX() {
		return speedX;
	}

	public void setSpeedX(int speedX) {
		this.speedX = speedX;
	}

	public int getSpeedY() {
		return speedY;
	}

	public void setSpeedY(int speedY) {
		this.speedY = speedY;
	}

	public Bitmap getBitmap() {
		return bitmap;
	}

	public void setBitmap(Bitmap bitmap) {
		this.bitmap = bitmap;
	}

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public Bitmap[] getBms() {
		return bms;
	}

	public void setBms(Bitmap[] bms) {
		this.bms = bms;
	}
}

