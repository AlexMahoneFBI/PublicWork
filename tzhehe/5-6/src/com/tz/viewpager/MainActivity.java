package com.tz.viewpager;

import java.util.ArrayList;

import com.tz.adapter.MyFragmentPagerAdapter;
import com.tz.fragment.TestFragment;
import com.tz.viewpager.utils.Constant;

import android.R.color;
import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.drawable.GradientDrawable.Orientation;
import android.os.Bundle;
import android.provider.ContactsContract.CommonDataKinds.Organization;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends FragmentActivity  {
	 private ViewPager mPager;  
	    private ArrayList<Fragment> fragmentList;  
	    private ImageView cursor;  
	    private TextView tv_spring, tv_summer, tv_autumn, tv_winter;  
	    private int currIndex;//��ǰҳ�����  
	    private int bmpW;//����ͼƬ���  
	    private int offset;//ͼƬ�ƶ���ƫ����  
	    @Override
	    public boolean onTouchEvent(MotionEvent event) {
	    	// TODO Auto-generated method stub
	    	return super.onTouchEvent(event);
	    }

	    @Override  
	    protected void onCreate(Bundle savedInstanceState) {  
	        super.onCreate(savedInstanceState);  
	        setContentView(R.layout.main);  
	          
	        InitTextView();  
	        InitImage();  
	        InitViewPager();  
	    }  
	      
	      
	    /* 
	     * ��ʼ����ǩ�� 
	     */  
	    public void InitTextView(){  
	    	tv_spring = (TextView)findViewById(R.id.tv_spring);  
	    	tv_summer = (TextView)findViewById(R.id.tv_summer);  
	    	tv_autumn = (TextView)findViewById(R.id.tv_autumn);  
	    	tv_winter = (TextView)findViewById(R.id.tv_winter);  
	    	tv_spring.setOnClickListener(new txListener(0));  
	    	tv_summer.setOnClickListener(new txListener(1));  
	    	tv_autumn.setOnClickListener(new txListener(2));  
	    	tv_winter.setOnClickListener(new txListener(3));  
	    }  
	      
	   
	      
	    public class txListener implements View.OnClickListener{  
	        private int index=0;  
	          
	        public txListener(int i) {  
	            index =i;  
	        }

			public void onClick(View v) {
				// TODO Auto-generated method stub
			    mPager.setCurrentItem(index);  
			}  
	       
	    }  
	      
	      
	    /* 
	     * ��ʼ��ͼƬ��λ������ 
	     */  
	    public void InitImage(){  
	    	cursor = (ImageView)findViewById(R.id.cursor);  
	        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher).getWidth();  
	        DisplayMetrics dm = new DisplayMetrics();  
	        getWindowManager().getDefaultDisplay().getMetrics(dm);  
	        int screenW = dm.widthPixels;  
	        offset = (screenW/4 - bmpW)/2;  
	        //imgageview����ƽ�ƣ�ʹ�»���ƽ�Ƶ���ʼλ�ã�ƽ��һ��offset��  
	        Matrix matrix = new Matrix();  
	        matrix.postTranslate(offset, 0);  
	        cursor.setImageMatrix(matrix);  
	    }  
	      
	    /* 
	     * ��ʼ��ViewPager 
	     */  
	    public void InitViewPager(){  
	        mPager = (ViewPager)findViewById(R.id.viewpager);  
	        fragmentList = new ArrayList<Fragment>();  
	        //����
	        Fragment spring = TestFragment.newInstance(R.drawable.spring);
	        //����
	        Fragment summer = TestFragment.newInstance(R.drawable.summer);
	        //����
	        Fragment autumn = TestFragment.newInstance(R.drawable.autumn);
	        //����
	        Fragment winter = TestFragment.newInstance(R.drawable.winter);
	        fragmentList.add(spring);  
	        fragmentList.add(summer);  
	        fragmentList.add(autumn);  
	        fragmentList.add(winter);  
	          
	        //��ViewPager����������  
	        mPager.setAdapter(new MyFragmentPagerAdapter(getSupportFragmentManager(), fragmentList));  
	        mPager.setCurrentItem(0);//���õ�ǰ��ʾ��ǩҳΪ��һҳ  
	        mPager.setOnPageChangeListener(new MyOnPageChangeListener());//ҳ��仯ʱ�ļ�����  	   
	        }  
	  
	     
		    public class MyOnPageChangeListener implements OnPageChangeListener{  
		        private int one = offset *2 +bmpW;//��������ҳ���ƫ����  

				public void onPageScrollStateChanged(int arg0) {
					// TODO Auto-generated method stub
					
				}

				public void onPageScrolled(int arg0, float arg1, int arg2) {
					// TODO Auto-generated method stub
					
				}

				public void onPageSelected(int arg0) {
					Toast.makeText(MainActivity.this, "�ڼ���ҳ��"+arg0, Toast.LENGTH_SHORT).show();
		            Animation animation = new TranslateAnimation(currIndex*one,arg0*one,0,0);//ƽ�ƶ���  
		            currIndex = arg0;  
		            animation.setFillAfter(true);//������ֹʱͣ�������һ֡����Ȼ��ص�û��ִ��ǰ��״̬  
		            animation.setDuration(200);//��������ʱ��0.2��  
		            cursor.startAnimation(animation);//����ImageView����ʾ������  
		            int i = currIndex + 1;  
				}
		        
		    }



		      



		
	
}