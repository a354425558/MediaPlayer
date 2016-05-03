package com.example.h264dec.display;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;

import android.content.Context;
import android.graphics.PixelFormat;
import android.opengl.GLSurfaceView;
import android.util.AttributeSet;
import android.util.Log;

import com.graduation.design.client.utils.Debug;

/**
 * GLSurfaceView控制视频播放View
 * @author Jack
 */
public class GL2JNIView extends GLSurfaceView {

	/**
	 * EGL配置信息
	 * 
	 * @author Jack
	 * 
	 */
	private static class ConfigChooser implements
			GLSurfaceView.EGLConfigChooser {
		// 子类可以调整这些值
		protected int mRedSize;
		protected int mGreenSize;
		protected int mBlueSize;
		protected int mAlphaSize;
		protected int mDepthSize;
		protected int mStencilSize;
		private int[] mValue = new int[1];

		private static int EGL_OPENGL_ES2_BIT = 4;
		private static int[] s_configAttribs2 = { EGL10.EGL_RED_SIZE, 4,
				EGL10.EGL_GREEN_SIZE, 4, EGL10.EGL_BLUE_SIZE, 4,
				EGL10.EGL_RENDERABLE_TYPE, EGL_OPENGL_ES2_BIT, EGL10.EGL_NONE };

		public ConfigChooser(int r, int g, int b, int a, int depth, int stencil) {
			mRedSize = r;
			mGreenSize = g;
			mBlueSize = b;
			mAlphaSize = a;
			mDepthSize = depth;
			mStencilSize = stencil;
		}

		@Override
		public EGLConfig chooseConfig(EGL10 arg0, EGLDisplay arg1) {
			int[] num_Configs = new int[1];
			arg0.eglChooseConfig(arg1, s_configAttribs2, null, 0, num_Configs);

			int numConfigs = num_Configs[0];

			if (numConfigs <= 0) {
				throw new IllegalArgumentException(
						"No configs match configSpec");
			}
			EGLConfig[] configs = new EGLConfig[numConfigs];
			arg0.eglChooseConfig(arg1, s_configAttribs2, configs, numConfigs,
					num_Configs);

			if (DEBUG) {
				printConfigs(arg0, arg1, configs);
			}
			return chooseConfig(arg0, arg1, configs);
		}

		// 自定义方法
		public EGLConfig chooseConfig(EGL10 egl, EGLDisplay display,
				EGLConfig[] configs) {
			for (EGLConfig config : configs) {
				int d = findConfigAttrib(egl, display, config,
						EGL10.EGL_DEPTH_SIZE, 0);
				int s = findConfigAttrib(egl, display, config,
						EGL10.EGL_STENCIL_SIZE, 0);

				// We need at least mDepthSize and mStencilSize bits
				if (d < mDepthSize || s < mStencilSize)
					continue;

				// We want an *exact* match for red/green/blue/alpha
				int r = findConfigAttrib(egl, display, config,
						EGL10.EGL_RED_SIZE, 0);
				int g = findConfigAttrib(egl, display, config,
						EGL10.EGL_GREEN_SIZE, 0);
				int b = findConfigAttrib(egl, display, config,
						EGL10.EGL_BLUE_SIZE, 0);
				int a = findConfigAttrib(egl, display, config,
						EGL10.EGL_ALPHA_SIZE, 0);

				if (r == mRedSize && g == mGreenSize && b == mBlueSize
						&& a == mAlphaSize)
					return config;
			}
			return null;
		}

		// 自定义方法
		private int findConfigAttrib(EGL10 egl, EGLDisplay display,
				EGLConfig config, int attribute, int defaultValue) {

			if (egl.eglGetConfigAttrib(display, config, attribute, mValue)) {
				return mValue[0];
			}
			return defaultValue;
		}

		// 自定义方法
		private void printConfig(EGL10 egl, EGLDisplay display, EGLConfig config) {
			int[] attributes = { EGL10.EGL_BUFFER_SIZE, EGL10.EGL_ALPHA_SIZE,
					EGL10.EGL_BLUE_SIZE,
					EGL10.EGL_GREEN_SIZE,
					EGL10.EGL_RED_SIZE,
					EGL10.EGL_DEPTH_SIZE,
					EGL10.EGL_STENCIL_SIZE,
					EGL10.EGL_CONFIG_CAVEAT,
					EGL10.EGL_CONFIG_ID,
					EGL10.EGL_LEVEL,
					EGL10.EGL_MAX_PBUFFER_HEIGHT,
					EGL10.EGL_MAX_PBUFFER_PIXELS,
					EGL10.EGL_MAX_PBUFFER_WIDTH,
					EGL10.EGL_NATIVE_RENDERABLE,
					EGL10.EGL_NATIVE_VISUAL_ID,
					EGL10.EGL_NATIVE_VISUAL_TYPE,
					0x3030, // EGL10.EGL_PRESERVED_RESOURCES,
					EGL10.EGL_SAMPLES,
					EGL10.EGL_SAMPLE_BUFFERS,
					EGL10.EGL_SURFACE_TYPE,
					EGL10.EGL_TRANSPARENT_TYPE,
					EGL10.EGL_TRANSPARENT_RED_VALUE,
					EGL10.EGL_TRANSPARENT_GREEN_VALUE,
					EGL10.EGL_TRANSPARENT_BLUE_VALUE,
					0x3039, // EGL10.EGL_BIND_TO_TEXTURE_RGB,
					0x303A, // EGL10.EGL_BIND_TO_TEXTURE_RGBA,
					0x303B, // EGL10.EGL_MIN_SWAP_INTERVAL,
					0x303C, // EGL10.EGL_MAX_SWAP_INTERVAL,
					EGL10.EGL_LUMINANCE_SIZE, EGL10.EGL_ALPHA_MASK_SIZE,
					EGL10.EGL_COLOR_BUFFER_TYPE, EGL10.EGL_RENDERABLE_TYPE,
					0x3042 // EGL10.EGL_CONFORMANT
			};
			String[] names = { "EGL_BUFFER_SIZE", "EGL_ALPHA_SIZE",
					"EGL_BLUE_SIZE", "EGL_GREEN_SIZE", "EGL_RED_SIZE",
					"EGL_DEPTH_SIZE", "EGL_STENCIL_SIZE", "EGL_CONFIG_CAVEAT",
					"EGL_CONFIG_ID", "EGL_LEVEL", "EGL_MAX_PBUFFER_HEIGHT",
					"EGL_MAX_PBUFFER_PIXELS", "EGL_MAX_PBUFFER_WIDTH",
					"EGL_NATIVE_RENDERABLE", "EGL_NATIVE_VISUAL_ID",
					"EGL_NATIVE_VISUAL_TYPE", "EGL_PRESERVED_RESOURCES",
					"EGL_SAMPLES", "EGL_SAMPLE_BUFFERS", "EGL_SURFACE_TYPE",
					"EGL_TRANSPARENT_TYPE", "EGL_TRANSPARENT_RED_VALUE",
					"EGL_TRANSPARENT_GREEN_VALUE",
					"EGL_TRANSPARENT_BLUE_VALUE", "EGL_BIND_TO_TEXTURE_RGB",
					"EGL_BIND_TO_TEXTURE_RGBA", "EGL_MIN_SWAP_INTERVAL",
					"EGL_MAX_SWAP_INTERVAL", "EGL_LUMINANCE_SIZE",
					"EGL_ALPHA_MASK_SIZE", "EGL_COLOR_BUFFER_TYPE",
					"EGL_RENDERABLE_TYPE", "EGL_CONFORMANT" };
			int[] value = new int[1];
			for (int i = 0; i < attributes.length; i++) {
				int attribute = attributes[i];
				String name = names[i];
				if (egl.eglGetConfigAttrib(display, config, attribute, value)) {
					Log.w("GLES", String.format("  %s: %d\n", name, value[0]));
				} else {
					// Log.w(TAG, String.format("  %s: failed\n", name));
					while (egl.eglGetError() != EGL10.EGL_SUCCESS)
						;
				}
			}
		}

		private void printConfigs(EGL10 egl, EGLDisplay display,
				EGLConfig[] configs) {
			int numConfigs = configs.length;
			Log.w("GLES", String.format("%d configurations", numConfigs));
			for (int i = 0; i < numConfigs; i++) {
				Log.w("GLES", String.format("Configuration %d:\n", i));
				printConfig(egl, display, configs[i]);
			}

		}
	}

	/**
	 * 创建内部类实现接口功能
	 * 
	 * @param prompt
	 * @param egl10
	 */
	private static class ContextFactory implements
			GLSurfaceView.EGLContextFactory {

		private static int EGL_CONTEXT_CLIENT_VERSION = 0X3098;

		@Override
		public EGLContext createContext(EGL10 egl10, EGLDisplay arg1,
				EGLConfig arg2) {
			// 检查EGL错误
			checkEglError("创建egl上下文对象之前", egl10);
			int[] attrib_list = { EGL_CONTEXT_CLIENT_VERSION, 2, EGL10.EGL_NONE };
			EGLContext context = egl10.eglCreateContext(arg1, arg2,
					EGL10.EGL_NO_CONTEXT, attrib_list);
			// 检查EGL错误
			checkEglError("创建之后", egl10);

			return context;
		}

		@Override
		public void destroyContext(EGL10 arg0, EGLDisplay arg1, EGLContext arg2) {
			arg0.eglDestroyContext(arg1, arg2);
		}

	}

	private static boolean DEBUG = false;

	/**
	 * 检查egl错误信息
	 * 
	 * @param prompt
	 * @param egl10
	 */
	private static void checkEglError(String prompt, EGL10 egl10) {
		int error;
		while ((error = egl10.eglGetError()) != EGL10.EGL_SUCCESS) {
			Debug.log("myEGL",
					String.format("%s: EGL error: 0x%x", prompt, error));
		}
	}

	public GL2JNIView(Context context) {
		super(context);
		init(false, 0, 0);
	}

	public GL2JNIView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init(false, 0, 0);
	}

	/**
	 * 
	 * @param translucent
	 *            半透明的
	 * @param depth
	 *            深度
	 * @param stencil
	 *            模板
	 */
	private void init(boolean translucent, int depth, int stencil) {
		if (translucent) {
			this.getHolder().setFormat(PixelFormat.TRANSLUCENT);
		}

		setEGLContextFactory(new ContextFactory());

		setEGLConfigChooser(translucent ? new ConfigChooser(8, 8, 8, 8, depth,
				stencil) : new ConfigChooser(5, 6, 5, 0, depth, stencil));

	}
}
