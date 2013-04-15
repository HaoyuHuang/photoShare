package com.photoshare.service.photos.filter;

import android.graphics.Bitmap;

public class SwirlFilter implements ImageFilter {

	private ImageData image;

	// recommended scope is [0.1 ~ 0.001]
	private double degree = 0.02d; // default value,

	public SwirlFilter(Bitmap bmp) {
		super();
		this.image = new ImageData(bmp);
	}

	public ImageData process() {
		int width = image.getWidth();
		int height = image.getHeight();
		int centerX = width / 2;
		int centerY = height / 2;
		double theta, radius;
		double newX, newY;
		int offsetX = 0, offsetY = 0;
		for (int row = 0; row < height; row++) {
			int rgb;
			for (int col = 0; col < width; col++) {

				int trueX = col - centerX;
				int trueY = row - centerY;
				theta = Math.atan2((trueY), (trueX));
				radius = Math.sqrt(trueX * trueX + trueY * trueY);

				// the top trick is to add (degree * radius), generate the swirl
				// effect...
				newX = centerX + (radius * Math.cos(theta + degree * radius));
				newY = centerY + (radius * Math.sin(theta + degree * radius));

				if (newX > 0 && newX < width) {
					offsetX = (int) newX;
				} else {
					offsetX = col;
				}

				if (newY > 0 && newY < height) {
					offsetY = (int) newY;
				} else {
					offsetY = row;
				}

				rgb = image.getPixelColor(offsetX, offsetY);

				// use newX, newY and fill the pixel data now...
				image.setPixelColor(col, row, rgb);
			}
		}
		return image;
	}

}
