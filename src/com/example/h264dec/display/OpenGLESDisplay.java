package com.example.h264dec.display;

public class OpenGLESDisplay {
	/**
	 * 此类不能转移到其他包下
	 * @param ptr
	 * @param width
	 * @param height
	 */
	public static native void init(int ptr, int width, int height);

	public static native void render(int ptr);
}
