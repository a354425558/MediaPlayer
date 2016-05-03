package com.graduation.design.client.fragments;

import java.util.Arrays;
import java.util.Vector;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.h264dec.ActivityYuvOrRgbViewer;
import com.graduation.design.client.R;
import com.graduation.design.client.utils.FileUtil;
import com.graduation.design.client.utils.UIUtil;
import com.graduation.design.client.widgets.TitleBarView;
import com.graduation.design.client.widgets.TitleBarView.OnTextListener;
/**
 * ������Ƶ
 * @author Jack
 */
public class ShipFragments extends Fragment implements OnItemClickListener{

	View view;
	ListView listView;
	Context context;
	/**
	 * ֻ�����һ��UI
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		context = getActivity();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_ship, null);
		initViews();
		return view;
	}
	private void initViews(){
		listView = (ListView) view.findViewById(R.id.videolist);
		listView.setOnItemClickListener(this);
		getVideoData();
	}

	/**
	 * ��File�л�ȡVideo
	 */
	private void getVideoData() {
		if (!FileUtil.isHasSDCard()) {
			UIUtil.Toast(context, "�ڴ濨·��������");
		}
		Vector<String> vector = FileUtil.getVideoDataFromSDCard();
		if (vector.size() > 0) {
			Object[] vectorObjects = vector.toArray();
			Arrays.sort(vectorObjects);

			// listView������
			ArrayAdapter<Object> aAdapter = new ArrayAdapter<Object>(context,
					R.layout.item_listview, vectorObjects);
			listView.setAdapter(aAdapter);

		} else {
			UIUtil.Toast(context, "��·����û����Ƶ�ļ�");
		}
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent resultIntent = null;
		resultIntent = new Intent(context, ActivityYuvOrRgbViewer.class);

		String fileName = ((TextView) arg1).getText().toString();
		resultIntent.putExtra("h264.fileName",
				FileUtil.getItemFileToString(fileName));
		startActivity(resultIntent);
	}
	 
}
