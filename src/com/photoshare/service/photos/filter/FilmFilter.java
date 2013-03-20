package com.photoshare.service.photos.filter;

import android.graphics.Bitmap;

import com.photoshare.service.photos.filter.ImageBlender.BlendMode;

/**
 * @author Aron
 * 
 */
public class FilmFilter implements ImageFilter {
	private GradientFilter gradient;
	private SaturationModifyFilter saturationFx;
	ImageData imageData;

	public FilmFilter(Bitmap bmp, float angle) {
		imageData = new ImageData(bmp);
		gradient = new GradientFilter(imageData);
		gradient.Gradient = Gradient.Fade();
		gradient.OriginAngleDegree = angle;
	}

	public ImageData process() {
		ImageData clone = imageData.clone();
		imageData = gradient.process();
		ImageBlender blender = new ImageBlender();
		blender.Mode = BlendMode.Multiply;
		saturationFx = new SaturationModifyFilter(blender.Blend(clone,
				imageData));
		saturationFx.SaturationFactor = -0.6f;
		return saturationFx.process();
	}

}
