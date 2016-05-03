package com.graduation.design.client.utils;

import java.util.List;

import android.provider.Contacts.Intents.UI;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;

import com.graduation.design.client.R;
import com.graduation.design.client.activitys.MainActivity;
import com.graduation.design.client.fragments.HomeFragment;
import com.graduation.design.client.fragments.MySetFragment;
import com.graduation.design.client.fragments.NavigateFragment;
import com.graduation.design.client.fragments.ShipFragments;

public class FragmentManager implements OnCheckedChangeListener {

	private List<Fragment> fList;
	private RadioGroup groupId;
	private int fragmentContentId;
	private int currentTab = 0;
	MainActivity context;

	public FragmentManager(MainActivity context, RadioGroup groupId,
			int fragmentContentId,List<Fragment> fList) {
		this.fragmentContentId = fragmentContentId;
		this.context = context;
		this.fList = fList;
		this.groupId = groupId;
		groupId.setOnCheckedChangeListener(this);
	}
	@Override
	public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
		 for (int i = 0; i < fList.size(); i++) {
			 if (groupId.getChildAt(i).getId() == checkedId) {//Ŀ��tab
				clickAfter(checkedId,i);
				if (onSwitchTitleListener != null) {
					onSwitchTitleListener.onSwitchTitle(i);
				}
			}
		}
	}
	private void clickAfter(int checkedId, int i) {
		 Fragment fragment =fList.get(i);
		 FragmentTransaction fragmentTransaction = obtainFragmentTransaction(i);
		getCurrentFragment().onStop();
		if (fragment.isAdded()) {
			fragment.onStart();
		}else {
			fragmentTransaction.add(fragmentContentId, fragment);
		}
		showTab(i);
		fragmentTransaction.commit();
	}
	private void showTab(int i) {
		for (int j = 0; j < fList.size(); j++) {
			Fragment fragment =fList.get(j);
			FragmentTransaction fragmentTransaction = obtainFragmentTransaction(i);
			//i��Ŀ��tab, ���Ŀ��tab = j   ��˵����Ҫ��ʾ����
			if (i == j) {
				fragmentTransaction.show(fragment);
			}else {
				fragmentTransaction.hide(fragment);
				
			}
			fragmentTransaction.commit();
		}
		currentTab = i;
	}
	
	

	private Fragment getCurrentFragment() {
		return fList.get(currentTab);
		
	}

	/**
	 * Fragment��������,�����������Ч��
	 * @param i
	 * @return
	 */
	private FragmentTransaction obtainFragmentTransaction(int i) {
		FragmentTransaction fTransaction = null;
		if (fTransaction == null) {
			fTransaction = context.getSupportFragmentManager().beginTransaction();
		}
		// ���Ŀ��tab i �ȵ�ǰtab��,����Ч��Ϊ:
		if (currentTab < i) {
			 fTransaction.setCustomAnimations(R.anim.anim_fragment_left_in, R.anim.anim_fragment_left_out);
		} else {
			 fTransaction.setCustomAnimations(R.anim.anim_fragment_right_in, R.anim.anim_fragment_right_out);
		}
		return fTransaction;
	}
	public void setOnSwitchTitleListener(OnSwitchTitleListener onSwitchTitleListener){
		this.onSwitchTitleListener = onSwitchTitleListener;
	}
	OnSwitchTitleListener onSwitchTitleListener;
    public interface OnSwitchTitleListener {
		void onSwitchTitle(int position);
	}
}
