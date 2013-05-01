package com.photoshare.service.photos.filter;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;

/**
 * @author Aron
 * 
 */
public class ImageRawData {
	private Bitmap srcBitmap;
	private Bitmap dstBitmap;

	private int width;
	private int height;
	private int size;

	// protected int[] colorArray;

	public ImageRawData(Bitmap bmp) {
		srcBitmap = bmp;
		width = bmp.getWidth();
		height = bmp.getHeight();
		size = width * height;
		// dstBitmap = srcBitmap.copy(Config.ARGB_8888, true);
		dstBitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
		initColorArray();
	}

	public ImageData clone() {
		return new ImageData(this.srcBitmap);
	}

	public void clearImage(int color) {
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				setPixelColor(x, y, color);
			}
		}
	}

	 private void initColorArray() {
		 
	 }

	public int getSize() {
		return size;
	}

	public int getPixelColor(int x, int y) {
		return srcBitmap.getPixel(x, y);
	}

	public int getPixelColor(int index) {
		int x = index % width;
		int y = index / width;
		return srcBitmap.getPixel(x, y);
	}

	public int getPixelColor(int x, int y, int offset) {
		if (offset >= width) {
			offset -= width;
			y += 1;
		}
		return srcBitmap.getPixel(x, y);
		// return colorArray[y * srcBitmap.getWidth() + x + offset];
	}

	public int getRComponent(int x, int y) {
		return Color.red(srcBitmap.getPixel(x, y));
		// return Color.red(colorArray[y * srcBitmap.getWidth() + x]);
	}

	public int getRComponent(int x, int y, int offset) {
		if (offset >= width) {
			offset -= width;
			y += 1;
		}
		return Color.red(srcBitmap.getPixel(x, y));
		// return Color.red(colorArray[y * srcBitmap.getWidth() + x + offset]);
	}

	public int getGComponent(int x, int y) {
		return Color.green(srcBitmap.getPixel(x, y));
		// return Color.green(colorArray[y * srcBitmap.getWidth() + x]);
	}

	public int getGComponent(int x, int y, int offset) {
		if (offset >= width) {
			offset -= width;
			y += 1;
		}
		return Color.green(srcBitmap.getPixel(x, y));
		// return Color.green(colorArray[y * srcBitmap.getWidth() + x +
		// offset]);
	}

	public int getBComponent(int x, int y) {
		return Color.blue(srcBitmap.getPixel(x, y));
		// return Color.blue(colorArray[y * srcBitmap.getWidth() + x]);
	}

	public int getBComponent(int x, int y, int offset) {
		if (offset >= width) {
			offset -= width;
			y += 1;
		}
		return Color.blue(srcBitmap.getPixel(x, y));
		// return Color.blue(colorArray[y * srcBitmap.getWidth() + x + offset]);
	}

	public void setPixelColor(int x, int y, int r, int g, int b) {
		int rgbcolor = (255 << 24) + (r << 16) + (g << 8) + b;
		dstBitmap.setPixel(x, y, rgbcolor);
		// colorArray[((y * srcBitmap.getWidth() + x))] = rgbcolor;
	}

	public void setPixelColor(int index, int color) {
		int x = index % width;
		int y = index / width;
		dstBitmap.setPixel(x, y, color);
	}

	public void setPixelColor(int x, int y, int rgbcolor) {
		// colorArray[((y * srcBitmap.getWidth() + x))] = rgbcolor;
		dstBitmap.setPixel(x, y, rgbcolor);
	}

	public Bitmap getDstBitmap() {
		// dstBitmap.setPixels(colorArray, 0, width, 0, 0, width, height);
		return dstBitmap;
	}

	public int safeColor(int a) {
		if (a < 0)
			return 0;
		else if (a > 255)
			return 255;
		else
			return a;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
