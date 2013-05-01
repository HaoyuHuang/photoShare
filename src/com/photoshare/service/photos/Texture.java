package com.photoshare.service.photos;

import android.graphics.Bitmap;

public class Texture {
	private final String name;

	private final Bitmap texture;

	private final int width;

	private final int height;

	public Texture(String name, Bitmap texture, int width, int height) {
		super();
		this.name = name;
		this.texture = texture;
		this.width = width;
		this.height = height;
	}

	public String getName() {
		return name;
	}

	public Bitmap getTexture() {
		return texture;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

}
