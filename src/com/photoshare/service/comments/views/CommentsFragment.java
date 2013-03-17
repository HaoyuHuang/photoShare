package com.photoshare.service.comments.views;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.photoshare.command.Command;
import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.CommentHelper;
import com.photoshare.service.comments.CommentsGetInfoRequestParam;
import com.photoshare.service.comments.CommentsGetInfoResponseBean;
import com.photoshare.service.comments.PutCommentRequestParam;
import com.photoshare.service.comments.PutCommentResponseBean;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.view.AppTitleBarView;
import com.photoshare.view.NotificationDisplayer;

/**
 * @author Aron
 * 
 */
public class CommentsFragment extends BaseFragment {

	private PhotoBean photo;
	private CommentsInfoView commentView;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";
	private int leftBtnVisibility = View.VISIBLE;
	private int rightBtnVisibility = View.GONE;
	private NotificationDisplayer mNotificationDisplayer;

	public static CommentsFragment newInstance(int fragmentViewId) {
		CommentsFragment comments = new CommentsFragment();
		comments.setFragmentViewId(fragmentViewId);
		return comments;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		leftBtnText = getBackText();
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
		try {
			Bundle bundle = getArguments();
			if (bundle != null) {
				if (bundle.containsKey(PhotoBean.KEY_PHOTO)) {
					photo = bundle.getParcelable(PhotoBean.KEY_PHOTO);
				}
				if (bundle.containsKey(AppTitleBarView.LEFT_BTN_TEXT)) {
					leftBtnText = bundle
							.getString(AppTitleBarView.LEFT_BTN_TEXT);
				}
			}
			initViews();
			if (photo != null && photo.getComments() != null
					&& !photo.getComments().isEmpty()) {

			} else {
				AsyncGetComments(0, 20);
			}
		} catch (NetworkException e) {

		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelable(PhotoBean.KEY_PHOTO, photo);
			outState.putString(AppTitleBarView.LEFT_BTN_TEXT, leftBtnText);
		}
		super.onSaveInstanceState(outState);
	}

	/**
	 * 
	 */
	private void initViews() {

		titlebarText = getCommentTitleText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		commentView = new CommentsInfoView(getActivity(), getActivity()
				.findViewById(R.id.commentId), photo, async);
		commentView.registerListener(onCommentInfoClickListener);
		commentView.applyView();
		mNotificationDisplayer = new NotificationDisplayer.NotificationBuilder()
				.Context(getActivity()).Tag(getCommentTag())
				.Ticker(getCommentTicker()).build();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.comments_layout, container, false);
	}

	private void AsyncPutComment(String comment) throws NetworkException {

		PutCommentRequestParam param = new PutCommentRequestParam();
		param.setComment(comment);
		param.setmPhotoId(photo.getPid());
		param.setmUserId(user.getUserInfo().getUid());

		mNotificationDisplayer.setTicker(getCommentTicker());
		mNotificationDisplayer.setTag(getCommentTag());
		mNotificationDisplayer.displayNotification();

		async.publishComments(param, new CommentHelper.ICallback() {

			public void OnNetworkError(NetworkError networkError) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mExceptionHandler.obtainMessage(
									NetworkError.ERROR_COMMENT).sendToTarget();
						}
					});
				}
			}

			public void OnFault(Throwable fault) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mExceptionHandler.obtainMessage(
									NetworkError.ERROR_NETWORK).sendToTarget();
						}

					});
				}

			}

			public void OnComplete(final PutCommentResponseBean comment) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							if (comment != null) {
								if (comment.getComment() != null) {
									mNotificationDisplayer
											.setTicker(getSuccessTicker());
									mNotificationDisplayer
											.setTag(getSuccessTag());
									mNotificationDisplayer
											.displayNotification();
									commentView.addComment(comment.getComment());
									mNotificationDisplayer.cancleNotification();
								}
							}
						}

					});
				}

			}
		});
		mNotificationDisplayer.cancleNotification();
	}

	private void AsyncGetComments(int cPage, int dPage) throws NetworkException {

		CommentsGetInfoRequestParam param = new CommentsGetInfoRequestParam(
				photo.getPid());
		param.setCurrentPage(cPage);
		param.setDemandPage(dPage);

		mNotificationDisplayer.setTag(getRefreshTag());
		mNotificationDisplayer.setTicker(getSuccessTicker());
		mNotificationDisplayer.displayNotification();

		AbstractRequestListener<CommentsGetInfoResponseBean> listener = new AbstractRequestListener<CommentsGetInfoResponseBean>() {

			@Override
			public void onComplete(final CommentsGetInfoResponseBean bean) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							if (bean.getComments() != null) {
								if (bean.getComments().size() != 0) {
									commentView.addComments(bean.getComments());
								}
							}
						}
					});
				}
			}

			@Override
			public void onFault(final Throwable fault) {
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
			public void onNetworkError(final NetworkError networkError) {
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

		};
		async.getCommentsInfo(param, listener);
		mNotificationDisplayer.cancleNotification();
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
		backward(null);
	}

	private String getCommentsFragment() {
		return getString(R.string.fcommentsFragment);
	}

	private String getCommentTicker() {
		return getString(R.string.nCommentTicker);
	}

	private String getCommentTag() {
		return getString(R.string.nCommentTag);
	}

	private String getCommentTitleText() {
		return getString(R.string.comment);
	}

	private String getBackText() {
		return getString(R.string.back);
	}

	private void OnLoadAllComments() {

	}

	private CommentsInfoView.OnCommentInfoClickListener onCommentInfoClickListener = new CommentsInfoView.OnCommentInfoClickListener() {

		public void OnPutComment(String comment) {
			try {
				AsyncPutComment(comment);
			} catch (NetworkException e) {
				mExceptionHandler
						.obtainMessage(NetworkError.ERROR_SIGN_IN_NULL)
						.sendToTarget();
			}
		}

		public void OnLoadMore(int currentPage, int demandPage) {
			try {
				AsyncGetComments(currentPage, demandPage);
			} catch (NetworkException e) {
				mExceptionHandler
						.obtainMessage(NetworkError.ERROR_SIGN_IN_NULL)
						.sendToTarget();
			}
		}

		public void OnLoadAll() {
			OnLoadAllComments();
		}

		public void OnNameClicked(UserInfo info) {
			Bundle args = new Bundle();
			args.putParcelable(UserInfo.KEY_USER_INFO, info);
			Command.UserHome(getActivity(), args);
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

		public void OnImageDefault(final ImageView image) {
			if (getActivity() != null) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						image.setImageResource(R.drawable.icon);
					}

				});
			}
		}
	};

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub

	}

}
