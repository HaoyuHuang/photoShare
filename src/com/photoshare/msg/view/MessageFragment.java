/**
 * 
 */
package com.photoshare.msg.view;

import java.io.File;
import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.common.IObserver;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.exception.ValveException;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.msg.MessageItem;
import com.photoshare.msg.MessageList;
import com.photoshare.service.CommentHelper;
import com.photoshare.service.FollowHelper;
import com.photoshare.service.LikeHelper;
import com.photoshare.service.comments.PutCommentRequestParam;
import com.photoshare.service.comments.PutCommentResponseBean;
import com.photoshare.service.follow.UserFollowRequestParam;
import com.photoshare.service.follow.UserFollowResponseBean;
import com.photoshare.service.likes.PhotoLikeRequestParam;
import com.photoshare.service.likes.PhotoLikeResponseBean;
import com.photoshare.service.photos.PhotoUploadRequestParam;
import com.photoshare.service.photos.PhotoUploadResponseBean;
import com.photoshare.tabHost.R;
import com.photoshare.view.NotificationDisplayer;

/**
 * @author Aron
 * 
 */
public class MessageFragment extends BaseFragment {

	private MessageList messageList = MessageList.getInstance();

	private NotificationDisplayer mNotificationDisplayer;

	private MessageQueueView msgView;

	private String leftBtnText = "";

	private String rightBtnText = "";

	private String titlebarText = "";

	private int leftBtnVisibility = View.VISIBLE;

	private int rightBtnVisibility = View.GONE;

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelableArrayList(MessageItem.MSG_TAG,
					messageList.getArrayList());
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.message_activity_layout, container,
				false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(MessageItem.MSG_TAG)) {
				ArrayList<MessageItem> list = savedInstanceState
						.getParcelableArrayList(MessageItem.MSG_TAG);
				messageList.setList(list);
			}
		}
		super.onActivityCreated(savedInstanceState);

		initView();
	}

	private String getCommentTicker() {
		return getString(R.string.nCommentTicker);
	}

	private String getPhotoTicker() {
		return getString(R.string.nPhotoTicker);
	}

	private String getLikeTicker() {
		return getString(R.string.nLikeTicker);
	}

	private String getFollowTicker() {
		return getString(R.string.nFollowTicker);
	}

	private void initView() {
		try {
			leftBtnText = getPreferencesText();
			titlebarText = getMessageText();
			initTitleBar(leftBtnText, rightBtnText, titlebarText,
					leftBtnVisibility, rightBtnVisibility);
			messageList.initList();
			msgView = new MessageQueueView(getActivity(), getActivity()
					.findViewById(R.id.messageQueueLayoutId), async,
					messageList);
			msgView.registerListener(onMsgListener);
			msgView.applyView();

			mNotificationDisplayer = new NotificationDisplayer.NotificationBuilder()
					.Context(getActivity()).build();

		} catch (Exception e) {

		}
	}

	private String getPreferencesText() {
		return getString(R.string.preferences);
	}

	private String getMessageText() {
		return getString(R.string.messages);
	}

	private void AsyncPutComment(final MessageItem message,
			final IObserver<Boolean> observer) throws NetworkException {
		PutCommentRequestParam param = new PutCommentRequestParam();
		param.setComment(param.getComment());
		param.setmPhotoId(message.getEventId());
		param.setmUserId(user.getUserInfo().getUid());

		mNotificationDisplayer.setTicker(getCommentTicker());
		mNotificationDisplayer.displayNotification();

		CommentHelper.ICallback callback = new CommentHelper.ICallback() {

			public void OnNetworkError(NetworkError networkError) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mExceptionHandler.obtainMessage(
									NetworkError.ERROR_COMMENT).sendToTarget();
							observer.update(false);
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
							observer.update(false);
						}

					});
				}
			}

			public void OnComplete(PutCommentResponseBean comment) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mNotificationDisplayer
									.setTicker(getSuccessTicker());
							mNotificationDisplayer.displayNotification();
							observer.update(false);
							messageList.remove(message);
							mNotificationDisplayer.cancleNotification();
						}

					});
				}
			}
		};

		try {
			async.publishComments(param, callback);
		} catch (ValveException e) {
			mExceptionHandler.obtainMessage(NetworkError.ERROR_NETWORK)
					.sendToTarget();
		}
		mNotificationDisplayer.cancleNotification();
	}

	private void AsyncPublishLike(final MessageItem message,
			final IObserver<Boolean> observer) throws NetworkException {
		PhotoLikeRequestParam param = new PhotoLikeRequestParam.LikeBuilder()
				.PhotoId(message.getEventId())
				.UserId(user.getUserInfo().getUid())
				.isLike(message.isBtnStatus()).build();
		mNotificationDisplayer.setTicker(getLikeTicker());
		mNotificationDisplayer.displayNotification();
		LikeHelper.ICallback callback = new LikeHelper.ICallback() {

			public void OnNetworkError(NetworkError error) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mExceptionHandler.obtainMessage(
									NetworkError.ERROR_LIKE).sendToTarget();
							observer.update(false);
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
							observer.update(false);
						}

					});
				}
			}

			public void OnComplete(PhotoLikeResponseBean bean) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mNotificationDisplayer
									.setTicker(getSuccessTicker());
							mNotificationDisplayer.displayNotification();
							observer.update(false);
							messageList.remove(message);
							mNotificationDisplayer.cancleNotification();
						}

					});
				}
			}
		};
		try {
			async.publishLikePhoto(param, callback);
		} catch (ValveException e) {
			mExceptionHandler.obtainMessage(NetworkError.ERROR_NETWORK)
					.sendToTarget();
		}
		mNotificationDisplayer.cancleNotification();
	}

	private void AsyncPublishFollow(final MessageItem message,
			final IObserver<Boolean> observer) throws NetworkException {
		UserFollowRequestParam param = new UserFollowRequestParam.FollowBuilder()
				.UserId(user.getUserInfo().getUid())
				.isFollowing(message.isBtnStatus())
				.FollowId(message.getEventId()).build();

		mNotificationDisplayer.setTicker(getFollowTicker());
		mNotificationDisplayer.displayNotification();
		FollowHelper.ICallback callback = new FollowHelper.ICallback() {

			public void OnNetworkError(NetworkError error) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mExceptionHandler.obtainMessage(
									NetworkError.ERROR_FOLLOW).sendToTarget();
							observer.update(false);
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
							observer.update(false);
						}

					});
				}
			}

			public void OnComplete(UserFollowResponseBean bean) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mNotificationDisplayer
									.setTicker(getSuccessTicker());
							mNotificationDisplayer.displayNotification();
							observer.update(false);
							messageList.remove(message);
							mNotificationDisplayer.cancleNotification();
						}

					});
				}
			}

		};
		try {
			async.publishFollow(param, callback);
		} catch (ValveException e) {
			mExceptionHandler.obtainMessage(NetworkError.ERROR_NETWORK)
					.sendToTarget();
		}
		mNotificationDisplayer.cancleNotification();
	}

	private void AsyncPublishPhoto(final MessageItem message,
			final IObserver<Boolean> observer) throws NetworkException {
		PhotoUploadRequestParam param = new PhotoUploadRequestParam();
		param.setCaption(message.getMsgDescription());
		param.setUid(user.getUserInfo().getUid());
		param.setFile(new File(message.getMsgPhotoUrl()));

		mNotificationDisplayer.setTicker(getPhotoTicker());
		mNotificationDisplayer.displayNotification();

		AbstractRequestListener<PhotoUploadResponseBean> listener = new AbstractRequestListener<PhotoUploadResponseBean>() {

			@Override
			public void onNetworkError(NetworkError networkError) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mExceptionHandler.obtainMessage(
									NetworkError.ERROR_PHOTO).sendToTarget();
							observer.update(false);
						}

					});
				}
			}

			@Override
			public void onFault(Throwable fault) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mExceptionHandler.obtainMessage(
									NetworkError.ERROR_NETWORK).sendToTarget();
							observer.update(false);
						}

					});
				}

			}

			@Override
			public void onComplete(PhotoUploadResponseBean bean) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mNotificationDisplayer
									.setTicker(getSuccessTicker());
							mNotificationDisplayer.displayNotification();
							observer.update(false);
							messageList.remove(message);
							mNotificationDisplayer.cancleNotification();
						}

					});
				}

			}
		};
		async.publishPhoto(param, listener);
		mNotificationDisplayer.cancleNotification();

	}

	private MessageQueueView.OnMsgListener onMsgListener = new MessageQueueView.OnMsgListener() {

		public void OnMsgClicked(MessageItem message,
				IObserver<Boolean> observer) {
			try {
				switch (message.getMsgType()) {
				case COMMENT:
					AsyncPutComment(message, observer);
					break;
				case FOLLOW:
					AsyncPublishFollow(message, observer);
					break;
				case LIKE:
					AsyncPublishLike(message, observer);
					break;
				case NULL:
					break;
				case PHOTO:
					AsyncPublishPhoto(message, observer);
					break;
				default:
					break;
				}
			} catch (NetworkException e) {
				AsyncSignIn();
			}
		}

		public void OnImageLoaded(final ImageView image,
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
