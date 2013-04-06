/**
 * 
 */
package com.photoshare.service.users.views;

import java.util.ArrayList;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.photoshare.command.Command;
import com.photoshare.common.AbstractRequestListener;
import com.photoshare.common.IObserver;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.exception.ValveException;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.FollowHelper;
import com.photoshare.service.follow.FollowType;
import com.photoshare.service.follow.UserFollowRequestParam;
import com.photoshare.service.follow.UserFollowResponseBean;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.RequestPhotoType;
import com.photoshare.service.photos.PhotosGetInfoRequestParam;
import com.photoshare.service.photos.PhotosGetInfoResponseBean;
import com.photoshare.service.photos.views.PopularPhotosView;
import com.photoshare.service.users.UserGetInfoResponseBean;
import com.photoshare.service.users.UserGetOtherInfoRequestParam;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.view.OtherHomeTitleBarView;

/**
 * @author Aron
 * 
 *         查看用户的详细信息
 * 
 */
public class UserHomeFragment extends BaseFragment {

	// ---------- meta data
	private ArrayList<PhotoBean> photos;
	private RequestPhotoType type;
	private UserInfo userInfo;

	// ---------- title bar attributes
	private String rightBtnText = "";
	private int leftBtnVisibility = View.VISIBLE;
	private int rightBtnVisibility = View.GONE;

	// ---------- title bar view
	private OtherHomeTitleBarView homeTitle;

	// ---------- photo view
	private PopularPhotosView popularView;

	public static UserHomeFragment newInstance(int fragmentViewId) {
		UserHomeFragment uh = new UserHomeFragment();
		uh.setFragmentViewId(fragmentViewId);
		return uh;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("UserHomeFragment", "titleBarCreated");
		if (!hideTitleBarView()) {
			container.addView(super.onCreateView(inflater, container,
					savedInstanceState));
		}
		return inflater.inflate(R.layout.user_home_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
	}

	/**
	 * Init the popular Photo View
	 */
	private void initPopularPhotoView() {
//		Tag = getPopularFragment();
		popularView = new PopularPhotosView(getActivity().findViewById(
				R.id.userHomeWrapperId), photos, async, getActivity());
		popularView.registerCallback(photoCallback);
		popularView.apply();
	}

	private void AsyncGetPhotos() throws NetworkException {

		PhotosGetInfoRequestParam param = new PhotosGetInfoRequestParam.PhotoRequestBuilder()
				.Field(PhotosGetInfoRequestParam.FIELD_DEFAULT).Method(type)
				.CurrentPage(0).UserId(userInfo.getUid()).DemandPage(10)
				.build();

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
							initPopularPhotoView();
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

	private String getFeedsItemFragment() {
		return getString(R.string.ffeedsItemFragment);
	}

	private String getPopularFragment() {
		return getString(R.string.fpopularPhotosFragment);
	}

	/**
	 * Init the User Home View
	 */
	private void initViews() {

		Bundle bundle = getArguments();
		if (bundle != null) {
			if (bundle.containsKey(UserInfo.KEY_USER_INFO)) {
				userInfo = bundle.getParcelable(UserInfo.KEY_USER_INFO);
			}
			if (bundle.containsKey(PhotoBean.KEY_PHOTO_TYPE)) {
				type = RequestPhotoType.SWITCH(bundle
						.getString(PhotoBean.KEY_PHOTO_TYPE));
			}
			if (bundle.containsKey(PhotoBean.KEY_PHOTOS)) {
				photos = bundle.getParcelableArrayList(PhotoBean.KEY_PHOTOS);
			}
		}
		type = RequestPhotoType.MyPhotos;
		initVOtherHomeTitleBarViews();
		try {
			AsyncGetOthersInfo();
		} catch (NetworkException e1) {
			e1.printStackTrace();
			AsyncSignIn();
		}

	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelable(UserInfo.KEY_USER_INFO, userInfo);
		}
		super.onSaveInstanceState(outState);
	}

	private String getHomeText() {
		return getString(R.string.home);
	}

	private String getBackText() {
		return getString(R.string.back);
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	/**
	 * Init the other home title bar view
	 */
	private void initVOtherHomeTitleBarViews() {
		initTitleBar(getBackText(), rightBtnText, getHomeText(),
				leftBtnVisibility, rightBtnVisibility);
		homeTitle = new OtherHomeTitleBarView(getActivity().findViewById(
				R.id.userHomeWrapperId), userInfo, async);
		homeTitle.registerCallback(TitleBarCallBack);
	}

	private void OnFollowClicked() {
		Bundle param = new Bundle();
		param.putParcelable(UserInfo.KEY_USER_INFO, userInfo);
		param.putString(UserInfo.KEY_FOLLOW_TYPE,
				FollowType.FOLLOWER.toString());
		forward(getFollowFragment(), param);
	}

	private void OnFollowingClicked() {
		Bundle param = new Bundle();
		param.putParcelable(UserInfo.KEY_USER_INFO, userInfo);
		param.putString(UserInfo.KEY_FOLLOW_TYPE,
				FollowType.FOLLOWER.toString());
		forward(getFollowFragment(), param);
	}

	private String getFollowFragment() {
		return getString(R.string.ffollowInfoFragment);
	}

	private void OnLikeCntClicked() {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Do nothing
	 */
	private void OnUserNameClicked() {

	}

	/**
	 * Initiate Uri and parameters
	 */
	private void OnUserWebsiteClicked(UserInfo info) {
		String url = info.getWebsite();
		if (url.startsWith("www")) {
			url = "http://" + url;
		}
		Uri uri = Uri.parse(url);
		Intent i = new Intent(Intent.ACTION_VIEW, uri);
		i.putExtra(Browser.EXTRA_APPLICATION_ID, getActivity().getPackageName());
		startActivity(i);
	}

	/**
	 * 
	 * 
	 * @throws NetworkException
	 */
	private void AsyncOnFollowClick() throws NetworkException {
		UserFollowRequestParam param = new UserFollowRequestParam.FollowBuilder()
				.FollowId(userInfo.getUid())
				.UserId(user.getUserInfo().getUid())
				.isFollowing(userInfo.isFollowing()).build();
		FollowHelper.ICallback mCallback = new FollowHelper.ICallback() {

			public void OnNetworkError(final NetworkError error) {

				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mExceptionHandler.obtainMessage(
									NetworkError.ERROR_FOLLOW).sendToTarget();
						}
					});
				}
			}

			public void OnFault(final Throwable fault) {

				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mExceptionHandler.obtainMessage(
									NetworkError.ERROR_NETWORK).sendToTarget();
						}
					});
				}
			}

			public void OnComplete(final UserFollowResponseBean bean) {

				final boolean isChecked = checkFollowingResponseBean(bean);
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							if (isChecked) {

							}
						}
					});
				}
			}
		};
		try {
			async.publishFollow(param, mCallback);
		} catch (ValveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * @throws NetworkException
	 */
	private void AsyncGetOthersInfo() throws NetworkException {
		UserGetOtherInfoRequestParam param = new UserGetOtherInfoRequestParam(
				user.getUserInfo().getUid(), userInfo.getUid());
		AbstractRequestListener<UserGetInfoResponseBean> listener = new AbstractRequestListener<UserGetInfoResponseBean>() {

			@Override
			public void onNetworkError(NetworkError networkError) {
				try {
					AsyncGetPhotos();
				} catch (NetworkException e) {
					AsyncSignIn();
					e.printStackTrace();
				}
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mExceptionHandler.obtainMessage(
									NetworkError.ERROR_REFRESH_DATA)
									.sendToTarget();
						}

					});
				}
			}

			@Override
			public void onFault(Throwable fault) {
				try {
					AsyncGetPhotos();
				} catch (NetworkException e) {
					AsyncSignIn();
					e.printStackTrace();
				}
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mExceptionHandler.obtainMessage(
									NetworkError.ERROR_NETWORK).sendToTarget();
						}

					});
				}

			}

			@Override
			public void onComplete(final UserGetInfoResponseBean bean) {
				try {
					AsyncGetPhotos();
				} catch (NetworkException e) {
					AsyncSignIn();
					e.printStackTrace();
				}
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							if (homeTitle != null && bean.getUserInfo() != null) {
								homeTitle.applyData(bean.getUserInfo());
							}
						}

					});
				}

			}
		};
		async.getOthersInfo(param, listener);
	}

	/**
	 * Check the response form corresponds to the user follow action.
	 * 
	 * @param bean
	 * @return
	 */
	private boolean checkFollowingResponseBean(UserFollowResponseBean bean) {
		if (bean == null || bean.getUserId() != user.getUserInfo().getUid()
				|| bean.getFollowId() != userInfo.getUid())
			return false;
		if (bean.isFollowing()) {
			user.getUserInfo().setFollowingCnt(
					user.getUserInfo().getFollowingCnt() + 1);
		} else {
			user.getUserInfo().setFollowingCnt(
					user.getUserInfo().getFollowingCnt() - 1);
		}
		return true;
	}

	/**
	 * Popular Photo Callback
	 */
	private PopularPhotosView.ICallback photoCallback = new PopularPhotosView.ICallback() {

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

	/**
	 * The Other Home Title Bar Callback
	 */
	private OtherHomeTitleBarView.ICallback TitleBarCallBack = new OtherHomeTitleBarView.ICallback() {

		public void OnWebsiteClick(UserInfo info) {
			OnUserWebsiteClicked(info);
		}

		public void OnNameClick(UserInfo info) {
			OnUserNameClicked();
		}

		public void OnLikesCntClick(UserInfo info) {
			OnLikeCntClicked();
		}

		public void OnFollowingCntClick(UserInfo info) {
			OnFollowingClicked();
		}

		public void OnFollowerCntClick(UserInfo info) {
			OnFollowClicked();
		}

		public void OnFollowClick(UserInfo info, IObserver<Boolean> observer) {
			try {
				AsyncOnFollowClick();
			} catch (NetworkException e) {
				AsyncSignIn();
			}
		}

		public void OnUserHeadLoaded(final ImageView imageView,
				final Drawable photo, String url) {
			if (getActivity() != null) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						imageView.setImageDrawable(photo);
					}
				});
			}
		}

		public void OnDefault(final ImageView imageView) {
			if (getActivity() != null) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						imageView.setImageResource(R.drawable.icon);
					}
				});
			}
		}
	};

	private String getFollowingFragment() {
		return getString(R.string.ffollowInfoFragment);
	}

	private String getProfileFragment() {
		return getString(R.string.fpersonalProfileFragment);
	}

	private String getPreferencesSettingsFragment() {
		return getString(R.string.fpreferenceSettingsFragment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void onRightBtnClicked() {

	}

	private String getPreferenceSettingsFragment() {
		return getString(R.string.fpreferenceSettingsFragment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnLeftBtnClicked()
	 */
	@Override
	protected void onLeftBtnClicked() {
		Command.TabHost(getActivity());
	}

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub

	}

}
