/**
 * 
 */
package com.photoshare.camera;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.photos.DecoratePhotoType;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.Texture;
import com.photoshare.service.photos.factory.DecoratePhotoWrapper;
import com.photoshare.service.photos.factory.PhotoFactory;
import com.photoshare.tabHost.R;
import com.photoshare.utils.QuartzUtils;
import com.photoshare.utils.Utils;
import com.photoshare.utils.async.AsyncImageLoader;

/**
 * @author Aron
 * 
 *         The Camera Fragment starts the Camera and holds the image View for
 *         subsequent operations.
 * 
 */
public class PhotoFilterFragment extends BaseFragment {

	private PhotoFilterView mPhotoFilterView;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";
	private Bitmap photo;
	private PhotoBean photoBean;

	public static PhotoFilterFragment newInstance(int fragmentViewId) {
		PhotoFilterFragment cf = new PhotoFilterFragment();
		cf.setFragmentViewId(fragmentViewId);
		return cf;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (!hideTitleBarView()) {
			container.addView(super.onCreateView(inflater, container,
					savedInstanceState));
		}
		return inflater.inflate(R.layout.decorate_photo_layout, container,
				false);
	}

	@Override
	public void onDestroy() {
		if (mPhotoFilterView != null) {
			Utils.logger("Photo Filter View Destroyed");
			mPhotoFilterView.destroy();
		}
		super.onDestroy();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		if (hideTitleBarView()) {
			initTitleBar(leftBtnText, rightBtnText, titlebarText);
			initView();
			initViews();
			Bundle args = getArguments();
			if (args.containsKey(PhotoBean.KEY_SRC_PHOTO)) {
				photo = args.getParcelable(PhotoBean.KEY_SRC_PHOTO);
				if (photo != null) {
					mPhotoFilterView.setPhoto(photo);
				}
			}
			if (args.containsKey(PhotoBean.KEY_PHOTO)) {
				photoBean = args.getParcelable(PhotoBean.KEY_PHOTO);
			}
		} else {
			initView();
		}
	}

	private void initViews() {
		leftBtnText = getBackText();
		rightBtnText = getSaveText();
		titlebarText = getDecorateText();
		if (mPhotoFilterView == null) {
			throw new IllegalStateException(
					"Photo Filter View has not been initialized!");
		}
		initTitleBar(leftBtnText, rightBtnText, titlebarText);
		mPhotoFilterView.setContext(getActivity());
		mPhotoFilterView.setBaseView(getActivity().findViewById(
				R.id.editPhotoLayoutId));
		mPhotoFilterView
				.registerPhotoFilterViewClickListener(filterViewClickListener);
		mPhotoFilterView.applyView();
	}

	private void initView() {
		Tag = getCameraFragment();
		mPhotoFilterView = new PhotoFilterView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void onRightBtnClicked(View view) {
		savePhoto();
		backward(null);
	}

	private void savePhoto() {
		PhotoFactory.savePhototoImageStore(getActivity(), photo, Utils.APP_NAME
				+ "-" + QuartzUtils.formattedNow(), photoBean.getCaption());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnLeftBtnClicked()
	 */
	@Override
	protected void onLeftBtnClicked(View view) {
		backward(null);
	}

	public Bitmap getPhoto() {
		return photo;
	}

	public void setPhoto(Bitmap photo) {
		this.photo = photo;
	}

	private String getBackText() {
		return getString(R.string.back);
	}

	private String getSaveText() {
		return getString(R.string.save);
	}

	private String getDecorateText() {
		return getString(R.string.decorate);
	}

	private String getCameraFragment() {
		return getString(R.string.fcameraPhotoFragment);
	}

	private String getDecoratedPhotoUploadFragment() {
		return getString(R.string.fdecoratedPhotoUploadFragment);
	}

	private String getMarbleTexture() {
		return getString(R.string.textureMarble);
	}

	/** The callback is used to asynchronized decorating image */
	private AsyncImageLoader.ImageCallback mCallback = new AsyncImageLoader.ImageCallback() {

		public void imageLoaded(final Drawable imageDrawable, String imageUrl) {
			if (getActivity() != null) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						mPhotoFilterView.setDecoratedPhoto(imageDrawable);
					}
				});
			}
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
			if (getActivity() != null) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {

					}
				});
			}
		}
	};

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	private PhotoFilterView.PhotoFilterViewClickListener filterViewClickListener = new PhotoFilterView.PhotoFilterViewClickListener() {

		private Texture texture;

		public void OnToolsClick(DecoratePhotoType type, Bitmap map) {
			DecoratePhotoWrapper wrapper = null;
			if (DecoratePhotoType.HALFTONE.equals(type)) {
				if (texture == null) {
					texture = new Texture("marble",
							PhotoFactory.createScaledBitmap(PhotoFactory
									.readBitMap(getActivity(),
											R.drawable.marble), map.getWidth(),
									map.getHeight()), map.getWidth(),
							map.getHeight());
				}
				wrapper = new DecoratePhotoWrapper(map, texture, type);
			} else {
				wrapper = new DecoratePhotoWrapper(map, type);
			}

			async.decorateImage(wrapper, mCallback);
		}

		public void OnSubmitClick() {
			savePhoto();
		}

		public void OnCancleClick() {
			if (!photo.isRecycled()) {
				photo.recycle();
				photo = null;
			}
		}
	};

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub

	}

}
