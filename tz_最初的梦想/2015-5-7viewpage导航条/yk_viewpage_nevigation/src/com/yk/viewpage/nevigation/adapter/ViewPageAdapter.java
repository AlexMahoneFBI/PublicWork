package com.yk.viewpage.nevigation.adapter;

import java.util.List;

import com.yk.viewpage.nevigation.fragment.MyFragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
/**
 *�Զ���viewpage������
 * @author yk
 *
 */
public class ViewPageAdapter extends FragmentPagerAdapter{
	/**��Ŀ����*/
	private int count;
	
	public ViewPageAdapter(FragmentManager fm,int count) {
		super(fm);
		this.count=count;
	}

	@Override
	public Fragment getItem(int position) {
		MyFragment myFragment=new MyFragment();
		Bundle bundle=new Bundle();
		bundle.putInt("position", position);
		myFragment.setArguments(bundle);
		return myFragment;
	}

	@Override
	public int getCount() {
		return count;
	}

}
