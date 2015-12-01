package com.cyy.naak.activity;

import com.astuetz.PagerSlidingTabStrip;
import com.cyy.naak.adapter.MyPagerAdapter;
import com.cyy.naak.fragmentdemo.R;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MainActivity extends FragmentActivity{
	private Fragment[] mFragments;
	private RadioGroup bottomRg;
	private FragmentManager fragmentManager;
	private FragmentTransaction fragmentTransaction;
	private RadioButton rbOne, rbTwo, rbThree;
	private ViewPager mViewPager;
	private PagerSlidingTabStrip mTabs;
	private MyPagerAdapter myPagerAdapter;
    private GridView gv;
	private Dialog dialog;
	private TextView dialogTitle,dialogContent;
	private Button dialogCancel,dialogComplete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		mTabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		mViewPager = (ViewPager)findViewById(R.id.viewPager);
        gv = (GridView)findViewById(R.id.gridview);

        myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(myPagerAdapter);
		final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources().getDisplayMetrics());
		mViewPager.setPageMargin(pageMargin);

		mTabs.setViewPager(mViewPager);
		//设置PagerSlidingTabStrip上的文字颜色
		mTabs.setTextColor(getResources().getColor(R.color.white));
		//设置PagerSlidingTabStrip上文字大小
		mTabs.setTextSize(48);
		//设置PagerSlidingTabStrip下方滑动条的颜色
		mTabs.setIndicatorColor(getResources().getColor(R.color.white));
		//设置PagerSlidingTabStrip下方滑动条的高度
		mTabs.setIndicatorHeight(5);
		
		mFragments = new Fragment[3];
		fragmentManager = getSupportFragmentManager();
		mFragments[0] = fragmentManager.findFragmentById(R.id.fg_main);
		mFragments[1] = fragmentManager.findFragmentById(R.id.fg_collect);
		mFragments[2] = fragmentManager.findFragmentById(R.id.fg_historical);
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
			//fragment之间的切换
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
		}});
	}

    private void exit(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示").setMessage("确实要退出这个应用吗?").setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.create().show();
    }

    //系统退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0){
//            exit();
			getDialog();
        }
        return true;//让消息到此为止
    }

	public void getDialog(){
		dialog = new Dialog(this);
		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		dialog.setContentView(R.layout.dialog_layout);
		Window dialogWindow = dialog.getWindow();
		dialogWindow.setGravity(Gravity.CENTER);

		dialogTitle = (TextView)dialog.findViewById(R.id.dialog_title);
		dialogContent = (TextView)dialog.findViewById(R.id.dialog_content);
		dialogCancel = (Button)dialog.findViewById(R.id.dialog_cancel);
		dialogComplete = (Button)dialog.findViewById(R.id.dialog_complete);

		dialogCancel.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});

		dialogComplete.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				android.os.Process.killProcess(android.os.Process.myPid());
			}
		});

		dialogWindow.setBackgroundDrawableResource(R.color.dialog_transparent);
		dialog.show();

	}

}
