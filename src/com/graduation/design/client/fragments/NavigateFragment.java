package com.graduation.design.client.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.graduation.design.client.R;
import com.graduation.design.client.widgets.TitleBarView;
import com.graduation.design.client.widgets.TitleBarView.OnTextListener;

public class NavigateFragment extends Fragment  {

	View view;
	/**
	 * 只会绘制一次UI
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_navigate, null);
		initViews();
		return view;
	}
	private void initViews(){
	}

	 
}
