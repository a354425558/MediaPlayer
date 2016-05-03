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
 * 此类不能转移到其他包下
 *      该类播放功能由JNI底层C++代码实现
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
		Debug.log("获取到的视频名称是:", _fileName);
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

	//根据视频名来播放
	native void setVideoWindowId(Object wId, String path);

	//停止播放视频
	native void stopVideo();
}
