package com.photoshare.service.photos.filter;

/**
 * @author Aron
 *
 */
public class GradientFilter implements ImageFilter {

	private Palette palette = null;
	public Gradient Gradient;
	public float OriginAngleDegree;
	ImageData imageData;

	public GradientFilter(ImageData imageData) {
		this.OriginAngleDegree = 0f;
		this.Gradient = new Gradient();
		this.imageData = imageData;
	}

	public void ClearCache() {
		this.palette = null;
	}

	public ImageData process() {
		int width = imageData.getWidth();
		int height = imageData.getHeight();
		double d = this.OriginAngleDegree * 0.0174532925;
		float cos = (float) Math.cos(d);
		float sin = (float) Math.sin(d);
		float radio = (cos * width) + (sin * height);
		float dcos = cos * radio;
		float dsin = sin * radio;
		int dist = (int) Math.sqrt((double) ((dcos * dcos) + (dsin * dsin)));
		dist = Math.max(Math.max(dist, width), height);

		if ((this.palette == null) || (dist != this.palette.Length)) {
			this.palette = this.Gradient.CreatePalette(dist);
		}
		int[] red = this.palette.Red;
		int[] green = this.palette.Green;
		int[] blue = this.palette.Blue;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				radio = (cos * j) + (sin * i);
				dcos = cos * radio;
				dsin = sin * radio;
				dist = (int) Math
						.sqrt((double) ((dcos * dcos) + (dsin * dsin)));
				imageData.setPixelColor(j, i, red[dist], green[dist],
						blue[dist]);
			}
		}
		return imageData;
	}
}
