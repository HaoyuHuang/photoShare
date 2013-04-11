/**
 * 
 */
package com.photoshare.camera;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.photoshare.tabHost.R;

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
		if (photo != null) {
			mPhotoView.setImageBitmap(photo);
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
