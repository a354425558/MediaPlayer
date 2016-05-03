package com.graduation.design.client.utils;

import java.io.File;
import java.util.Vector;

import android.os.Environment;

public class FileUtil {
	// 文件夹路径
	private static String directory = "A_h264";
	// SD卡路径
	private static String storagePath = Environment
			.getExternalStorageDirectory().getPath() + "/" + directory;

	/**
	 * 根据name获取视频文件所在的路径
	 * 
	 * @param fileName
	 *            File类型
	 * @return
	 */
	private static File getItemFile(String fileName) {
		return new File(getSDCardPathToFile().getAbsolutePath() + "/"
				+ fileName);
	}

	/**
	 * 根据name获取视频文件所在的路径
	 * 
	 * @param fileName
	 *            String类型
	 * @return
	 */
	public static String getItemFileToString(String fileName) {
		return getSDCardPath() + "/" + fileName;
	}

	/**
	 * 获取文件夹路径
	 * 
	 * @return String型地址
	 */
	public static String getSDCardPath() {
		// Debug.log("获取文件夹路径String", storagePath);
		return storagePath;
	}

	/**
	 * 获取文件夹路径
	 * 
	 * @return File型地址
	 */
	private static File getSDCardPathToFile() {
		File file = new File(getSDCardPath());

		return file;
	}

	/**
	 * 从SD中获取视频文件
	 * 
	 * @return 存集合里
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
	 * 是否存在文件夹或者是否创建
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
