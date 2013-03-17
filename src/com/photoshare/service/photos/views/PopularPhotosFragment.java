/**
 * 
 */
package com.photoshare.service.photos.views;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.PhotoType;
import com.photoshare.service.photos.PhotosGetInfoRequestParam;
import com.photoshare.service.photos.PhotosGetInfoResponseBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.Utils;

/**
 * @author Aron
 * 
 */
public class PopularPhotosFragment extends BaseFragment {

	private PopularPhotosView popularView;
	private UserInfo userInfo;
	private ArrayList<PhotoBean> photos;
	private PhotoType type;
	private int leftBtnVisibility = View.INVISIBLE;
	private int rightBtnVisibility = View.INVISIBLE;
	private String titleBarText;

	private int demandPage;
	private int currentPage;

	public static PopularPhotosFragment newInstance(int fragmentViewId) {
		PopularPhotosFragment pp = new PopularPhotosFragment();
		pp.setFragmentViewId(fragmentViewId);
		return pp;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public PhotoType getType() {
		return type;
	}

	public ArrayList<PhotoBean> getPhotos() {
		return photos;
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public void setType(PhotoType type) {
		this.type = type;
	}

	public void setPhotos(ArrayList<PhotoBean> photos) {
		this.photos = photos;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(PhotoBean.KEY_PHOTOS)) {
				photos = savedInstanceState
						.getParcelableArrayList(PhotoBean.KEY_PHOTOS);
			}
			if (savedInstanceState.containsKey(PhotoBean.KEY_PHOTO_TYPE)) {
				type = PhotoType.SWITCH(savedInstanceState
						.getString(PhotoBean.KEY_PHOTO_TYPE));
			}
			if (savedInstanceState.containsKey(UserInfo.KEY_USER_INFO)) {
				userInfo = savedInstanceState
						.getParcelable(UserInfo.KEY_USER_INFO);
			}
		}
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			if (bundle.containsKey(UserInfo.KEY_USER_INFO)) {
				userInfo = bundle.getParcelable(UserInfo.KEY_USER_INFO);
			}
			if (bundle.containsKey(PhotoBean.KEY_PHOTO_TYPE)) {
				type = PhotoType.SWITCH(bundle
						.getString(PhotoBean.KEY_PHOTO_TYPE));
			}
			if (bundle.containsKey(PhotoBean.KEY_PHOTOS)) {
				photos = bundle.getParcelableArrayList(PhotoBean.KEY_PHOTOS);
			}
		}
		try {
			AsyncGetPhotos();
		} catch (NetworkException e) {
			AsyncSignIn();
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelableArrayList(PhotoBean.KEY_PHOTOS, photos);
			if (photos == null) {
				Utils.logger("Photos is Null");
			}
			outState.putString(PhotoBean.KEY_PHOTO_TYPE, type.toString());
			outState.putParcelable(UserInfo.KEY_USER_INFO, userInfo);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onResume() {
		super.onResume();
		Bundle bundle = getArguments();
		if (bundle != null) {
			if (bundle.containsKey(UserInfo.KEY_USER_INFO)) {
				userInfo = bundle.getParcelable(UserInfo.KEY_USER_INFO);
			}
			if (bundle.containsKey(PhotoBean.KEY_PHOTO_TYPE)) {
				type = PhotoType.SWITCH(bundle
						.getString(PhotoBean.KEY_PHOTO_TYPE));
			}
			if (bundle.containsKey(PhotoBean.KEY_PHOTOS)) {
				photos = bundle.getParcelableArrayList(PhotoBean.KEY_PHOTOS);
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (!processArguments()) {
			container.addView(super.onCreateView(inflater, container,
					savedInstanceState));
			titleBarText = getPopularText();
			initTitleBar("", "", titleBarText, leftBtnVisibility,
					rightBtnVisibility);
		}
		return inflater.inflate(R.layout.popular_layout, container, false);
	}

	/**
	 * 
	 */
	private void initView() {
		Tag = getPopularFragment();
		popularView = new PopularPhotosView(getActivity().findViewById(
				R.id.popularId), photos, async, getActivity());
		popularView.registerCallback(mCallback);
		popularView.apply();
	}

	private void AsyncGetPhotos() throws NetworkException {

		PhotosGetInfoRequestParam param = new PhotosGetInfoRequestParam.PhotoRequestBuilder()
				.Field(PhotosGetInfoRequestParam.FIELD_DEFAULT).Method(type)
				.CurrentPage(currentPage).UserId(userInfo.getUid())
				.DemandPage(demandPage).build();

		AbstractRequestListener<PhotosGetInfoResponseBean> listener = new AbstractRequestListener<PhotosGetInfoResponseBean>() {

			@Override
			public void onComplete(final PhotosGetInfoResponseBean bean) {
				if (bean != null) {
					photos = bean.getPhotos();
				}
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							Log.i("receivePopularPhotos", "getPhotos");
							initView();
						}

					});
				}
			}

			@Override
			public void onFault(final Throwable fault) {
				mExceptionHandler.obtainMessage(NetworkError.ERROR_NETWORK)
						.sendToTarget();
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {

						}

					});
				}
			}

			@Override
			public void onNetworkError(final NetworkError networkError) {
				mExceptionHandler
						.obtainMessage(NetworkError.ERROR_REFRESH_DATA)
						.sendToTarget();
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {

						}

					});
				}
			}

		};
		async.getPhotosInfo(param, listener);
	}

	private PopularPhotosView.ICallback mCallback = new PopularPhotosView.ICallback() {

		public void OnImageClick(PhotoBean photo) {
			Bundle params = (Bundle) getArguments().clone();
			params.putParcelable(PhotoBean.KEY_PHOTO, photo);
			forward(getFeedsItemFragment(), params);
		}

		public void OnImageLoaded(final ImageView image,
				final Drawable drawable, final String url) {
			if (getActivity() != null) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						Log.i("displayImage", url);
						image.setImageDrawable(drawable);
					}
				});
			}
		}

		public void OnImageDefaule(final ImageView image) {
			if (getActivity() != null) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						Log.i("displayImage", "default");
						image.setImageResource(R.drawable.icon);
					}
				});
			}
		}
	};

	private String getFeedsItemFragment() {
		return getString(R.string.ffeedsItemFragment);
	}

	private String getPopularFragment() {
		return getString(R.string.fpopularPhotosFragment);
	}

	private String getPopularText() {
		return getString(R.string.popular);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void onRightBtnClicked() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnLeftBtnClicked()
	 */
	@Override
	protected void onLeftBtnClicked() {

	}

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub

	}

}
