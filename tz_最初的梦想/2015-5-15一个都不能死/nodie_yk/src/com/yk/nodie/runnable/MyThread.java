package com.yk.nodie.runnable;

import com.yk.nodie.R;
import com.yk.nodie.role.Role;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceHolder;

/**
 * ���߳��л��ƶ���
 * 
 * @author yk
 * 
 */
public class MyThread extends Thread {
	/**
	 * ������
	 */
	private Context context;
	/**
	 * ����ܼ�
	 */
	private SurfaceHolder holder;
	/**
	 * ����
	 */
	private Paint paint;
	/**
	 * �����Ⱥ͸߶�
	 */
	private int w, h;
	/**
	 * ��Ϸ�ȼ�
	 */
	private int gameLevel;
	/**
	 * ͼƬ��Դ
	 */
	private Bitmap[] bms;
	/**
	 * ͼƬ·��
	 */
	private int[] paths;
	/**
	 * �Ƿ�ʼ����
	 */
	private boolean isStart;
	private Role role;

	public MyThread(Context context, SurfaceHolder holder, int w, int h,
			int gameLevel) {
		super();
		this.context = context;
		this.holder = holder;
		this.w = w;
		this.h = h;
		this.gameLevel = gameLevel;
		paint = new Paint();
		isStart = true;
		paths = new int[] { R.drawable.role1_00, R.drawable.role1_01,
				R.drawable.role1_02, R.drawable.role1_03, R.drawable.role1_04,
				R.drawable.role1_05, };
		bms = new Bitmap[paths.length];
		for (int i = 0; i < paths.length; i++) {
			bms[i] = BitmapFactory.decodeResource(context.getResources(),
					paths[i]);
		}

		role = new Role(bms);
		role.setX(100);
		role.setY(100);
	}

	@Override
	public void run() {
		super.run();
		while (isStart) {
			Canvas canvas = null;
			try {
				canvas = holder.lockCanvas();//��������
				canvas.drawColor(Color.WHITE);//���ư�ɫ����
				canvas.drawText("����", 50, 50, paint);
				role.drawSelf(canvas);//����С��
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (canvas != null) {
					holder.unlockCanvasAndPost(canvas);
				}
			}

		}
	}

	public void setIsStart(boolean isStart) {
		this.isStart = isStart;
	}

}
