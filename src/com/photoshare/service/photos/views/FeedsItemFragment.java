/**
 * 
 */
package com.photoshare.service.photos.views;

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
import com.photoshare.service.LikeHelper;
import com.photoshare.service.likes.PhotoLikeRequestParam;
import com.photoshare.service.likes.PhotoLikeResponseBean;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.PhotoGetInfoRequestParam;
import com.photoshare.service.photos.PhotoGetInfoResponseBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.Utils;
import com.photoshare.view.AppTitleBarView;
import com.photoshare.view.NotificationDisplayer;

/**
 * @author Aron
 * 
 */
public class FeedsItemFragment extends BaseFragment {

	private FeedItemView itemView;
	private PhotoBean photo;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";
	private int leftBtnVisibility = View.VISIBLE;
	private int rightBtnVisibility = View.GONE;
	private NotificationDisplayer mNotificationDisplayer;

	public static FeedsItemFragment newInstance(int fragmentViewId) {
		FeedsItemFragment fif = new FeedsItemFragment();
		fif.setFragmentViewId(fragmentViewId);
		return fif;
	}

	public PhotoBean getPhoto() {
		return photo;
	}

	private void initViews() {
		Tag = getFeedItemFragment();
		itemView = new FeedItemView(getActivity().findViewById(
				R.id.feedsItemLayoutId), async, photo);
		itemView.registerCallback(mCallback);
		itemView.applyView();
		mNotificationDisplayer = new NotificationDisplayer.NotificationBuilder()
				.Context(getActivity()).Tag(getLikeTag())
				.Ticker(getLikeTicker()).build();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(PhotoBean.KEY_PHOTO)) {
				photo = savedInstanceState.getParcelable(PhotoBean.KEY_PHOTO);
			}
			if (savedInstanceState.containsKey(AppTitleBarView.LEFT_BTN_TEXT)) {
				leftBtnText = savedInstanceState
						.getString(AppTitleBarView.LEFT_BTN_TEXT);
			}
		}
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			if (bundle.containsKey(PhotoBean.KEY_PHOTO)) {
				photo = bundle.getParcelable(PhotoBean.KEY_PHOTO);
			}
			if (bundle.containsKey(AppTitleBarView.LEFT_BTN_TEXT)) {
				leftBtnText = bundle.getString(AppTitleBarView.LEFT_BTN_TEXT);
			}
		}
		leftBtnText = getBackText();
		titlebarText = getPhotoText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		try {
			AsyncGetPhotoInfo();
		} catch (NetworkException e) {
			e.printStackTrace();
			AsyncSignIn();
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.simple_list_item_feeds, container,
				false);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelable(PhotoBean.KEY_PHOTO, photo);
			outState.putString(AppTitleBarView.LEFT_BTN_TEXT, leftBtnText);
		}
		super.onSaveInstanceState(outState);
	}

	private String getLikeTag() {
		return getString(R.string.nLikeTag);
	}

	private String getLikeTicker() {
		return getString(R.string.nLikeTicker);
	}

	private String getPhotoText() {
		return getString(R.string.photos);
	}

	private String getFeedItemFragment() {
		return getString(R.string.ffeedsItemFragment);
	}

	private void AsyncLikePhoto(PhotoBean photo) throws NetworkException {
		PhotoLikeRequestParam param = new PhotoLikeRequestParam.LikeBuilder()
				.UserId(user.getUserInfo().getUid()).PhotoId(photo.getPid())
				.isLike(photo.isLike()).build();
		mNotificationDisplayer.setTag(getLikeTag());
		mNotificationDisplayer.setTicker(getLikeTicker());
		mNotificationDisplayer.displayNotification();

		LikeHelper.ICallback mCallback = new LikeHelper.ICallback() {

			public void OnNetworkError(NetworkError error) {

				getActivity().runOnUiThread(new Runnable() {

					public void run() {

					}

				});
			}

			public void OnFault(Throwable fault) {
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

	private void AsyncGetPhotoInfo() throws NetworkException {
		PhotoGetInfoRequestParam param = new PhotoGetInfoRequestParam(
				photo.getPid());
		Log.i("getPhotoInfo", photo.getPid() + "");
		AbstractRequestListener<PhotoGetInfoResponseBean> listener = new AbstractRequestListener<PhotoGetInfoResponseBean>() {

			@Override
			public void onNetworkError(NetworkError networkError) {
				mExceptionHandler
						.obtainMessage(NetworkError.ERROR_REFRESH_DATA)
						.sendToTarget();
				getActivity().runOnUiThread(new Runnable() {

					public void run() {

					}

				});
			}

			@Override
			public void onFault(Throwable fault) {
				mExceptionHandler.obtainMessage(NetworkError.ERROR_NETWORK)
						.sendToTarget();
				getActivity().runOnUiThread(new Runnable() {

					public void run() {

					}

				});
			}

			@Override
			public void onComplete(PhotoGetInfoResponseBean bean) {
				if (bean != null) {
					photo = bean.getPhoto();
					Utils.logger(photo.toString());
				}
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						initViews();
					}

				});
			}
		};
		async.getPhotoInfo(param, listener);
	}

	private String getUserHomeFragment() {
		return getString(R.string.fuserHomeFragment);
	}

	private String getLikeFragment() {
		return getString(R.string.flikeFragment);
	}

	private String getCommentFragment() {
		return getString(R.string.fcommentsFragment);
	}

	private String getBackText() {
		return getString(R.string.back);
	}

	private FeedItemView.ICallback mCallback = new FeedItemView.ICallback() {

		public void OnNameClick(UserInfo info) {
			Bundle args = new Bundle();
			args.putParcelable(PhotoBean.KEY_PHOTO, photo);
			args.putParcelable(UserInfo.KEY_USER_INFO, info);
			forward(getUserHomeFragment(), args);
		}

		public void OnLikeListClick(PhotoBean like) {
			Bundle args = new Bundle();
			args.putParcelable(PhotoBean.KEY_PHOTO, like);
			forward(getLikeFragment(), args);
		}

		public void OnCommentListClick(PhotoBean photo) {
			Bundle args = new Bundle();
			args.putParcelable(PhotoBean.KEY_PHOTO, photo);
			forward(getCommentFragment(), args);
		}

		public void OnLikeClick(PhotoBean photo) {
			try {
				AsyncLikePhoto(photo);
			} catch (NetworkException e) {
				AsyncSignIn();
			}
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
		backward(null);
	}

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub

	}

}
