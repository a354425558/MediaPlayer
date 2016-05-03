package com.graduation.design.client.activitys;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.graduation.design.client.R;
import com.graduation.design.client.fragments.HomeFragment;
import com.graduation.design.client.fragments.MySetFragment;
import com.graduation.design.client.fragments.NavigateFragment;
import com.graduation.design.client.fragments.ShipFragments;
import com.graduation.design.client.utils.FragmentManager;
import com.graduation.design.client.utils.FragmentManager.OnSwitchTitleListener;
import com.graduation.design.client.widgets.TitleBarView;
import com.graduation.design.client.widgets.TitleBarView.OnTextListener;

public class MainActivity extends FragmentActivity implements OnTextListener{

	List<Fragment> fList = new ArrayList<>();
	private RadioGroup radioGroup;
	TitleBarView title_navigate;
	public String[] textTitleStrings ={"在线视频","导航","我的","本地视频"};
	FragmentManager fragmentManager;
	FragmentTransaction fTransaction;
	static {
		try {
			System.loadLibrary("avutil-linphone-arm");
			System.loadLibrary("swscale-linphone-arm");
			System.loadLibrary("avcodec-linphone-arm");
			System.loadLibrary("ndk_h264dec");

		} catch (UnsatisfiedLinkError e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initViews();
		
	}
	private void initViews() {
		radioGroup = (RadioGroup) findViewById(R.id.radiogroup);
		 title_navigate = (TitleBarView) findViewById(R.id.title_navigate);
		 defaultTab();
	
		 fragmentManager = new FragmentManager(this, radioGroup, R.id.id_content,getFragmentList());
		 fragmentManager.setOnSwitchTitleListener(new MySwitchTitle());
	}
	private List<Fragment> getFragmentList(){
		fList.add(new HomeFragment());
		fList.add(new NavigateFragment());
		fList.add(new MySetFragment());
		fList.add(new ShipFragments());
		return fList;
	}
	/**
	 * 默认显示Home content
	 */
	private void defaultTab() { 
		 title_navigate.setOnTextListener(this);
		((RadioButton)radioGroup.getChildAt(0)).setChecked(true);
		if ( fTransaction == null) {
			 fTransaction = getSupportFragmentManager().beginTransaction();
		}
		 fTransaction.add(R.id.id_content, new MySetFragment());
		 fTransaction.commit();
	}
	class MySwitchTitle implements OnSwitchTitleListener{
		@Override
		public void onSwitchTitle(final int position) {
		
			title_navigate.setOnTextListener(new OnTextListener() {
				@Override
				public String onText() {
					return textTitleStrings[position];
				}
			});
		}
		
	}

	@Override
	public String onText() {
		return textTitleStrings[0];
	}
}
