package com.photoshare.service.photos.filter;

import android.graphics.Bitmap;

/**
 * @author Aron
 *
 */
public class SoftGlowFilter implements ImageFilter {

	BrightContrastFilter contrastFx;

	GaussianBlurFilter gaussianBlurFx;
	private ImageData image = null;

	public SoftGlowFilter(Bitmap bmp) {
		image = new ImageData(bmp);
	}

	public SoftGlowFilter(Bitmap bmp, int nSigma, float nBrightness,
			float nContrast) {
		gaussianBlurFx = new GaussianBlurFilter(bmp);
		gaussianBlurFx.Sigma = nSigma;
		image = gaussianBlurFx.process();
		contrastFx = new BrightContrastFilter(image);
		contrastFx.BrightnessFactor = nBrightness;
		contrastFx.ContrastFactor = nContrast;

		image = contrastFx.process();
	}

	public ImageData process() {
		int width = image.getWidth();
		int height = image.getHeight();
		ImageData clone = image.clone();
		int old_r, old_g, old_b, r, g, b;
		for (int x = 0; x < (width - 1); x++) {
			for (int y = 0; y < (height - 1); y++) {
				old_r = clone.getRComponent(x, y);
				old_g = clone.getGComponent(x, y);
				old_b = clone.getBComponent(x, y);

				r = 255 - (255 - old_r) * (255 - image.getRComponent(x, y))
						/ 255;
				g = 255 - (255 - old_g) * (255 - image.getGComponent(x, y))
						/ 255;
				b = 255 - (255 - old_b) * (255 - image.getBComponent(x, y))
						/ 255;
				image.setPixelColor(x, y, r, g, b);
			}
		}
		return image;
	}

}
