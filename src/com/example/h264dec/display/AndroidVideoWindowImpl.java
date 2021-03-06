package com.example.h264dec.display;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.opengl.GLSurfaceView;
import android.util.Log;
import android.view.Surface;
import android.view.Surface.OutOfResourcesException;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

public class AndroidVideoWindowImpl {
	private static class Renderer implements GLSurfaceView.Renderer {
		int ptr;
		boolean initPending;
		int width, height;

		public Renderer() {
			ptr = 0;
			initPending = false;
		}

		@Override
		public void onDrawFrame(GL10 gl) {
			/*
			 * See comment in setOpenGLESDisplay
			 */
			synchronized (this) {
				if (ptr == 0)
					return;
				if (initPending) {
					OpenGLESDisplay.init(ptr, width, height);
					initPending = false;
				}
				OpenGLESDisplay.render(ptr);
			}
		}

		@Override
		public void onSurfaceChanged(GL10 gl, int width, int height) {
			/* delay init until ptr is set */
			this.width = width;
			this.height = height;
			initPending = true;
		}

		@Override
		public void onSurfaceCreated(GL10 gl, EGLConfig config) {

		}

		public void setOpenGLESDisplay(int ptr) {
			/*
			 * Synchronize this with onDrawFrame: - they are called from
			 * different threads (Rendering thread and Linphone's one) -
			 * setOpenGLESDisplay can modify ptr while onDrawFrame is using it
			 */
			synchronized (this) {
				if (this.ptr != 0 && ptr != this.ptr) {
					initPending = true;
				}
				this.ptr = ptr;
			}
		}
	}

	/**
	 * Utility listener interface providing callback for Android events useful
	 * to Mediastreamer.
	 */
	public static interface VideoWindowListener {
		void onVideoRenderingSurfaceDestroyed(AndroidVideoWindowImpl vw);

		void onVideoRenderingSurfaceReady(AndroidVideoWindowImpl vw,
				SurfaceView surface);
	}

	public static int rotationToAngle(int r) {
		switch (r) {
		case Surface.ROTATION_0:
			return 0;
		case Surface.ROTATION_90:
			return 90;
		case Surface.ROTATION_180:
			return 180;
		case Surface.ROTATION_270:
			return 270;
		}
		return 0;
	}

	private SurfaceView mVideoRenderingView;
	// private SurfaceView mVideoPreviewView;
	private boolean useGLrendering;
	private Bitmap mBitmap;

	private Surface mSurface;;

	private VideoWindowListener mListener;

	private Renderer renderer;

	/**
	 * @param renderingSurface
	 *            Surface created by the application that will be used to render
	 *            decoded video stream
	 * @param previewSurface
	 *            Surface created by the application used by Android's Camera
	 *            preview framework
	 */
	public AndroidVideoWindowImpl(SurfaceView renderingSurface) {
		mVideoRenderingView = renderingSurface;
		// mVideoPreviewView = previewSurface;

		useGLrendering = (renderingSurface instanceof GLSurfaceView);

		mBitmap = null;
		mSurface = null;
		mListener = null;
	}

	public Bitmap getBitmap() {
		if (useGLrendering)
			Log.e("GLES",
					"View class does not match Video display filter used (you must use a non-GL View)");
		return mBitmap;
	}

	public Surface getSurface() {
		if (useGLrendering)
			Log.e("GLES",
					"View class does not match Video display filter used (you must use a non-GL View)");
		return mVideoRenderingView.getHolder().getSurface();
	}

	public void init() {
		// register callback for rendering surface events
		mVideoRenderingView.getHolder().addCallback(new Callback() {
			@Override
			public void surfaceChanged(SurfaceHolder holder, int format,
					int width, int height) {
				Log.i("GLES", "Video display surface is being changed.");
				if (!useGLrendering) {
					synchronized (AndroidVideoWindowImpl.this) {
						mBitmap = Bitmap.createBitmap(width, height,
								Config.RGB_565);
						mSurface = holder.getSurface();
					}
				}
				if (mListener != null)
					mListener.onVideoRenderingSurfaceReady(
							AndroidVideoWindowImpl.this, mVideoRenderingView);
				Log.w("GLES", "Video display surface changed");
			}

			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				Log.w("GLES", "Video display surface created");
			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				Log.d("GLES", "IN Video display surface destroyed");
				if (!useGLrendering) {
					synchronized (AndroidVideoWindowImpl.this) {
						mSurface = null;
						mBitmap = null;
					}
				}
				if (mListener != null)
					mListener
							.onVideoRenderingSurfaceDestroyed(AndroidVideoWindowImpl.this);
				Log.d("GLES", "OUT Video display surface destroyed");
			}
		});
		// register callback for preview surface events
		/*
		 * if (mVideoPreviewView != null) {
		 * mVideoPreviewView.getHolder().addCallback(new Callback(){ public void
		 * surfaceChanged(SurfaceHolder holder, int format, int width, int
		 * height) { Log.i("GLES","Video preview surface is being changed."); if
		 * (mListener!=null)
		 * mListener.onVideoPreviewSurfaceReady(AndroidVideoWindowImpl.this,
		 * mVideoPreviewView); Log.w("GLES","Video preview surface changed"); }
		 * 
		 * public void surfaceCreated(SurfaceHolder holder) {
		 * Log.w("GLES","Video preview surface created"); }
		 * 
		 * public void surfaceDestroyed(SurfaceHolder holder) { if
		 * (mListener!=null)
		 * mListener.onVideoPreviewSurfaceDestroyed(AndroidVideoWindowImpl
		 * .this); Log.d("GLES","Video preview surface destroyed"); } }); }
		 */

		if (useGLrendering) {
			renderer = new Renderer();
			((GLSurfaceView) mVideoRenderingView).setRenderer(renderer);
			((GLSurfaceView) mVideoRenderingView)
					.setRenderMode(GLSurfaceView.RENDERMODE_WHEN_DIRTY);
		}
	}

	public void release() {
		// mSensorMgr.unregisterListener(this);
	}

	public void requestRender() {
		((GLSurfaceView) mVideoRenderingView).requestRender();
	}

	public void setListener(VideoWindowListener l) {
		mListener = l;
	}

	public void setOpenGLESDisplay(int ptr) {
		if (!useGLrendering)
			Log.e("GLES",
					"View class does not match Video display filter used (you must use a GL View)");
		renderer.setOpenGLESDisplay(ptr);
	}

	// Called by the mediastreamer2 android display filter
	public synchronized void update() {
		if (mSurface != null) {
			try {
				Canvas canvas = mSurface.lockCanvas(null);
				canvas.drawBitmap(mBitmap, 0, 0, null);
				mSurface.unlockCanvasAndPost(canvas);

			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (OutOfResourcesException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
