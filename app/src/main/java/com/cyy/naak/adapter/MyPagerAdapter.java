package com.cyy.naak.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cyy.naak.fragment.SuperAwesomeCardFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {

	private final String[] TITLES = {"动漫","综艺","真人秀"};
	
	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return TITLES[position];
	}

	@Override
	public int getCount() {
		return TITLES.length;
	}

	@Override
	public Fragment getItem(int position) {

		return SuperAwesomeCardFragment.newInstance(position);
	}

}
