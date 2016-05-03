package com.graduation.design.client.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.graduation.design.client.R;
import com.graduation.design.client.widgets.TitleBarView;
import com.graduation.design.client.widgets.TitleBarView.OnTextListener;

/**
 * 获取SD卡中的视频资源
 * 
 * @author Jack
 * 
 */
public class HomeFragment extends Fragment  {

	View view;
	ListView listView;
	Context context;
	TextView directory_text;

	/**
	 * 只会绘制一次UI
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_home, null);
		initViews();
		return view;
	}
	private void initViews(){
	}

	 

}
