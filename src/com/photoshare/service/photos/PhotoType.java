package com.photoshare.service.photos;

public enum PhotoType {

	SMALL(180, 180), MIDDLE(360, 360), LARGE(720, 720);

	private int width;

	private int height;

	private PhotoType(int width, int height) {
		this.width = width;
		this.height = height;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
