/**
 * 
 */
package com.photoshare.service.photos.views;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.LikeHelper;
import com.photoshare.service.likes.PhotoLikeRequestParam;
import com.photoshare.service.likes.PhotoLikeResponseBean;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.PhotoType;
import com.photoshare.service.photos.PhotosGetInfoRequestParam;
import com.photoshare.service.photos.PhotosGetInfoResponseBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.Utils;
import com.photoshare.view.NotificationDisplayer;

/**
 * @author Aron
 * 
 */
public class FeedsFragment extends BaseFragment {

	private FeedsView feedsView;
	private UserInfo userInfo;
	private int currentPage = 0;
	private int demandPage = 10;
	private PhotoType type;
	private ArrayList<PhotoBean> photos;
	private NotificationDisplayer mNotificationDisplayer;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";
	private int leftBtnVisibility = View.INVISIBLE;
	private int rightBtnVisibility = View.INVISIBLE;

	public static FeedsFragment newInstance(int fragmentViewId) {
		FeedsFragment feeds = new FeedsFragment();
		feeds.setFragmentViewId(fragmentViewId);
		return feeds;
	}

	public PhotoType getType() {
		return type;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {

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
		Tag = getFeedsFragment();
		titlebarText = getFeedsText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		if (photos != null && photos.size() != 0) {
			initFeeds();
		} else {
			try {
				AsyncGetFeeds();
			} catch (NetworkException e) {
				AsyncSignIn();
			}
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (!processArguments()) {
			container.addView(super.onCreateView(inflater, container,
					savedInstanceState));
		}
		return inflater.inflate(R.layout.feeds_layout, container, false);
	}

	private String getLikeTag() {
		return getString(R.string.nLikeTag);
	}

	private String getLikeTicker() {
		return getString(R.string.nLikeTicker);
	}

	private String getFeedsText() {
		return getString(R.string.feeds);
	}

	private String getFeedsFragment() {
		return getString(R.string.ffeedsFragment);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelableArrayList(PhotoBean.KEY_PHOTOS, photos);
		}
		super.onSaveInstanceState(outState);
	}

	private void AsyncLikePhoto(PhotoBean photo) throws NetworkException {
		PhotoLikeRequestParam param = new PhotoLikeRequestParam.LikeBuilder()
				.UserId(user.getUserInfo().getUid()).PhotoId(photo.getPid())
				.isLike(photo.isLike()).build();
//		mNotificationDisplayer.displayNotification();

		LikeHelper.ICallback mCallback = new LikeHelper.ICallback() {

			public void OnNetworkError(NetworkError error) {

				getActivity().runOnUiThread(new Runnable() {

					public void run() {

					}

				});
			}

			public void OnFault(Throwable fault) {
				mExceptionHandler.obtainMessage(NetworkError.ERROR_NETWORK)
						.sendToTarget();
				getActivity().runOnUiThread(new Runnable() {

					public void run() {

					}

				});
			}

			public void OnComplete(PhotoLikeResponseBean bean) {
				mNotificationDisplayer.setTag(getSuccessTag());
				mNotificationDisplayer.setTicker(getSuccessTicker());
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						mNotificationDisplayer.displayNotification();
						mNotificationDisplayer.cancleNotification();
					}

				});
			}
		};
		async.publishLikePhoto(param, mCallback);
		mNotificationDisplayer.cancleNotification();
	}

	private void AsyncGetFeeds() throws NetworkException {

		PhotosGetInfoRequestParam param = new PhotosGetInfoRequestParam.PhotoRequestBuilder()
				.CurrentPage(currentPage).DemandPage(demandPage)
				.UserId(userInfo.getUid()).Method(type)
				.Field(PhotosGetInfoRequestParam.FIELDS_ALL).build();
		AbstractRequestListener<PhotosGetInfoResponseBean> listener = new AbstractRequestListener<PhotosGetInfoResponseBean>() {

			@Override
			public void onNetworkError(NetworkError networkError) {
				mExceptionHandler
						.obtainMessage(NetworkError.ERROR_REFRESH_DATA)
						.sendToTarget();
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						if (feedsView != null)
							feedsView.onRefreshComplete();
					}

				});
			}

			@Override
			public void onFault(Throwable fault) {
				mExceptionHandler.obtainMessage(NetworkError.ERROR_NETWORK)
						.sendToTarget();
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						if (feedsView != null)
							feedsView.onRefreshComplete();
					}

				});
			}

			@Override
			public void onComplete(PhotosGetInfoResponseBean bean) {
				if (bean != null) {
					photos = bean.getPhotos();
					currentPage += demandPage;
				}
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						initFeeds();
						if (feedsView != null)
							feedsView.onRefreshComplete();
					}

				});
			}
		};
		async.getPhotosInfo(param, listener);
	}

	private void initFeeds() {
		feedsView = new FeedsView(getActivity(), (View) getActivity()
				.findViewById(R.id.feedsLayoutId), photos, async);
		feedsView.registerCallback(mFeedsActionListener);
		feedsView.applyView();
		mNotificationDisplayer = new NotificationDisplayer.NotificationBuilder()
				.Context(getActivity()).Tag(getLikeTag())
				.Ticker(getLikeTicker()).build();
	}

	private FeedsView.OnFeedsActionListener mFeedsActionListener = new FeedsView.OnFeedsActionListener() {

		public void OnRefresh() {
			try {
				AsyncGetFeeds();
			} catch (NetworkException e) {
				AsyncSignIn();
			}
		}

		public void OnNameClick(UserInfo info) {
			Bundle args = new Bundle();
			args.putParcelableArrayList(PhotoBean.KEY_PHOTOS, photos);
			args.putParcelable(UserInfo.KEY_USER_INFO, info);
			forward(getUserHomeFragment(), args);
		}

		public void OnLikeClick(PhotoBean photo) {
			try {
				Utils.logger("OnLikeClicked");
				AsyncLikePhoto(photo);
			} catch (NetworkException e) {
				AsyncSignIn();
			}
		}

		public void OnCommentListClick(PhotoBean photo) {
			Bundle args = new Bundle();
			args.putParcelableArrayList(PhotoBean.KEY_PHOTOS, photos);
			args.putParcelable(PhotoBean.KEY_PHOTO, photo);
			forward(getCommentsFragment(), args);
		}

		public void OnLikeListClick(PhotoBean photo) {
			Bundle args = new Bundle();
			args.putParcelableArrayList(PhotoBean.KEY_PHOTOS, photos);
			args.putParcelable(PhotoBean.KEY_PHOTO, photo);
			forward(getLikeFragment(), args);
		}

		public void OnUserHeadLoaded(final ImageView image,
				final Drawable drawable, String url) {
			getActivity().runOnUiThread(new Runnable() {

				public void run() {
					image.setImageDrawable(drawable);
				}
			});
		}

		public void OnFeedPhotoLoaded(final ImageView image,
				final Drawable drawable, String url) {
			getActivity().runOnUiThread(new Runnable() {

				public void run() {
					image.setImageDrawable(drawable);
				}
			});
		}

		public void OnUserHeadDefault(final ImageView image) {
			getActivity().runOnUiThread(new Runnable() {

				public void run() {
					image.setImageResource(R.drawable.icon);
				}
			});
		}

		public void OnFeedPhotoDefault(final ImageView image) {
			getActivity().runOnUiThread(new Runnable() {

				public void run() {
					image.setImageResource(R.drawable.icon);
				}
			});

		}
	};

	public void setPhotos(ArrayList<PhotoBean> photos) {
		this.photos = photos;
	}

	public void addPhotoBean(PhotoBean photo) {
		feedsView.addPhotoBean(photo);
	}

	public void setUserInfo(UserInfo userInfo) {
		this.userInfo = userInfo;
	}

	public void setType(PhotoType type) {
		this.type = type;
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

	private String getUserHomeFragment() {
		return getString(R.string.fuserHomeFragment);
	}

	private String getCommentsFragment() {
		return getString(R.string.fcommentsFragment);
	}

	private String getLikeFragment() {
		return getString(R.string.flikeFragment);
	}

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub
		
	}

}
