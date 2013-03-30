package com.photoshare.service.photos;

import android.view.animation.Animation;

public enum AnimationType {

	FadeIn(1, null);

	private int animationTypeCode;

	private Animation animation;

	private AnimationType(int animationTypeCode, Animation animation) {
		this.animationTypeCode = animationTypeCode;
		this.animation = animation;
	}

	public int getAnimationTypeCode() {
		return animationTypeCode;
	}

	public Animation getAnimation() {
		return animation;
	}

}
