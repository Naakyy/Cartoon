package com.cyy.naak.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.cyy.naak.fragment.FragmentMain;
import com.cyy.naak.fragment.FragmentSearch;
import com.cyy.naak.fragment.FragmentSetting;
import com.cyy.naak.fragment.SuperAwesomeCardFragment;

public class MyPagerAdapter extends FragmentPagerAdapter {
//    private FragmentMain fragmentMain;
//    private FragmentSearch fragmentSearch;
	
	private final String[] TITLES = {"动漫","电视剧","电影"};
	
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

//        switch (position){
//            case 0:
//                return fragmentMain;
//            case 1:
//                return fragmentSearch;
//            default:
//                return new FragmentSetting();
//        }

		return SuperAwesomeCardFragment.newInstance(position);
	}

}
