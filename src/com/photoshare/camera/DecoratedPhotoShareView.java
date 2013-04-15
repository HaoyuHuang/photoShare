/**
 * 
 */
package com.photoshare.camera;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.photoshare.service.photos.PhotoType;
import com.photoshare.service.photos.factory.PhotoFactory;
import com.photoshare.tabHost.R;
import com.photoshare.utils.Utils;

/**
 * @author Aron
 * 
 */
public class DecoratedPhotoShareView {

	private View baseView;
	private ImageView mPhotoView;
	private EditText mPhotoCaptionView;
	private Bitmap photo;
	private boolean displayCaption = true;
	private int mSharePhotoPreferencesLayoutHolderId;

	/**
	 * @param baseView
	 * @param photo
	 */
	public DecoratedPhotoShareView(View baseView, Bitmap photo) {
		super();
		this.baseView = baseView;
		this.photo = photo;
	}

	public DecoratedPhotoShareView(View baseView, Bitmap photo,
			boolean displayCaption) {
		super();
		this.baseView = baseView;
		this.photo = photo;
		this.displayCaption = displayCaption;
	}

	public void applyView() {
		mPhotoView = (ImageView) baseView
				.findViewById(R.id.decoratingSharePhoto);
		mPhotoCaptionView = (EditText) baseView
				.findViewById(R.id.decoratingSharePhotoCaption);
		if (!displayCaption) {
			mPhotoCaptionView.setVisibility(View.GONE);
		}
		mSharePhotoPreferencesLayoutHolderId = R.id.decoratedPhotoSharingPreferenceListHolder;
		photo = PhotoFactory.createBitmapBySize(photo,
				PhotoType.MIDDLE.getWidth(), PhotoType.MIDDLE.getHeight());
		Drawable drawable = new BitmapDrawable(photo);
		if (photo != null) {
			Utils.logger("Set Decorated Image Drawable");
			mPhotoView.setBackgroundDrawable(drawable);
		}
	}

	public String getCaption() {
		return mPhotoCaptionView.getText().toString();
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
		mPhotoView.setImageBitmap(photo);
	}

	public int getmSharePhotoPreferencesLayoutHolderId() {
		return mSharePhotoPreferencesLayoutHolderId;
	}

}
