package com.tz.custom.view;

import com.tz.custom.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.View.MeasureSpec;

public class CustomView  extends View{
	//�����м������
	private Bitmap gray_bg;
	private float scale_h;
	private int bg_height;
	private  int[] numbers={10000,1000,500,200,0};
	private Paint paint;
	private Bitmap btn;
	//����һ�����Ĵ�С
	private int pic;
	//�۸������
	private Bitmap bg_number;
	private int text_u=200;
	private int text_d=500;
	/**
	 * ��ʼ���ؼ�
	 * @param context
	 * @param attrs
	 */
	public CustomView(Context context, AttributeSet attrs) {
		super(context, attrs);
		//���ȳ�ʼ�����ؼ�
		gray_bg=BitmapFactory.decodeResource(getResources(), R.drawable.axis_after);
		btn=BitmapFactory.decodeResource(getResources(), R.drawable.btn);
		bg_number=BitmapFactory.decodeResource(getResources(), R.drawable.bg_number);
		paint = new Paint();
		paint.setColor(Color.RED);
		
	}
	
	/**
	 * ����
	 */
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		//������ָ����

		int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
		int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
		
		//�Լ����õ�
		int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
		int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
		
		//���㵱����Ϊwrap_content��ʱ��ؼ��Ŀ�͸߸��Ƕ���
		bg_height = gray_bg.getHeight();
		int measuredHeight = (modeHeight == MeasureSpec.EXACTLY)?sizeHeight:bg_height;
		int measuredWidth = 2*measuredHeight/3;
		//����һ�����Ĵ�С
		pic= measuredHeight/20;
		
		//��������
		scale_h = (float)measuredHeight/bg_height;
		//���øÿؼ����
		setMeasuredDimension(measuredWidth, measuredHeight);
		Log.i("info", "gao:"+measuredHeight);
	}
	
	
	@Override
	protected void onDraw(Canvas canvas) {
		//������ε�״̬
		canvas.save();
		//���û���������
		canvas.scale(scale_h, scale_h);
		//�����м����ӵķŵ�X����
		int bg_x = (int) ((this.getWidth()/scale_h - gray_bg.getWidth())/2);
		canvas.drawBitmap(gray_bg, bg_x,0, null);
		//��������
		paint.setTextSize(20/scale_h);
		Log.d("info", "������Ĵ�С��"+pic);
		Log.d("info", "�ؼ����ܸ߶ȣ�"+bg_height);
		
		for (int i = 0;i<numbers.length;i++) {
			//100/5   0 20  40 60 80 
			float text_y=((bg_height-pic)/(numbers.length-1))*i+(pic/2)+paint.descent();
			Log.d("info", "��"+i+"�����ĸ߶�:"+text_y);
			canvas.drawText(numbers[i]+"", 5*bg_x/4, text_y, paint);
		}
		 
		canvas.drawText("���",0,this.getHeight(), paint);
		float y_u= getBtnLocationByPrice(text_u);
		canvas.drawBitmap(btn, bg_x,y_u-btn.getHeight()/2, paint);
		canvas.drawBitmap(bg_number,0,y_u-(pic/2),paint);
		
		float y_d= getBtnLocationByPrice(text_d);
		canvas.drawBitmap(btn, bg_x,y_d-btn.getHeight()/2, paint);
		canvas.drawBitmap(bg_number,0,y_d-(pic/2),paint);
		//����ͼƬ�����ֵ�λ��
		canvas.drawText(text_u+"" ,bg_number.getWidth()/3,y_u+(18/scale_h), paint);
		canvas.drawText(text_d+"" ,bg_number.getWidth()/3,y_d+(18/scale_h), paint);
		//���û���
		canvas.restore();
		super.onDraw(canvas);
	}
	
	/*
	 * ���ݼ۸�õ�����
	 */
	public float getBtnLocationByPrice(int price){
		float y = 0;
		if(price<=0){
			price = 1;
		}
		if(price>10000){
			price = 10000;
		}
		//��¼ÿ������Ĵ�С
		//�ӵ�һ������ʼ��
		float between= (bg_height-pic)/(numbers.length-1);
		for(int i=0 ; i<numbers.length-1;i++){
			if(price<=numbers[i]&&price>numbers[i+1]){
				y= (between*i)+between*(numbers[i]-price)/(numbers[i]-numbers[i+1])+(pic/2);
			}
		}
		Log.i("INFO", "y����:"+y+"      price:"+price);
		return y;
	}
	/**
	 * ��������õ��۸�
	 */
	/*
	 * ���ݼ۸�õ�����
	 */
	public float getPriceByLocation(float y){
		
	}

}
