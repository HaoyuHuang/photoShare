package com.photoshare.service.photos.factory;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

public class SimpleDisplayer implements Displayer {

	public void loadCompletedisplay(ImageView imageView, Bitmap bitmap,
			BitmapDisplayConfig config) {
		// TODO Auto-generated method stub

	}

	public void loadFailDisplay(ImageView imageView, Bitmap bitmap) {
		// TODO Auto-generated method stub

	}

	private void fadeInDisplay(ImageView imageView, Bitmap bitmap) {
		final TransitionDrawable td = new TransitionDrawable(new Drawable[] {
				new ColorDrawable(android.R.color.transparent),
				new BitmapDrawable(imageView.getResources(), bitmap) });
		imageView.setImageDrawable(td);
		td.startTransition(300);
	}

	private void animationDisplay(ImageView imageView, Bitmap bitmap,
			Animation animation) {
		animation.setStartTime(AnimationUtils.currentAnimationTimeMillis());
		imageView.setImageBitmap(bitmap);
		imageView.startAnimation(animation);
	}

}
