package com.photoshare.service.photos.factory;

import com.photoshare.service.photos.AnimationType;
import com.photoshare.service.photos.PhotoType;

public class BitmapDisplayConfig {

	public static final BitmapDisplayConfig SMALL = new BitmapDisplayConfig(
			PhotoType.SMALL);

	public static final BitmapDisplayConfig MIDDLE = new BitmapDisplayConfig(
			PhotoType.MIDDLE);

	public static final BitmapDisplayConfig LARGE = new BitmapDisplayConfig(
			PhotoType.LARGE);

	private PhotoType photoType;

	private AnimationType animationType;
	
	public BitmapDisplayConfig() {
		
	}
	
	public BitmapDisplayConfig(PhotoType photoType) {
		super();
		this.photoType = photoType;
		this.animationType = AnimationType.FadeIn;
	}

	public BitmapDisplayConfig(PhotoType photoType, AnimationType animationType) {
		super();
		this.photoType = photoType;
		this.animationType = animationType;
	}

	public PhotoType getPhotoType() {
		return photoType;
	}

	public void setPhotoType(PhotoType photoType) {
		this.photoType = photoType;
	}

	public AnimationType getAnimationType() {
		return animationType;
	}

	public void setAnimationType(AnimationType animationType) {
		this.animationType = animationType;
	}

}
