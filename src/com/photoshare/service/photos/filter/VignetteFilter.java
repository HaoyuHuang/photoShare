package com.photoshare.service.photos.filter;

import android.graphics.Bitmap;

/**
 * @author Aron
 * 
 */
public class VignetteFilter implements ImageFilter {

	private ImageData image = null;
	public float Size = 0.5f;

	public VignetteFilter(Bitmap bmp) {
		image = new ImageData(bmp);
	}

	public VignetteFilter(ImageData iData) {
		image = iData;
	}

	public ImageData process() {
		int width = image.getWidth();
		int height = image.getHeight();
		int ratio = image.getWidth() > image.getHeight() ? image.getHeight()
				* 32768 / image.getWidth() : image.getWidth() * 32768
				/ image.getHeight();
		int r, g, b;
		int cx = image.getWidth() >> 1;
		int cy = image.getHeight() >> 1;
		int max = cx * cx + cy * cy;
		int min = (int) (max * (1 - Size));
		int diff = max - min;
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				r = image.getRComponent(x, y);
				g = image.getGComponent(x, y);
				b = image.getBComponent(x, y);
				int dx = cx - x;
				int dy = cy - y;
				if (width > height) {
					dx = (dx * ratio) >> 15;
				} else {
					dy = (dy * ratio) >> 15;
				}
				int distSq = dx * dx + dy * dy;

				if (distSq > min) {
					// Calculate vignette
					int v = ((max - distSq) << 8) / diff;
					v *= v;

					// Apply vignette
					int ri = (r * v) >> 16;
					int gi = (g * v) >> 16;
					int bi = (b * v) >> 16;

					// Check bounds
					r = (ri > 255 ? 255 : (ri < 0 ? 0 : ri));
					g = (gi > 255 ? 255 : (gi < 0 ? 0 : gi));
					b = (bi > 255 ? 255 : (bi < 0 ? 0 : bi));
				}

				image.setPixelColor(x, y, r, g, b);
			}
		}
		return image;
	}
}
