package com.tz.dlidingmenu;



import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends Activity implements  OnClickListener {
    /** Called when the activity is first created. */
	private SlidingMenu mMenu;
	private LinearLayout qqLayout;
	private Button btn;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
		mMenu = (SlidingMenu) findViewById(R.id.id_menu);
		qqLayout=(LinearLayout) findViewById(R.id.qq);
		btn=(Button) findViewById(R.id.btn);
		btn.setOnClickListener(this);
		
	}
    
   
	@Override
	public void onClick(View arg0) {
		//button�ĵ���¼�
		mMenu.toggle();
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		//��갴�µ�ʱ���ж�menu�Ǵ򿪻��ǹرյ�
		//menu�Ǵ򿪵ģ�buttonû���¼�layout���¼�
		//menu�ǹرյģ�layout���¼��ø�button
		
		int action = event.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
			if(mMenu.isToggle()){
				//menu�ǹر�
				
			}else{
				//menu�Ǵ�
				
			}
			break;
		default:
			break;
		}
			
		// TODO Auto-generated method stub
		return super.onTouchEvent(event);

		
		
	}

	
	
	
}