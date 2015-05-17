package com.example.nodie.runnable;

import com.example.nodie.R;
import com.example.nodie.bean.Role;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * �����߳�һֱ������ϷԪ�صĻ���
 * @author Administrator
 *
 */
public class GameThread extends Thread {
	private Bitmap[] roles;   //��ž���Ĳ�ͬ֡��ͼƬ
	private int index;   //��ǰ���ŵ��ڼ�֡
	private boolean flag;   //����ѭ���Ŀ�ʼ�ͽ���
	private SurfaceView view;   
	private Context context;   //�����Ķ���
	private int gameType;    //��Ϸ������
	private Role role;
	
	
	
	private static int bitmaps[]={R.drawable.role1_00,
		R.drawable.role1_01,
		R.drawable.role1_02,
		R.drawable.role1_03,
		R.drawable.role1_04,
		R.drawable.role1_05};
	public GameThread(int index, SurfaceView view, Context context, int gameType) {
		super();
		this.index = index;
		this.view = view;
		this.context = context;
		this.gameType = gameType;
		flag=true;
		roles=new Bitmap[bitmaps.length];
		for(int i=0;i<bitmaps.length;i++){
			roles[i]=BitmapFactory.decodeResource(context.getResources(), bitmaps[i]);
		}
		
		role = new Role(roles);
		role.setX(200);
		role.setY(200);
	}


	@Override
	public void run() {
		super.run();
		SurfaceHolder holder=view.getHolder();
		Canvas canvas=null;

		
		while(flag){
			canvas=holder.lockCanvas();
			canvas.drawColor(Color.WHITE);//���ư�ɫ�ı���
			role.drawRole(canvas);

			holder.unlockCanvasAndPost(canvas);
		}
		
		
	}


	public boolean isFlag() {
		return flag;
	}


	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
