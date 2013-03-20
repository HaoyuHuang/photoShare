package com.photoshare.service.photos.filter;

import android.graphics.Bitmap;

/**
 * @author Aron
 *
 */
public class LomoFilter implements ImageFilter {

	private BrightContrastFilter contrastFx;
	private GradientMapFilter gradientMapFx;
	private ImageBlender blender = new ImageBlender();
	private VignetteFilter vignetteFX;
	private NoiseFilter noiseFx;

	private ImageData image;

	public LomoFilter(Bitmap bm) {
		image = new ImageData(bm);
		contrastFx = new BrightContrastFilter(image);
		contrastFx.BrightnessFactor = 0.05f;
		contrastFx.ContrastFactor = 0.5f;

		blender.Mixture = 0.5f;
		blender.Mode = ImageBlender.BlendMode.Multiply;

		vignetteFX = new VignetteFilter(image);
		vignetteFX.Size = 0.6f;

	}

	public ImageData process() {
		// TODO Auto-generated method stub
		ImageData tempImg = contrastFx.process();
		noiseFx = new NoiseFilter(tempImg);
		noiseFx.Intensity = 0.02f;
		tempImg = noiseFx.process();
		gradientMapFx = new GradientMapFilter(tempImg);
		image = gradientMapFx.process();
		image = blender.Blend(image, tempImg);
		vignetteFX = new VignetteFilter(image);
		vignetteFX.Size = 0.6f;
		image = vignetteFX.process();
		return image;
	}

}
