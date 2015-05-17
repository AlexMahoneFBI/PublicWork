package com.yk.nodie;

import com.yk.nodie.runnable.MyThread;

import android.app.Activity;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
/**
 * 
 * @author yk
 *
 */
public class MainActivity extends Activity implements OnClickListener, Callback {
	/** ��Ϸ�ȼ� */
	private int gameLevel;
	/** �Ѷ�ѡ�� */
	private ImageView img_normal, img_nightmare, img_hell, img_pur;
	private MyThread myThread;
	private SurfaceView sv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//������Ϸ�˵���ͼ
		gameMenuView();

	}

	/**
	 * ��Ϸ�˵���ͼ
	 */
	private void gameMenuView() {
		setContentView(R.layout.activity_main);
		findViews();
		initViews();
	}

	private void findViews() {
		img_normal = (ImageView) findViewById(R.id.normal);
		img_nightmare = (ImageView) findViewById(R.id.nightmare);
		img_hell = (ImageView) findViewById(R.id.hell);
		img_pur = (ImageView) findViewById(R.id.pur);
	}

	private void initViews() {
		img_normal.setOnClickListener(this);
		img_nightmare.setOnClickListener(this);
		img_hell.setOnClickListener(this);
		img_pur.setOnClickListener(this);
	}

	/**
	 * ��ʼ��Ϸ��ͼ
	 */
	private void startGameView() {
		sv = new SurfaceView(this);
		//ע��ص�����
		sv.getHolder().addCallback(this);
		//��������ص�activity
		setContentView(sv);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.normal:
			gameLevel=2;
			break;

		case R.id.nightmare:
			gameLevel=3;
			break;

		case R.id.hell:
			gameLevel=4;
			break;

		case R.id.pur:
			gameLevel=5;
			break;
		default:
			break;
		}
		//������Ϸ�ȼ���ʼ��Ϸ
		startGameView();
	}

	/**
	 * �����Ѿ�������ɣ���ʼ����
	 */
	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		int w=sv.getWidth();
		int h=sv.getHeight();
		//����һ���߳������ƶ���
		myThread = new MyThread(this,holder,w,h,gameLevel);
		myThread.start();
	}
	/**����ı�*/
	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		
	}
	/**��������*/
	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		if(myThread!=null){
			myThread.stop();
		}
	}
}
