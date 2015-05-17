package com.example.nodie;

import com.example.nodie.runnable.GameThread;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MainActivity extends Activity implements OnClickListener,Callback{
	private GameThread thread;
	private SurfaceView surfaceView;
	private ImageView normal,nightmare,hell,pur;
	private int gameType;    //��ʾ��Ϸ���Ѷ�ϵ��
	private int w;
	private int h;   //��Ϸ����Ŀ�͸�
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		initView();
	}

	/**
	 * ��ʼ����ͼ
	 */
	private void initView() {
		normal=(ImageView) findViewById(R.id.normal);
		nightmare=(ImageView) findViewById(R.id.nightmare);
		hell=(ImageView) findViewById(R.id.hell);
		pur=(ImageView) findViewById(R.id.pur);
		//��ӵ���¼��ļ���
		normal.setOnClickListener(this);
		nightmare.setOnClickListener(this);
		hell.setOnClickListener(this);
		pur.setOnClickListener(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.normal:
			gameType=2;
			break;
		case R.id.nightmare:
			gameType=3;
			break;
		case R.id.hell:
			gameType=4;
			break;
		case R.id.pur:
			gameType=5;
			break;
		default:
			break;
		}
		startGameView();
	}
	/**
	 * ������Ϸ����
	 */
	private void startGameView() {
		surfaceView = new SurfaceView(this);
		surfaceView.getHolder().addCallback(this);
		setContentView(surfaceView);
		
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		w = surfaceView.getWidth();//�����
		h = surfaceView.getHeight();//�����
		thread=new GameThread(0, surfaceView, this, gameType);
		thread.start();
		
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// TODO Auto-generated method stub
		thread.setFlag(false);
	}

}
