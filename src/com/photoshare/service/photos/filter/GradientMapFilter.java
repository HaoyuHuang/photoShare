package com.photoshare.service.photos.filter;

import android.graphics.Bitmap;
import android.graphics.Color;

/**
 * @author Aron
 *
 */
public class GradientMapFilter implements ImageFilter {
	public GradientMapFilter(Bitmap bm) {
		this.Map = new Gradient();
		image = new ImageData(bm);
	}

	public GradientMapFilter(ImageData iData) {
		this.Map = new Gradient();
		image = iData;
	}

	public GradientMapFilter(Bitmap bm, Gradient gradient) {
		this.Map = gradient;
		this.BrightnessFactor = this.ContrastFactor = 0f;
		image = new ImageData(bm);
	}

	public GradientMapFilter(ImageData iData, Gradient gradient) {
		this.Map = gradient;
		this.BrightnessFactor = this.ContrastFactor = 0f;
		image = iData;
	}

	private ImageData image;
	public Gradient Map;
	public float BrightnessFactor;
	public float ContrastFactor;

	public ImageData process() {
		// TODO Auto-generated method stub
		Palette palette = this.Map.CreatePalette(0x100);
		int[] red = palette.Red;
		int[] green = palette.Green;
		int[] blue = palette.Blue;
		ImageData bitmap = image.clone();
		bitmap.clearImage(Color.WHITE);
		int bfactor = (int) (this.BrightnessFactor * 255f);
		float cfactor = 1f + this.ContrastFactor;
		cfactor *= cfactor;
		int limit = ((int) (cfactor * 32768f)) + 1;
		for (int i = 0; i < image.colorArray.length; i++) {
			int r = (image.colorArray[i] & 0x00FF0000) >>> 16;
			int g = (image.colorArray[i] & 0x0000FF00) >>> 8;
			int b = image.colorArray[i] & 0x000000FF;
			int index = (((r * 0x1b36) + (g * 0x5b8c)) + (b * 0x93e)) >> 15;
			if (bfactor != 0) {
				index += bfactor;
				index = (index > 0xff) ? 0xff : ((index < 0) ? 0 : index);
			}
			if (limit != 0x8001) {
				index -= 0x80;
				index = (index * limit) >> 15;
				index += 0x80;
				index = (index > 0xff) ? 0xff : ((index < 0) ? 0 : index);
			}
			bitmap.colorArray[i] = (0xff << 24) + (red[index] << 16)
					+ (green[index] << 8) + blue[index];
		}
		return bitmap;
	}
}
