package com.graduation.design.client.utils;

import android.util.Log;

public class Debug {

	private static boolean isDeveloper = true;

	private static final String TAG = "MainActivity";

	public static void log(String key, String value) {
		if (isDeveloper) {// ¿ª·¢ÖÐ
			Log.i(TAG, key + ":" + value);
		}
	}
}
