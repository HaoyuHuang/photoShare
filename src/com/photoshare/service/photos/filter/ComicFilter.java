package com.photoshare.service.photos.filter;

import com.photoshare.service.photos.factory.PhotoFactory;

import android.graphics.Bitmap;

/**
 * @author Aron
 * 
 */
public class ComicFilter implements ImageFilter {

	private ImageData image = null;

	public ComicFilter(Bitmap bmp) {
		image = new ImageData(bmp);
	}

	public ImageData process() {
		int width = image.getWidth();
		int height = image.getHeight();
		int R, G, B, pixel;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				R = image.getRComponent(x, y);
				G = image.getGComponent(x, y);
				B = image.getBComponent(x, y);

				// R = |g �C b + g + r| * r / 256;
				pixel = G - B + G + R;
				if (pixel < 0)
					pixel = -pixel;
				pixel = pixel * R / 256;
				if (pixel > 255)
					pixel = 255;
				R = pixel;

				// G = |b �C g + b + r| * r / 256;
				pixel = B - G + B + R;
				if (pixel < 0)
					pixel = -pixel;
				pixel = pixel * R / 256;
				if (pixel > 255)
					pixel = 255;
				G = pixel;

				// B = |b �C g + b + r| * g / 256;
				pixel = B - G + B + R;
				if (pixel < 0)
					pixel = -pixel;
				pixel = pixel * G / 256;
				if (pixel > 255)
					pixel = 255;
				B = pixel;
				image.setPixelColor(x, y, R, G, B);
			}
		}
		Bitmap bitmap = image.getDstBitmap();
		bitmap = PhotoFactory.toGrayscale(bitmap);
		image = new ImageData(bitmap);
		return image;
	}

}
