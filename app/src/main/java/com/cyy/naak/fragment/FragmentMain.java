package com.cyy.naak.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cyy.naak.fragmentdemo.R;
import com.cyy.naak.tools.MyToolbar;

import butterknife.Bind;
import butterknife.ButterKnife;

public class FragmentMain extends Fragment {
	@Bind(R.id.toolbar)
	Toolbar toolbar;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_main, container, false);
		ButterKnife.bind(this, view);

		MyToolbar myToolbar = new MyToolbar(getActivity(),toolbar);
		myToolbar.setToolbar(5,false,"首页");

		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

	}
}
