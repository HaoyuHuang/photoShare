package com.photoshare.service.photos.filter;

import android.graphics.Bitmap;
import android.util.Log;

/**
 * @author Aron
 * 
 */
public class ZoomBlurFilter implements ImageFilter {

	private ImageData image = null;

	int m_length;
	double m_offset_x;
	double m_offset_y;
	int m_fcx, m_fcy;
	final int RADIUS_LENGTH = 64;

	int threadID = 0;
	int maxID = 0;

	public ZoomBlurFilter(Bitmap bmp, int nLength) {
		m_length = nLength;
		m_offset_x = 0f;
		m_offset_y = 0f;
		image = new ImageData(bmp);
		threadID = 0;
	}

	public ZoomBlurFilter(Bitmap bmp, int nLength, double offset_x,
			double offset_y) {
		m_length = (nLength >= 1) ? nLength : 1;
		m_offset_x = (offset_x > 2.0 ? 2.0 : (offset_x < -2.0 ? 0 : offset_x));
		m_offset_y = (offset_y > 2.0 ? 2.0 : (offset_y < -2.0 ? 0 : offset_y));
		image = new ImageData(bmp);
		threadID = 0;
	}

	public ImageData process() {
		int width = image.getWidth();
		int height = image.getHeight();
		m_fcx = (int) (width * m_offset_x * 32768.0) + (width * 32768);
		m_fcy = (int) (height * m_offset_y * 32768.0) + (height * 32768);
		final int ta = 255;
		ImageData clone = image.clone();
		// // new progressThread(clone, 0, 0, width, height / 2).start();
		// for (int i = 0; i < maxID; i++) {
		// new progressThread(clone, 0, (height / maxID)*i, width, height /
		// maxID * (i + 1)).start();
		// }
		// // new progressThread(clone, 0, height / 2, width, height).start();
		// // new progressThread(clone, 0, height / 2, width, height).start();
		for (int y = 0; y < height; y++) {
			for (int x = 0; x < width; x++) {
				int sr = 0, sg = 0, sb = 0, sa = 0;
				sr = clone.getRComponent(x, y) * ta;
				sg = clone.getGComponent(x, y) * ta;
				sb = clone.getBComponent(x, y) * ta;
				sa += ta;
				int fx = (x * 65536) - m_fcx;
				int fy = (y * 65536) - m_fcy;
				for (int i = 0; i < RADIUS_LENGTH; i++) {
					fx = fx - (fx / 16) * m_length / 1024;
					fy = fy - (fy / 16) * m_length / 1024;

					int u = (fx + m_fcx + 32768) / 65536;
					int v = (fy + m_fcy + 32768) / 65536;
					if (u >= 0 && u < width && v >= 0 && v < height) {
						sr += clone.getRComponent(u, v) * ta;
						sg += clone.getGComponent(u, v) * ta;
						sb += clone.getBComponent(u, v) * ta;
						sa += ta;
					}
				}
				image.setPixelColor(x, y, image.safeColor(sr / sa),
						image.safeColor(sg / sa), image.safeColor(sb / sa));
			}
		}
		while (true) {
			if (threadID == maxID) {
				return image;
			}
		}
	}

	class progressThread extends Thread {
		ImageData clone;
		ImageData imageData;
		int startX;
		int startY;
		int endY;
		int endX;

		public progressThread(ImageData clone, int startx, int starty,
				int endx, int endY) {
			this.clone = clone;
			this.startX = startx;
			this.startY = starty;
			this.endY = endY;
			this.endX = endx;
		}

		public void run() {
			final int ta = 255;
			int width = image.getWidth();
			int height = image.getHeight();
			Log.i("startY", startY + "");
			Log.i("startX", startX + "");
			Log.i("endX", endX + "");
			Log.i("endY", endY + "");
			for (int y = startY; y < endY; y++) {
				for (int x = startX; x < endX; x++) {
					int sr = 0, sg = 0, sb = 0, sa = 0;
					sr = clone.getRComponent(x, y) * ta;
					sg = clone.getGComponent(x, y) * ta;
					sb = clone.getBComponent(x, y) * ta;
					sa += ta;
					int fx = (x * 65536) - m_fcx;
					int fy = (y * 65536) - m_fcy;
					for (int i = 0; i < RADIUS_LENGTH; i++) {
						fx = fx - (fx / 16) * m_length / 1024;
						fy = fy - (fy / 16) * m_length / 1024;

						int u = (fx + m_fcx + 32768) / 65536;
						int v = (fy + m_fcy + 32768) / 65536;
						if (u >= 0 && u < width && v >= 0 && v < height) {
							sr += clone.getRComponent(u, v) * ta;
							sg += clone.getGComponent(u, v) * ta;
							sb += clone.getBComponent(u, v) * ta;
							sa += ta;
						}
					}
					image.setPixelColor(x, y, image.safeColor(sr / sa),
							image.safeColor(sg / sa), image.safeColor(sb / sa));
					// Log.i("x", x+"");
				}
				// Log.i("y", y+"");
			}
			threadID++;
		}
	}
}
