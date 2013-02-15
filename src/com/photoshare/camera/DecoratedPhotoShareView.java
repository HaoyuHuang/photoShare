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
	private int mSharePhotoPreferencesLayoutHolderId;

	/**
	 * @param baseView
	 */
	public DecoratedPhotoShareView(View baseView) {
		super();
		this.baseView = baseView;
	}

	public void applyView() {
		mPhotoView = (ImageView) baseView
				.findViewById(R.id.decoratingSharePhoto);
		mPhotoCaptionView = (EditText) baseView
				.findViewById(R.id.decoratingSharePhotoCaption);
		mSharePhotoPreferencesLayoutHolderId = R.id.decoratedPhotoSharingPreferenceListHolder;
		mPhotoView.setImageBitmap(photo);
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
