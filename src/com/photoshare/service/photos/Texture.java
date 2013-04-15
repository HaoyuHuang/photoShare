package com.photoshare.service.photos;

import android.graphics.Bitmap;

public class Texture {
	private final String name;

	private final Bitmap texture;

	public Texture(String name, Bitmap texture) {
		super();
		this.name = name;
		this.texture = texture;
	}

	public String getName() {
		return name;
	}

	public Bitmap getTexture() {
		return texture;
	}

}
