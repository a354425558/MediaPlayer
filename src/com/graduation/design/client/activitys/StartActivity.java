package com.graduation.design.client.activitys;

import com.graduation.design.client.R;
import com.graduation.design.client.R.id;
import com.graduation.design.client.R.layout;
import com.graduation.design.client.R.menu;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;

public class StartActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		StartTask task = new StartTask();
		task.execute("");
		
	}
	
	 class StartTask extends AsyncTask<String, String, String>{

		@Override
		protected String doInBackground(String... arg0) {
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(String result) {
//			super.onPostExecute(result);
			Intent intent = new Intent(StartActivity.this,MainActivity.class);
			startActivity(intent);
			finish();
		}
		
	}

}
