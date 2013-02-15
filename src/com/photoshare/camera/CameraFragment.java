/**
 * 
 */
package com.photoshare.camera;

import java.io.File;
import java.io.IOException;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.photos.EditPhotoType;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.tabHost.R;
import com.photoshare.utils.Utils;
import com.photoshare.utils.async.AsyncImageLoader;

/**
 * @author Aron
 * 
 *         The Camera Fragment starts the Camera and holds the image View for
 *         subsequent operations.
 * 
 */
public class CameraFragment extends BaseFragment {

	private CameraView mCameraView;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";
	private Bitmap photo;

	public static final String PHOTO_DATA = "data";
	public static final String PHOTO_URL = "photoUrl";

	public static CameraFragment newInstance(int fragmentViewId) {
		CameraFragment cf = new CameraFragment();
		cf.setFragmentViewId(fragmentViewId);
		return cf;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (!processArguments()) {
			container.addView(super.onCreateView(inflater, container,
					savedInstanceState));
		}
		return inflater.inflate(R.layout.decorate_photo_layout, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		initView();
		if (isIntentAvailable(getActivity(), MediaStore.ACTION_IMAGE_CAPTURE)) {
			dispatchTakePictureIntent(ACTION_TAKE_PHOTO_S);
		}
	}

	private void initViews() {
		leftBtnText = getRetakeText();
		rightBtnText = getCropText();
		titlebarText = getCameraText();
		if (mCameraView == null) {
			throw new IllegalStateException(
					"CameraView has not been initialized!");
		}
		initTitleBar(leftBtnText, rightBtnText, titlebarText);
		mCameraView.setContext(getActivity());
		mCameraView.setBaseView(getActivity().findViewById(
				R.id.editPhotoLayoutId));
		mCameraView.registerCameraClickListener(listener);
		mCameraView.applyView();
	}

	private void initView() {
		Tag = getCameraFragment();
		mCameraView = new CameraView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void OnRightBtnClicked() {
		Bundle param = new Bundle();
		param.putParcelable(PhotoBean.KEY_PHOTO, photo);
		forward(getDecoratedPhotoFragment(), param);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnLeftBtnClicked()
	 */
	@Override
	protected void OnLeftBtnClicked() {
		dispatchTakePictureIntent(ACTION_TAKE_PHOTO_S);
	}

	public Bitmap getPhoto() {
		return photo;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}

	private String getRetakeText() {
		return getString(R.string.retake);
	}

	private String getCropText() {
		return getString(R.string.crop);
	}

	private String getCameraText() {
		return getString(R.string.camera);
	}

	private String getCameraFragment() {
		return getString(R.string.fcameraPhotoFragment);
	}

	private String getDecoratedPhotoFragment() {
		return getString(R.string.fdecoratedPhotoFragment);
	}

	private String getDecoratedPhotoShareFragment() {
		return getString(R.string.fdecoratedPhotoShareFragment);
	}

	/** The callback is used to asynchronized decorating image */
	private AsyncImageLoader.ImageCallback mCallback = new AsyncImageLoader.ImageCallback() {

		public void imageLoaded(final Drawable imageDrawable, String imageUrl) {
			getActivity().runOnUiThread(new Runnable() {

				public void run() {
					mCameraView.setDecoratedPhoto(imageDrawable);
				}
			});
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * com.photoshare.utils.async.AsyncImageLoader.ImageCallback#imageDefault
		 * ()
		 * 
		 * Do nothing, just preserve the original one.
		 */
		public void imageDefault() {
			getActivity().runOnUiThread(new Runnable() {

				public void run() {

				}
			});
		}
	};

	private static final int ACTION_TAKE_PHOTO_B = 1;
	private static final int ACTION_TAKE_PHOTO_S = 2;

	private static final String BITMAP_STORAGE_KEY = "viewbitmap";
	private static final String IMAGEVIEW_VISIBILITY_STORAGE_KEY = "imageviewvisibility";

	private void dispatchTakePictureIntent(int actionCode) {
		Utils.logger("Take Picture " + actionCode);
		Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		switch (actionCode) {
		case ACTION_TAKE_PHOTO_B:
			File f = null;
			try {
				f = mCameraView.setUpPhotoFile();
				mCameraView.setmCurrentPhotoPath(f.getAbsolutePath());
				takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT,
						Uri.fromFile(f));
			} catch (IOException e) {
				e.printStackTrace();
				f = null;
				mCameraView.setmCurrentPhotoPath(null);
			}
			break;
		default:
			break;
		}
		startActivityForResult(takePictureIntent, actionCode);
	}

	private void handleSmallCameraPhoto(Intent data) {
		// Toast.makeText(getActivity(), "handle picture", Toast.LENGTH_LONG)
		// .show();
		if (data != null) {
			Uri uri = data.getData();
			if (uri != null) {
				photo = BitmapFactory.decodeFile(uri.getPath());
			}
			if (photo == null) {
				Bundle bundle = data.getExtras();
				if (bundle != null) {
					photo = (Bitmap) bundle.get("data");
					mCameraView.setPhoto(photo);
				} else {
					return;
				}
			}
		}
		if (photo != null) {
			mCameraView.setPhoto(photo);
		}
	}

	private void handleBigCameraPhoto() {
		// Toast.makeText(getActivity(), "Take Picture",
		// Toast.LENGTH_LONG).show();
		if (!mCameraView.isCurrentPhotoPathNull()) {
			Toast.makeText(getActivity(), "Take Picture", Toast.LENGTH_LONG)
					.show();
			mCameraView.setPic();
			mCameraView.galleryAddPic();
			mCameraView.setmCurrentPhotoPath(null);
		}
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Utils.logger("Take Picture Done");
		// Toast.makeText(getActivity(), "Take Picture",
		// Toast.LENGTH_LONG).show();
		initTitleBar(leftBtnText, rightBtnText, titlebarText);
		initViews();
		switch (requestCode) {
		case ACTION_TAKE_PHOTO_B: {
			if (resultCode == Activity.RESULT_OK) {
				handleBigCameraPhoto();
			}
			break;
		} // ACTION_TAKE_PHOTO_B
		case ACTION_TAKE_PHOTO_S: {
			if (resultCode == Activity.RESULT_OK) {
				// Toast.makeText(getActivity(), "Take Picture",
				// Toast.LENGTH_LONG)
				// .show();
				handleSmallCameraPhoto(data);
			}
			break;
		} // ACTION_TAKE_PHOTO_S
		}
	}

	// Some lifecycle callbacks so that the image can survive orientation change
	@Override
	public void onSaveInstanceState(Bundle outState) {
		outState.putParcelable(BITMAP_STORAGE_KEY, mCameraView.getPhoto());
		outState.putBoolean(IMAGEVIEW_VISIBILITY_STORAGE_KEY,
				(mCameraView.getPhoto() != null));
		super.onSaveInstanceState(outState);
	}

	/**
	 * Indicates whether the specified action can be used as an intent. This
	 * method queries the package manager for installed packages that can
	 * respond to an intent with the specified action. If no suitable package is
	 * found, this method returns false.
	 * http://android-developers.blogspot.com/2009/01/can-i-use-this-intent.html
	 * 
	 * @param context
	 *            The application's environment.
	 * @param action
	 *            The Intent action to check for availability.
	 * 
	 * @return True if an Intent with the specified action can be sent and
	 *         responded to, false otherwise.
	 */
	public static boolean isIntentAvailable(Context context, String action) {
		final PackageManager packageManager = context.getPackageManager();
		final Intent intent = new Intent(action);
		List<ResolveInfo> list = packageManager.queryIntentActivities(intent,
				PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}

	private CameraView.OnCameraViewClick listener = new CameraView.OnCameraViewClick() {

		public void OnToolsClick(EditPhotoType type, Bitmap map) {
			async.decorateImage(type, map, mCallback);
		}

		public void OnSubmitClick() {
			Bundle param = new Bundle();
			param.putParcelable(PhotoBean.KEY_PHOTO, photo);
			forward(getDecoratedPhotoShareFragment(), param);
		}

		public void OnCancleClick() {
			if (!photo.isRecycled()) {
				photo.recycle();
				photo = null;
			}
			dispatchTakePictureIntent(ACTION_TAKE_PHOTO_B);
		}
	};

}
