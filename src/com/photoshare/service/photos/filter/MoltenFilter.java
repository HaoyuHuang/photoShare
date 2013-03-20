package com.photoshare.service.photos.filter;

import android.graphics.Bitmap;

/**
 * @author Aron
 *
 */
public class MoltenFilter implements ImageFilter {
	private ImageData image = null; 

	public MoltenFilter(Bitmap bmp) {
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

				pixel = R * 128 / (G + B + 1);
				if (pixel < 0)
					pixel = 0;
				if (pixel > 255)
					pixel = 255;
				R = pixel;

				pixel = G * 128 / (B + R + 1);
				if (pixel < 0)
					pixel = 0;
				if (pixel > 255)
					pixel = 255;
				G = pixel;

				pixel = B * 128 / (R + G + 1);
				if (pixel < 0)
					pixel = 0;
				if (pixel > 255)
					pixel = 255;
				B = pixel;
				image.setPixelColor(x, y, R, G, B);
			} // x
		} // y

		return image;
	}

}
