package com.cyy.naak.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyy.naak.fragmentdemo.R;

public class FragmentSearch extends Fragment {
	private TextView mTv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_search, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		mTv = (TextView)getView().findViewById(R.id.title_tv);
		mTv.setText("搜索");
	}

	@Override
	public void onPause() {
		super.onPause();
	}

}
