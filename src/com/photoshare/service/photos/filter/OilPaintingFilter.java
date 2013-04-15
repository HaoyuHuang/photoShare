package com.photoshare.service.photos.filter;

import android.graphics.Bitmap;

import com.photoshare.utils.RandomUtils;

public class OilPaintingFilter implements ImageFilter {

	private ImageData image = null;

	public OilPaintingFilter(Bitmap raw) {
		image = new ImageData(raw);
	}

	public ImageData process() {
		RandomUtils.setSeed(1000);
		int width = image.getWidth();
		int height = image.getHeight();
		int randomNumber;
		int newX = 0;
		int newY = 0;
		int pixel;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				randomNumber = RandomUtils.getNextInt(8);
				switch (randomNumber) {
				case 0:
					newX = x - 1;
					newY = y - 1;
					break;
				case 1:
					newX = x - 1;
					newY = y;
					break;
				case 2:
					newX = x - 1;
					newY = y + 1;
					break;
				case 3:
					newX = x;
					newY = y - 1;
					break;
				case 4:
					newX = x;
					newY = y + 1;
					break;
				case 5:
					newX = x + 1;
					newY = y - 1;
					break;
				case 6:
					newX = x + 1;
					newY = y;
					break;
				case 7:
					newX = x + 1;
					newY = y + 1;
					break;
				}
				newX = newX >= 0 ? newX : 0;
				newY = newY >= 0 ? newY : 0;
				newX = newX >= width ? x : newX;
				newY = newY >= height ? y : newY;
				pixel = image.getPixelColor(newX, newY);
				image.setPixelColor(x, y, pixel);
			}
		}
		return image;
	}
}
