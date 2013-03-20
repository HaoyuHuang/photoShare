package com.photoshare.service.photos.filter;

/**
 * @author Aron
 *
 */
public class SaturationModifyFilter implements ImageFilter {

	public float SaturationFactor = 0.5f;
	private ImageData imageData;

	public SaturationModifyFilter(ImageData imageData) {
		this.imageData = imageData;
	}

	public ImageData process() {
		// TODO Auto-generated method stub
		float saturation = this.SaturationFactor + 1f;
		float negosaturation = 1f - saturation;

		int r, g, b;
		for (int x = 0; x < imageData.getWidth(); x++) {
			for (int y = 0; y < imageData.getHeight(); y++) {
				r = imageData.getRComponent(x, y);
				g = imageData.getGComponent(x, y);
				b = imageData.getBComponent(x, y);

				float nego1 = negosaturation * 0.2126f;
				float ngeo2 = nego1 + saturation;
				float ngeo3 = negosaturation * 0.7152f;
				float nego4 = ngeo3 + saturation;
				float nego5 = negosaturation * 0.0722f;
				float nego6 = nego5 + saturation;
				float nego7 = ((r * ngeo2) + (g * ngeo3)) + (b * nego5);
				float nego8 = ((r * nego1) + (g * nego4)) + (b * nego5);
				float nego9 = ((r * nego1) + (g * ngeo3)) + (b * nego6);
				r = (nego7 > 255f) ? 255 : ((nego7 < 0f) ? 0 : ((int) nego7));
				g = (nego8 > 255f) ? 255 : ((nego8 < 0f) ? 0 : ((int) nego8));
				b = (nego9 > 255f) ? 255 : ((nego9 < 0f) ? 0 : ((int) nego9));
			}
		}
		return imageData;
	}
}
