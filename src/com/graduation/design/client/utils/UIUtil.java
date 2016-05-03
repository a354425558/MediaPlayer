package com.graduation.design.client.utils;

import android.content.Context;

public class UIUtil {

	public static void Toast(Context context, String text) {
		android.widget.Toast.makeText(context, text,
				android.widget.Toast.LENGTH_SHORT).show();
	}
}
