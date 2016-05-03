package com.graduation.design.client.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.graduation.design.client.R;

public class TitleBarView extends LinearLayout {

	public interface OnTextListener {
		String onText();
	}

	View titleBarView;

	public OnTextListener onTextListener;
	public TextView titleTextView;
	public TitleBarView(Context context) {
		super(context);
		initView(context);
	}

	public TitleBarView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public TitleBarView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		initView(context);
	}

	private void initView(Context context) {
		titleBarView = LayoutInflater.from(context).inflate(
				R.layout.view_titlebar, null);
		 titleTextView = (TextView) titleBarView
				.findViewById(R.id.title);

		this.addView(titleBarView);
	}

	public void setOnTextListener(OnTextListener onTextListener) {
		this.onTextListener = onTextListener;
		if (onTextListener != null) {
			titleTextView.setText(onTextListener.onText());
		}
	}

}
