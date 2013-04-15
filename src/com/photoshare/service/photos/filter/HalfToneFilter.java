package com.photoshare.service.photos.filter;

import android.graphics.Bitmap;

public class HalfToneFilter implements ImageFilter {

	private ImageData bitmap;

	private ImageData texture;

	private float softness = 0.5f;

	public HalfToneFilter(Bitmap bmp, Bitmap texture) {
		this.bitmap = new ImageData(bmp);
		this.texture = new ImageData(texture);
	}

	public ImageData process() {
		float s = softness * 255;
		int ir;
		int ig;
		int ib;
		int mr;
		int mg;
		int mb;
		int r;
		int g;
		int b;
		for (int i = 0; i < bitmap.getWidth(); i++) {
			for (int j = 0; j < bitmap.getHeight(); j++) {
				ir = bitmap.getRComponent(i, j);
				ig = bitmap.getGComponent(i, j);
				ib = bitmap.getBComponent(i, j);
				mr = texture.getRComponent(i, j);
				mg = texture.getGComponent(i, j);
				mb = texture.getBComponent(i, j);
				r = (int) (255 * (1 - cubeInterpolation(ir - s, ir + s, mr)));
				g = (int) (255 * (1 - cubeInterpolation(ig - s, ig + s, mg)));
				b = (int) (255 * (1 - cubeInterpolation(ib - s, ib + s, mb)));
				bitmap.setPixelColor(i, j, r, g, b);
			}
		}
		return bitmap;
	}

	private float cubeInterpolation(float a, float b, float x) {
		if (x < a)
			return 0;
		if (x >= b)
			return 1;
		x = (x - a) / (b - a);
		return x * x * (3 - 2 * x);
	}

}