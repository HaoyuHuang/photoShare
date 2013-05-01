package com.photoshare.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import android.os.Environment;

public class FileTools {

	public static boolean fileExist(String fileName) {
		File file = new File(fileName);
		return file.exists();
	}

	public static void makeDirs(String[] dirs) {
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			for (int i = 0; i < dirs.length; i++) {
				File destDir = new File(dirs[i]);
				if (!destDir.exists()) {
					destDir.mkdirs();
				}
			}
		}
	}

	public static File makeDir(String dir) {
		String status = Environment.getExternalStorageState();
		File destDir = null;
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			destDir = new File(dir);
			if (!destDir.exists()) {
				destDir.mkdirs();
			}
		}
		return destDir;
	}

	public static void makeFile(String dir, String fileName) {
		String filePath = dir + File.separator + fileName;
		File file = new File(filePath);
		if (!file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void deleteFile(String path) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
	}

	public static OutputStream OpenFile(String path, String name) {
		try {
			File file = new File(path);
			if (!file.exists()) {
				file.mkdirs();
			}
			file = new File(path, name);
			if (!file.exists()) {
				file.createNewFile();
			}
			OutputStream output = new FileOutputStream(file);
			return output;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static InputStream ReadFile(String path, String name) {
		try {
			File file = new File(path, name);
			if (!file.exists()) {
				return null;
			}
			InputStream input = new FileInputStream(file);
			return input;

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

}
