package com.fragmentviewpager.activity;

import java.util.ArrayList;
import java.util.List;

import com.fragmentviewpager.fragment.ContentFragment;

import android.os.Bundle;
import android.animation.Animator;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnCheckedChangeListener{
	/** ViewPager�����ؼ� */
	private ViewPager viewPager;
	/** ��ŵ�ѡ��ť��ˮƽ������ͼ */
	private HorizontalScrollView hsv;
	/** ��ѡ�� */
	private RadioGroup radioGroup;
	/** ����ͼƬ������ */
	private List<String> imageNameList;
	/** ���е�ѡ�� */
	private List<RadioButton> radioButtoonList;
	/** ��ǰ����ʾͼƬ������ */
	private TextView currentImageName;
	/** �������µ����� */
	private View line;
	/** �洢�α��λ��x,y */
	private int [] lineLocation;
	/** ��ǰ��ѡ���λ�� */
	private int [] radioButtonLocation;
	/** ��Ļ��� */
	private int winWidth,winHeight;
	/** ��ѡ��Ŀ�� */
	private int radioWidth;
	/** ���߿�ʼ�ƶ���λ�� */
	private int fromX;
	/** ������Ҫ�ƶ�����λ�� */
	private int toX;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		getWindowWidthAndHeight();
		initView();
		lineLocation = getLineLocation();
		initData();
	}

	/**
	 * Init Data
	 */
	private void initData() {
		imageNameList = new ArrayList<String>();
		radioButtoonList = new ArrayList<RadioButton>();
		RadioButton grilRadio;
		String name;
		for (int i = 0; i < radioGroup.getChildCount(); i++) {
			grilRadio = (RadioButton) radioGroup.getChildAt(i);
			name = grilRadio.getText().toString();
			radioButtoonList.add(grilRadio);
			imageNameList.add(name);
		}
		// ����Ĭ����ʾ
		currentImageName.setText(imageNameList.get(0));
		viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager()));
		viewPager.setOnPageChangeListener(new ViewPagerScrollListener());
		radioGroup.setOnCheckedChangeListener(this);
	}

	/**
	 * Init View
	 */
	private void initView() {
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		hsv = (HorizontalScrollView) findViewById(R.id.hsv);
		radioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		currentImageName = (TextView) findViewById(R.id.currentImageName);
		line = findViewById(R.id.line);
	}

	
	class ViewPagerAdapter extends FragmentPagerAdapter{

		public ViewPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			ContentFragment contentFragment = new ContentFragment();
			Bundle bundle = new Bundle();
			bundle.putInt("position", position);
			contentFragment.setArguments(bundle);
			return contentFragment;
		}

		@Override
		public int getCount() {
			return imageNameList.size();
		}
	}
	
	class ViewPagerScrollListener implements OnPageChangeListener{

		@Override
		public void onPageScrollStateChanged(int state) {
			
		}

		@Override
		public void onPageScrolled(int position, float positionOffset, int positionOffsetPx) {
			// �����Ӧѡ�����ʱ ˮƽ������ͼ��Ҫ������λ��
			// ��Ļ�ߵ��м�����֮��ľ���
			int num = (winWidth-radioWidth)/2;
			// ��Ҫ�������ܳ���
			int total = position*radioWidth;
			// ������Ҫ����ȥ�ľ���
			hsv.scrollTo(total - num, 0);
			
			radioWidth = radioButtoonList.get(0).getWidth();// ��ȡ��ѡ��Ŀ��
			radioButtonLocation = getCurrentRadioButtonLoaction(position);
			// ��������ƽ�ƶ���
			TranslateAnimation animation = new TranslateAnimation(
					fromX, 
					radioButtonLocation[0]+radioWidth * positionOffset, 
					0, 
					0);
			animation.setFillAfter(true);// ���ö�������ʱͣ�ڽ���ʱ��λ��
			animation.setDuration(10);
			// ��ʼ��X���� = ��ǰ��ѡ��ĵ�x����+��ѡ��Ŀ�� * ViewPager����ƫ����
			fromX = (int) (radioButtonLocation[0]+radioWidth * positionOffset);
			line.startAnimation(animation);
		}

		@Override
		public void onPageSelected(int position) {
			currentImageName.setText(imageNameList.get(position));
		}
		
	}
	
	/**
	 * Get Cursor Location
	 * @return Location[]
	 */
	private int [] getLineLocation(){
		int [] location = new int [2];
		line.getLocationInWindow(location);
		return location;
	}
	
	/**
	 * Get Current RadioButton Location
	 * @param position Current RadioButton index
	 * @return location[0] = x, location[1] = y
	 */
	public int [] getCurrentRadioButtonLoaction(int position){
		int [] location = new int[2];
		radioGroup.getChildAt(position).getLocationInWindow(location);
		return location;
	}
	
	/**
	 * Get Window Width and Height
	 */
	public void getWindowWidthAndHeight(){
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		winWidth = dm.widthPixels;
		winHeight = dm.heightPixels;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		int currentIndex = 0;
		switch (checkedId) {
		case R.id.gril1:
			currentIndex = 0;
			break;
		case R.id.gril2:
			currentIndex = 1;
			break;
		case R.id.gril3:
			currentIndex = 2;
			break;
		case R.id.gril4:
			currentIndex = 3;
			break;
		case R.id.gril5:
			currentIndex = 4;
			break;
		case R.id.gril6:
			currentIndex = 5;
			break;

		default:
			break;
		}
		viewPager.setCurrentItem(currentIndex,true);
	}
}
