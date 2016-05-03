package com.graduation.design.client.utils;

import java.io.File;
import java.util.Vector;

import android.os.Environment;

public class FileUtil {
	// �ļ���·��
	private static String directory = "A_h264";
	// SD��·��
	private static String storagePath = Environment
			.getExternalStorageDirectory().getPath() + "/" + directory;

	/**
	 * ����name��ȡ��Ƶ�ļ����ڵ�·��
	 * 
	 * @param fileName
	 *            File����
	 * @return
	 */
	private static File getItemFile(String fileName) {
		return new File(getSDCardPathToFile().getAbsolutePath() + "/"
				+ fileName);
	}

	/**
	 * ����name��ȡ��Ƶ�ļ����ڵ�·��
	 * 
	 * @param fileName
	 *            String����
	 * @return
	 */
	public static String getItemFileToString(String fileName) {
		return getSDCardPath() + "/" + fileName;
	}

	/**
	 * ��ȡ�ļ���·��
	 * 
	 * @return String�͵�ַ
	 */
	public static String getSDCardPath() {
		// Debug.log("��ȡ�ļ���·��String", storagePath);
		return storagePath;
	}

	/**
	 * ��ȡ�ļ���·��
	 * 
	 * @return File�͵�ַ
	 */
	private static File getSDCardPathToFile() {
		File file = new File(getSDCardPath());

		return file;
	}

	/**
	 * ��SD�л�ȡ��Ƶ�ļ�
	 * 
	 * @return �漯����
	 */
	public static Vector<String> getVideoDataFromSDCard() {
		Vector<String> files = new Vector<String>();
		String[] fileList = getSDCardPathToFile().list();

		if (fileList != null) {
			for (int i = 0; i < fileList.length; i++) {
				String fileName = fileList[i];

				if (fileName.endsWith("h264") && getItemFile(fileName).isFile()) {
					String[] splitFileName = fileName.split("\\.");
					for (int j = 0; j < splitFileName.length; j++) {
						if (splitFileName[j].contains("x")) {
							String[] dims = splitFileName[j].split("x");
							if (dims.length == 2) {
								files.add(fileName);
								break;
							}
						}
					}
				}
			}
		}

		return files;

	}

	/**
	 * �Ƿ�����ļ��л����Ƿ񴴽�
	 * 
	 * @return
	 */
	public static boolean isHasSDCard() {
		File file = new File(getSDCardPath());
		if (file.mkdirs() || file.exists()) {
			return true;
		}
		return false;
	}
}
