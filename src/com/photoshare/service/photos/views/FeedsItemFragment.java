/**
 * 
 */
package com.photoshare.service.photos.views;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.photoshare.command.Command;
import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.exception.ValveException;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.LikeHelper;
import com.photoshare.service.comments.CommentAction;
import com.photoshare.service.comments.CommentInfo;
import com.photoshare.service.likes.LikeAction;
import com.photoshare.service.likes.LikeBean;
import com.photoshare.service.likes.PhotoLikeRequestParam;
import com.photoshare.service.likes.PhotoLikeResponseBean;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.PhotoGetInfoRequestParam;
import com.photoshare.service.photos.PhotoGetInfoResponseBean;
import com.photoshare.service.photos.factory.PhotoFactory;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.QuartzUtils;
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
	private int rightBtnVisibility = View.VISIBLE;
	private NotificationDisplayer mNotificationDisplayer;
	private FeedsItemPopMenu popMenu;
	private Bitmap rawPhoto;

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
				R.id.feedsItemLayoutId), async, photo, true);
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
		rightBtnText = getMoreText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		popMenu = new FeedsItemPopMenu(getActivity());
		popMenu.init();
		popMenu.registerItemMenuClickListener(itemMenuClickListener);
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
		return inflater.inflate(R.layout.photo_layout, container, false);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelable(PhotoBean.KEY_PHOTO, photo);
			outState.putString(AppTitleBarView.LEFT_BTN_TEXT, leftBtnText);
		}
		super.onSaveInstanceState(outState);
	}

	private String getMoreText() {
		return getString(R.string.more);
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

	private void AsyncLikePhoto(final PhotoBean photo) throws NetworkException {
		PhotoLikeRequestParam param = new PhotoLikeRequestParam.LikeBuilder()
				.UserId(user.getUserInfo().getUid()).PhotoId(photo.getPid())
				.isLike(photo.isLike()).build();
		// mNotificationDisplayer.displayNotification();
		System.out.println(param);
		LikeHelper.ICallback mCallback = new LikeHelper.ICallback() {

			public void OnNetworkError(NetworkError error) {

				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {

						}

					});
				}
			}

			public void OnFault(Throwable fault) {
				mExceptionHandler.obtainMessage(NetworkError.ERROR_NETWORK)
						.sendToTarget();
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {

						}

					});
				}
			}

			public void OnComplete(final PhotoLikeResponseBean bean) {
				mNotificationDisplayer.setTag(getSuccessTag());
				mNotificationDisplayer.setTicker(getSuccessTicker());
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							if (bean != null && bean.getUserId() != 0) {
								photo.setLike(!bean.isLike());
							}
							mNotificationDisplayer.displayNotification();
							mNotificationDisplayer.cancleNotification();
						}

					});
				}
			}
		};
		try {
			async.publishLikePhoto(param, mCallback);
		} catch (ValveException e) {
			System.out.println(e.getMessage());
			mExceptionHandler.obtainMessage(NetworkError.ERROR_NETWORK)
					.sendToTarget();
		}
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
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {

						}

					});
				}
			}

			@Override
			public void onFault(Throwable fault) {
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
			public void onComplete(PhotoGetInfoResponseBean bean) {
				if (bean != null) {
					photo = bean.getPhoto();
					Utils.logger(photo.toString());
				}
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							initViews();
						}

					});
				}
			}
		};
		async.getPhotoInfo(param, listener);
	}

	private String getUserHomeFragment() {
		return getString(R.string.fuserHomeFragment);
	}

	private String getPhotoFilterFragment() {
		return getString(R.string.fphotoFilterFragment);
	}

	private String getCropPhotoFragment() {
		return getString(R.string.fCropPhotoFragment);
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

	private void savePhoto() {
		PhotoFactory.savePhototoImageStore(getActivity(), rawPhoto,
				Utils.APP_NAME + "-" + QuartzUtils.formattedNow(),
				photo.getCaption());
	}

	private FeedsItemPopMenu.FeedsItemMenuClickListener itemMenuClickListener = new FeedsItemPopMenu.FeedsItemMenuClickListener() {

		public void onSavetoImageStoreClick() {
			savePhoto();
		}

		public void onDecorateImageClick() {
			Bundle args = new Bundle();
			args.putParcelable(PhotoBean.KEY_PHOTO, photo);
			args.putParcelable(PhotoBean.KEY_SRC_PHOTO, rawPhoto);
			args.putBoolean(KEY_IGNORE_TITLE_VIEW, true);
			forward(getPhotoFilterFragment(), args);
		}

		public void onCropImageClick() {
			Bundle args = new Bundle();
			args.putParcelable(PhotoBean.KEY_PHOTO, photo);
			args.putParcelable(PhotoBean.KEY_SRC_PHOTO, rawPhoto);
			args.putBoolean(KEY_IGNORE_TITLE_VIEW, true);
			forward(getPhotoFilterFragment(), args);
		}
	};

	private FeedItemView.ICallback mCallback = new FeedItemView.ICallback() {

		public void OnNameClick(UserInfo info) {
			Bundle args = new Bundle();
			args.putParcelable(PhotoBean.KEY_PHOTO, photo);
			args.putParcelable(UserInfo.KEY_USER_INFO, info);
			Command.UserHome(getActivity(), args);
		}

		public void OnLikeListClick(PhotoBean like) {
			Bundle args = new Bundle();
			args.putParcelable(PhotoBean.KEY_PHOTO, like);
			args.putInt(LikeBean.KEY_LIKE_ACTION, LikeAction.LIKE.getCode());
			forward(getLikeFragment(), args);
		}

		public void OnCommentListClick(PhotoBean photo) {
			Bundle args = new Bundle();
			args.putParcelable(PhotoBean.KEY_PHOTO, photo);
			args.putInt(CommentInfo.KEY_COMMENT_ACTION,
					CommentAction.COMMENTS.getCode());
			forward(getCommentFragment(), args);
		}

		public void OnLikeClick(PhotoBean photo) {
			try {
				showLike(photo);
				AsyncLikePhoto(photo);
			} catch (NetworkException e) {
				AsyncSignIn();
			}
		}

		public void OnUserHeadLoaded(final ImageView image,
				final Drawable drawable, String url) {
			if (getActivity() != null) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						image.setImageDrawable(drawable);
					}
				});
			}
		}

		public void OnFeedPhotoLoaded(final ImageView image,
				final Drawable drawable, String url) {
			if (getActivity() != null) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						rawPhoto = ((BitmapDrawable) drawable).getBitmap();
						image.setImageDrawable(drawable);
					}
				});
			}

		}

		public void OnUserHeadDefault(final ImageView image) {
			if (getActivity() != null) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						image.setImageResource(R.drawable.icon);
					}
				});
			}
		}

		public void OnFeedPhotoDefault(final ImageView image) {
			if (getActivity() != null) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						image.setImageResource(R.drawable.icon);
					}
				});
			}
		}
	};

	private void showLike(PhotoBean photo) {

		// String likeText = ctx.getString(R.string.like);
		// String unlikeText = ctx.getString(R.string.unlike);
		boolean isLike = photo.isLike();
		Toast toast = new Toast(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View layout = inflater.inflate(R.layout.toast_like, null);
		ImageView image = (ImageView) layout.findViewById(R.id.toastLikeView);
		if (isLike) {
			image.setImageResource(R.drawable.unlike);
		} else {
			image.setImageResource(R.drawable.like);
		}
		toast.setGravity(Gravity.CENTER, 0, 0);
		// ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
		// PhotoType.MIDDLE.getWidth(), PhotoType.MIDDLE.getHeight());
		// layout.setLayoutParams(params);
		// image.setLayoutParams(params);
		layout.setAlpha(0.2f);
		toast.setDuration(Toast.LENGTH_SHORT);
		toast.setView(layout);
		toast.show();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void onRightBtnClicked(View view) {
		popMenu.display(view);
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

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub

	}

}
