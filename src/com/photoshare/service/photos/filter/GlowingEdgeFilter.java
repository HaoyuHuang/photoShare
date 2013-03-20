package com.photoshare.service.photos.filter;

import android.graphics.Bitmap;

/**
 * @author Aron
 * 
 */
public class GlowingEdgeFilter implements ImageFilter {
	private ImageData image = null;

	public GlowingEdgeFilter(Bitmap bmp) {
		image = new ImageData(bmp);
	}

	public ImageData process() {
		int width = image.getWidth();
		int height = image.getHeight();
		// ͼ��ʵ�ʴ�������
		// ���������� 1 �У������� 1 ��
		int rectTop = 0;
		int rectBottom = height - 1;
		int rectLeft = 0;
		int rectRight = width - 1;
		int pixel;

		// R = p[2];
		// G = p[1];
		// B = p[0];
		int R, G, B;
		for (int y = rectTop; y < rectBottom; y++) {
			for (int x = rectLeft; x < rectRight; x++) {
				pixel = (int) (Math.pow((image.getBComponent(x, y) - image
						.getBComponent(x, y, width)), 2) + Math
						.pow((image.getBComponent(x, y) - image.getBComponent(
								x, y, 1)), 2));
				pixel = (int) (Math.sqrt(pixel) * 2);

				if (pixel < 0)
					pixel = 0;
				if (pixel > 255)
					pixel = 255;

				B = pixel;
				pixel = (int) (Math.pow((image.getGComponent(x, y) - image
						.getGComponent(x, y, width)), 2) + Math
						.pow((image.getGComponent(x, y) - image.getGComponent(
								x, y, 1)), 2));
				pixel = (int) (Math.sqrt(pixel) * 2);

				if (pixel < 0)
					pixel = 0;
				if (pixel > 255)
					pixel = 255;

				G = pixel;
				pixel = (int) (Math.pow((image.getRComponent(x, y) - image
						.getRComponent(x, y, width)), 2) + Math
						.pow((image.getRComponent(x, y) - image.getRComponent(
								x, y, 1)), 2));
				pixel = (int) (Math.sqrt(pixel) * 2);

				if (pixel < 0)
					pixel = 0;
				if (pixel > 255)
					pixel = 255;

				R = pixel;

				image.setPixelColor(x, y, R, G, B);
			} // x
		} // y

		return image;
	}
}
