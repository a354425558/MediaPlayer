package com.example.h264dec.display;

public class OpenGLESDisplay {
	/**
	 * ���಻��ת�Ƶ���������
	 * @param ptr
	 * @param width
	 * @param height
	 */
	public static native void init(int ptr, int width, int height);

	public static native void render(int ptr);
}
