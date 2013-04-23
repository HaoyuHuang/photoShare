package com.photoshare.utils.async;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

public interface AsyncImageCallback {
	public void imageLoaded(Drawable imageDrawable, String imageUrl);

	public void imageDefault();
	
	public void imageLoaded(Bitmap image, String imageUrl);
}
