package com.photoshare.service.photos.filter;

import android.graphics.Bitmap;

/**
 * @author Aron
 * 
 */
public class NoiseFilter implements ImageFilter {

	private ImageData image = null;
	public float Intensity = 0.2f;

	public NoiseFilter(Bitmap bmp) {
		image = new ImageData(bmp);
	}

	public NoiseFilter(ImageData iData) {
		image = iData;
	}

	public static int getRandomInt(int a, int b) {
		int min = Math.min(a, b);
		int max = Math.max(a, b);
		return min + (int) (Math.random() * (max - min + 1));
	}

	public ImageData process() {
		int r, g, b;
		int num = (int) (this.Intensity * 32768f);
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				r = image.getRComponent(x, y);
				g = image.getGComponent(x, y);
				b = image.getBComponent(x, y);
				if (num != 0) {
					int rr = getRandomInt(-255, 0xff) * num;
					int gg = getRandomInt(-255, 0xff) * num;
					int bb = getRandomInt(-255, 0xff) * num;
					int rrr = r + (rr >> 15);
					int ggg = g + (gg >> 15);
					int bbb = b + (bb >> 15);
					r = (rrr > 0xff) ? ((byte) 0xff) : ((rrr < 0) ? ((byte) 0)
							: ((byte) rrr));
					g = (ggg > 0xff) ? ((byte) 0xff) : ((ggg < 0) ? ((byte) 0)
							: ((byte) ggg));
					b = (bbb > 0xff) ? ((byte) 0xff) : ((bbb < 0) ? ((byte) 0)
							: ((byte) bbb));
				}
				image.setPixelColor(x, y, r, g, b);
			}
		}
		return image;
	}

}
