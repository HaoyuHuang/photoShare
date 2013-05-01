package com.photoshare.cache;

import java.io.File;

import android.graphics.Bitmap;

import com.photoshare.service.photos.factory.PhotoFactory;
import com.photoshare.utils.Utils;

public class BitmapDiskCache {
	private static String diskCachePath = Utils.SDCARD_ABSOLUTE_PATH
			+ File.separator + Utils.APP_NAME + File.separator
			+ Utils.DIR_CACHE;

	public static void cache(Bitmap bmp, String fileName) {
		PhotoFactory.saveImage(bmp, diskCachePath, fileName, 100);
	}

	public static Bitmap retrieveFromCache(String url) {
		File file = new File(diskCachePath, url);
		if (file.exists()) {
			StringBuilder builder = new StringBuilder();
			builder.append(diskCachePath);
			builder.append(File.separator);
			builder.append(url);
			return PhotoFactory.readBitMap(builder.toString());
		}
		return null;
	}
}
