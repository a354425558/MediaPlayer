package com.graduation.design.client.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.graduation.design.client.R;
import com.graduation.design.client.activitys.LoginActivity;
import com.graduation.design.client.activitys.MainActivity;
import com.graduation.design.client.utils.UIUtil;
import com.graduation.design.client.widgets.TitleBarView;
import com.graduation.design.client.widgets.TitleBarView.OnTextListener;

public class MySetFragment extends Fragment implements OnClickListener{

	View view;
	RelativeLayout include_login;
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
		view = inflater.inflate(R.layout.fragment_myset, null);
		initViews();
		return view;
	}
	private void initViews(){
		include_login = (RelativeLayout)view.findViewById(R.id.include_login);
		include_login.setOnClickListener(this);
	}

	@Override
	public void onClick(View arg0) {
		switch (arg0.getId()) {
		case R.id.include_login:
			UIUtil.Toast(getActivity(), "呵呵");
			Intent intent = new Intent(getActivity(),LoginActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

	 
}
