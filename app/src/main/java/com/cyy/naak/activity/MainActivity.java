package com.cyy.naak.activity;

import com.astuetz.PagerSlidingTabStrip;
import com.cyy.naak.adapter.MyPagerAdapter;
import com.cyy.naak.fragmentdemo.R;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

public class MainActivity extends FragmentActivity implements AdapterView.OnItemClickListener{
	private Fragment[] mFragments;
	private RadioGroup bottomRg;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private RadioButton rbOne, rbTwo, rbThree;
	private ViewPager mViewPager;
	private PagerSlidingTabStrip mtabs;
	private MyPagerAdapter myPagerAdapter;

    private GridView gv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mtabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mViewPager = (ViewPager)findViewById(R.id.viewPager);
        gv = (GridView)findViewById(R.id.gridview);

        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(myPagerAdapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		mViewPager.setPageMargin(pageMargin);
		mtabs.setViewPager(mViewPager);
		
		mFragments = new Fragment[3];
		fragmentManager = getSupportFragmentManager();
		mFragments[0] = fragmentManager.findFragmentById(R.id.fg_main);
		mFragments[1] = fragmentManager.findFragmentById(R.id.fg_search);
		mFragments[2] = fragmentManager.findFragmentById(R.id.fg_setting);
		fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
		fragmentTransaction.show(mFragments[0]).commit();

        setFragmentIndicator();

	}

	private void setFragmentIndicator(){
		
		bottomRg = (RadioGroup)findViewById(R.id.bottom_rg);
		rbOne = (RadioButton)findViewById(R.id.one_rb);
		rbTwo = (RadioButton)findViewById(R.id.two_rb);
		rbThree = (RadioButton)findViewById(R.id.three_rb);
		
		bottomRg.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				
				fragmentTransaction = fragmentManager.beginTransaction().hide(mFragments[0]).hide(mFragments[1]).hide(mFragments[2]);
				switch (checkedId) {
				case R.id.one_rb:
					fragmentTransaction.show(mFragments[0]).commit();
					break;
					
				case R.id.two_rb:
					fragmentTransaction.show(mFragments[1]).commit();
					break;
					
				case R.id.three_rb:
					fragmentTransaction.show(mFragments[2]).commit();
					break;

				default:
					break;
				}
				
			}
		});
		
	}

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }
}
