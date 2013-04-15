package com.photoshare.service.photos.factory;

import android.graphics.Bitmap;

import com.photoshare.service.photos.DecoratePhotoType;
import com.photoshare.service.photos.Texture;

public final class DecoratePhotoWrapper {
	private final Bitmap raw;

	private final Texture texture;

	private final DecoratePhotoType type;

	public DecoratePhotoWrapper(Bitmap raw, Texture texture,
			DecoratePhotoType type) {
		super();
		this.raw = raw;
		this.texture = texture;
		this.type = type;
	}

	public DecoratePhotoWrapper(Bitmap raw, DecoratePhotoType type) {
		super();
		this.raw = raw;
		this.texture = null;
		this.type = type;
	}

	public Bitmap getRenderedPhoto() {
		return type.Decorate(this);
	}

	public Bitmap getRaw() {
		return raw;
	}

	public Texture getTexture() {
		return texture;
	}

}
