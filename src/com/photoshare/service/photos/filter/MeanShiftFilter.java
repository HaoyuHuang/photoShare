package com.photoshare.service.photos.filter;

import android.graphics.Bitmap;

public class MeanShiftFilter implements ImageFilter {

	private ImageData imageData;

	private int radius;
	private float colorDistance;

	public MeanShiftFilter(Bitmap bitmap) {
		super();
		this.imageData = new ImageData(bitmap);
		radius = 3; // default shift radius
		colorDistance = 25; // default color distance
	}

	public ImageData process() {
		int width = imageData.getWidth();
		int height = imageData.getHeight();

		int length = width * height;

		// convert RGB color space to YIQ color space
		float[][] pixelsf = new float[width * height][3];
		for (int i = 0; i < length; i++) {
			int argb = imageData.getPixelColor(i % width, i / width);
			int r = (argb >> 16) & 0xff;
			int g = (argb >> 8) & 0xff;
			int b = (argb) & 0xff;
			pixelsf[i][0] = 0.299f * r + 0.587f * g + 0.114f * b; // Y
			pixelsf[i][1] = 0.5957f * r - 0.2744f * g - 0.3212f * b; // I
			pixelsf[i][2] = 0.2114f * r - 0.5226f * g + 0.3111f * b; // Q
		}

		int index = 0;
		float shift = 0;
		float repetition = 0;
		float radius2 = radius * radius;
		float dis2 = colorDistance * colorDistance;
		for (int row = 0; row < height; row++) {
			int ta = 255, tr = 0, tg = 0, tb = 0;
			for (int col = 0; col < width; col++) {
				int xc = col;
				int yc = row;
				int xcOld, ycOld;
				float YcOld, IcOld, QcOld;
				index = row * width + col;
				float[] yiq = pixelsf[index];
				float Yc = yiq[0];
				float Ic = yiq[1];
				float Qc = yiq[2];

				repetition = 0;
				do {
					xcOld = xc;
					ycOld = yc;
					YcOld = Yc;
					IcOld = Ic;
					QcOld = Qc;

					float mx = 0;
					float my = 0;
					float mY = 0;
					float mI = 0;
					float mQ = 0;
					int num = 0;

					for (int ry = -radius; ry <= radius; ry++) {
						int y2 = yc + ry;
						if (y2 >= 0 && y2 < height) {
							for (int rx = -radius; rx <= radius; rx++) {
								int x2 = xc + rx;
								if (x2 >= 0 && x2 < width) {
									if (ry * ry + rx * rx <= radius2) {
										yiq = pixelsf[y2 * width + x2];

										float Y2 = yiq[0];
										float I2 = yiq[1];
										float Q2 = yiq[2];

										float dY = Yc - Y2;
										float dI = Ic - I2;
										float dQ = Qc - Q2;

										if (dY * dY + dI * dI + dQ * dQ <= dis2) {
											mx += x2;
											my += y2;
											mY += Y2;
											mI += I2;
											mQ += Q2;
											num++;
										}
									}
								}
							}
						}
					}
					float num_ = 1f / num;
					Yc = mY * num_;
					Ic = mI * num_;
					Qc = mQ * num_;
					xc = (int) (mx * num_ + 0.5);
					yc = (int) (my * num_ + 0.5);
					int dx = xc - xcOld;
					int dy = yc - ycOld;
					float dY = Yc - YcOld;
					float dI = Ic - IcOld;
					float dQ = Qc - QcOld;

					shift = dx * dx + dy * dy + dY * dY + dI * dI + dQ * dQ;
					repetition++;
				} while (shift > 3 && repetition < 100);
				tr = (int) (Yc + 0.9563f * Ic + 0.6210f * Qc);
				tg = (int) (Yc - 0.2721f * Ic - 0.6473f * Qc);
				tb = (int) (Yc - 1.1070f * Ic + 1.7046f * Qc);
				imageData.setPixelColor(col, row, tr, tg, tb);
			}
		}
		return imageData;
	}

}
