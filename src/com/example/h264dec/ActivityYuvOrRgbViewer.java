package com.example.h264dec;

import android.app.Activity;
import android.opengl.GLSurfaceView;
import android.os.Bundle;
import android.view.SurfaceView;

import com.example.h264dec.display.AndroidVideoWindowImpl;
import com.example.h264dec.display.AndroidVideoWindowImpl.VideoWindowListener;
import com.graduation.design.client.R;
import com.graduation.design.client.utils.Debug;
/**
 * ���಻��ת�Ƶ���������
 *      ���ಥ�Ź�����JNI�ײ�C++����ʵ��
 * @author Jack
 *
 */
public class ActivityYuvOrRgbViewer extends Activity implements
		VideoWindowListener {
	private String _fileName = null;
	GLSurfaceView view;
	AndroidVideoWindowImpl mVideoWindow;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_content_play);

		_fileName = getIntent().getStringExtra("h264.fileName");
		Debug.log("��ȡ������Ƶ������:", _fileName);
		view = (GLSurfaceView) findViewById(R.id.surfaceOutputView);
		view.setZOrderOnTop(false);

		mVideoWindow = new AndroidVideoWindowImpl(view);
		mVideoWindow.setListener(this);
		mVideoWindow.init();
	}

	@Override
	public void onVideoRenderingSurfaceDestroyed(AndroidVideoWindowImpl vw) {
		stopVideo();
	}

	@Override
	public void onVideoRenderingSurfaceReady(AndroidVideoWindowImpl vw,
			SurfaceView surface) {
		setVideoWindowId(vw, _fileName);

	}

	//������Ƶ��������
	native void setVideoWindowId(Object wId, String path);

	//ֹͣ������Ƶ
	native void stopVideo();
}
