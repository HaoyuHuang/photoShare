/**
 * 
 */
package com.photoshare.service.follow.views;

import java.util.ArrayList;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.photoshare.command.Command;
import com.photoshare.common.AbstractRequestListener;
import com.photoshare.common.IObserver;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.FollowHelper;
import com.photoshare.service.follow.FollowType;
import com.photoshare.service.follow.UserFollowRequestParam;
import com.photoshare.service.follow.UserFollowResponseBean;
import com.photoshare.service.follow.UserGetFollowInfoRequestParam;
import com.photoshare.service.follow.UserGetFollowInfoResponseBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.view.AppTitleBarView;
import com.photoshare.view.NotificationDisplayer;

/**
 * @author Aron
 * 
 */
public class FollowsInfoFragment extends BaseFragment {

	private FollowsView followsView;
	private FollowType type;
	private UserInfo userInfo;
	private ArrayList<UserInfo> userInfos;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";
	private int leftBtnVisibility = View.VISIBLE;
	private int rightBtnVisibility = View.GONE;

	// private NotificationDisplayer mNotificationDisplayer;

	public static FollowsInfoFragment newInstance(int fragmentViewId) {
		FollowsInfoFragment fi = new FollowsInfoFragment();
		fi.setFragmentViewId(fragmentViewId);
		return fi;
	}

	private String getFollowsInfoFragment() {
		return getString(R.string.ffollowInfoFragment);
	}

	public FollowType getType() {
		return type;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		leftBtnText = getBackText();
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			if (bundle.containsKey(UserInfo.KEY_USER_INFO)) {
				userInfo = bundle.getParcelable(UserInfo.KEY_USER_INFO);
			}
			if (bundle.containsKey(UserInfo.KEY_FOLLOW_TYPE)) {
				type = FollowType.SWITCH(bundle
						.getString(UserInfo.KEY_FOLLOW_TYPE));
				switch (type) {
				case FOLLOWER:
					titlebarText = getFollowerText();
					break;
				case FOLLOWING:
					titlebarText = getFollowingText();
					break;
				default:
					break;
				}
			}
		}
		try {
			AsyncGetFollowInfo();
		} catch (NetworkException e) {
			AsyncSignIn();
		}
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelableArrayList(UserInfo.KEY_USER_INFOS, userInfos);
			outState.putString(AppTitleBarView.LEFT_BTN_TEXT, leftBtnText);
			outState.putString(AppTitleBarView.TITLE_BAR_TEXT, titlebarText);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.user_follow_list_layout, container,
				false);
	}

	private void initView() {
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		followsView = new FollowsView(getActivity().findViewById(
				R.id.userFollowListLayoutId), userInfos, getActivity(), async);
		followsView.registerCallback(followListener);
		followsView.applyView();
		// mNotificationDisplayer = new
		// NotificationDisplayer.NotificationBuilder()
		// .Context(getActivity()).Tag(getFollowTag())
		// .Ticker(getFollowTicker()).build();

	}

	private String getFollowTicker() {
		return getString(R.string.nFollowTicker);
	}

	private String getFollowTag() {
		return getString(R.string.nFollowTag);
	}

	private String getBackText() {
		return getString(R.string.back);
	}

	private String getUserHomeFragment() {
		return getString(R.string.fuserHomeFragment);
	}

	private String getFollowingText() {
		return getString(R.string.following);
	}

	private String getFollowerText() {
		return getString(R.string.follower);
	}

	private void AsyncGetFollowInfo() throws NetworkException {
		UserGetFollowInfoRequestParam param = null;
		switch (type) {
		case FOLLOWER:
			param = new UserGetFollowInfoRequestParam(userInfo.getUid());
			break;
		case FOLLOWING:
			param = new UserGetFollowInfoRequestParam(userInfo.getUid());
			break;
		}
		param.setType(type);
		AbstractRequestListener<UserGetFollowInfoResponseBean> listener = new AbstractRequestListener<UserGetFollowInfoResponseBean>() {

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
			public void onComplete(final UserGetFollowInfoResponseBean bean) {
				if (bean != null) {
					userInfos = bean.getFollowInfos();
				}
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							if (bean != null) {
								initView();
							}
						}
					});
				}
			}
		};
		async.getFollowsInfo(param, listener);
	}

	private void AsyncOnFollowClick(final IObserver<Boolean> observer)
			throws NetworkException {
		UserFollowRequestParam param = new UserFollowRequestParam.FollowBuilder()
				.FollowId(userInfo.getUid())
				.UserId(user.getUserInfo().getUid())
				.isFollowing(userInfo.isFollowing()).build();
		// mNotificationDisplayer.displayNotification();

		FollowHelper.ICallback mCallback = new FollowHelper.ICallback() {

			public void OnNetworkError(final NetworkError error) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {

						}
					});
				}
			}

			public void OnFault(final Throwable fault) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {

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
								// mNotificationDisplayer.setTag(getSuccessTag());
								// mNotificationDisplayer
								// .setTicker(getSuccessTicker());
								// mNotificationDisplayer.displayNotification();
								observer.update(userInfo.isFollowing());
								// mNotificationDisplayer.cancleNotification();
							}
						}
					});
				}
			}
		};
		async.publishFollow(param, mCallback);
		// mNotificationDisplayer.cancleNotification();
	}

	private boolean checkFollowingResponseBean(UserFollowResponseBean bean) {
		if (bean == null || bean.getUserId() != user.getUserInfo().getUid()
				|| bean.getFollowId() != userInfo.getUid())
			return false;
		if (bean.isFollowing()) {
			userInfo.setFollowing(true);
			user.getUserInfo().setFollowingCnt(
					user.getUserInfo().getFollowingCnt() + 1);
		} else {
			userInfo.setFollowing(false);
			user.getUserInfo().setFollowingCnt(
					user.getUserInfo().getFollowingCnt() - 1);
		}
		return true;
	}

	private FollowsView.OnFollowActionListener followListener = new FollowsView.OnFollowActionListener() {

		public void OnUserNameClick(UserInfo info) {
			Command.UserHome(getActivity(), info.params());
		}

		public void OnFollowClick(UserInfo info, IObserver<Boolean> observer) {
			try {
				AsyncOnFollowClick(observer);
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
