/**
 * 
 */
package com.photoshare.service.users.views;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Browser;
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
import com.photoshare.fragments.PhotoBarFragment;
import com.photoshare.service.FollowHelper;
import com.photoshare.service.follow.FollowType;
import com.photoshare.service.follow.UserFollowRequestParam;
import com.photoshare.service.follow.UserFollowResponseBean;
import com.photoshare.service.users.UserGetInfoResponseBean;
import com.photoshare.service.users.UserGetOtherInfoRequestParam;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.view.AppTitleBarView;
import com.photoshare.view.NotificationDisplayer;
import com.photoshare.view.OtherHomeTitleBarView;

/**
 * @author Aron
 * 
 *         This fragment designed to display the title bar of the user's home,
 *         using the view {@link OtherHomeTitleBarView} containing information
 *         about user's head, name, biography, Website, follower count,
 *         following count, photos count. And in combination with fragment
 *         {@link PhotoBarFragment} to display the whole user home.
 * 
 */
public class OtherHomeTitleBarFragment extends BaseFragment {
	private OtherHomeTitleBarView homeTitle;
	private UserInfo userInfo;
	private NotificationDisplayer notification;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private int leftBtnVisibility = View.VISIBLE;
	private String titlebarText = "";
	private int rightBtnVisibility = View.GONE;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(UserInfo.KEY_USER_INFO)) {
				userInfo = savedInstanceState
						.getParcelable(UserInfo.KEY_USER_INFO);
			}
			if (savedInstanceState.containsKey(AppTitleBarView.LEFT_BTN_TEXT)) {
				leftBtnText = savedInstanceState
						.getString(AppTitleBarView.LEFT_BTN_TEXT);
			}
		}
		super.onActivityCreated(savedInstanceState);
		Bundle bundle = getArguments();
		if (bundle != null) {
			if (bundle.containsKey(UserInfo.KEY_USER_INFO)) {
				userInfo = bundle.getParcelable(UserInfo.KEY_USER_INFO);
			}
		}
		try {
			if (userInfo != null && userInfo.getBio() != null) {
				initTitleBar(leftBtnText, rightBtnText, userInfo.getName(),
						leftBtnVisibility, View.GONE);
				initViews();
			} else {
				AsyncGetOthersInfo();
			}
		} catch (NetworkException e) {
			AsyncSignIn();
		} catch (Exception e) {

		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.other_home_title_bar, container, false);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelable(UserInfo.KEY_USER_INFO, userInfo);
			outState.putString(AppTitleBarView.LEFT_BTN_TEXT, leftBtnText);
		}
		super.onSaveInstanceState(outState);
	}

	public static OtherHomeTitleBarFragment newInstance(int fragmentViewId) {
		OtherHomeTitleBarFragment uh = new OtherHomeTitleBarFragment();
		uh.setFragmentViewId(fragmentViewId);
		return uh;
	}

	private String getBackText() {
		return getString(R.string.back);
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	private void initViews() {
		leftBtnText = getBackText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		notification = new NotificationDisplayer.NotificationBuilder()
				.Context(getActivity()).Ticker("开始跟随").build();
		homeTitle = new OtherHomeTitleBarView(getActivity().findViewById(
				R.id.otherHomeTitleBarLayoutId), userInfo, async);
		homeTitle.registerCallback(TitleBarCallBack);
		homeTitle.applyView();
	}

	private void OnFollowClicked() {
		Bundle param = new Bundle();
		param.putParcelable(UserInfo.KEY_USER_INFO, userInfo);
		param.putString(UserInfo.KEY_FOLLOW_TYPE,
				FollowType.FOLLOWER.toString());
		Command.forward(this, getFollowFragment(), param);
	}

	private void OnFollowingClicked() {
		Bundle param = new Bundle();
		param.putParcelable(UserInfo.KEY_USER_INFO, userInfo);
		param.putString(UserInfo.KEY_FOLLOW_TYPE,
				FollowType.FOLLOWER.toString());
		Command.forward(this, getFollowFragment(), param);
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
								notification.displayNotification();
							}
						}
					});
				}
			}
		};
		try {
			async.publishFollow(param, mCallback);
		} catch (ValveException e) {
			mExceptionHandler.obtainMessage(
					NetworkError.ERROR_NETWORK).sendToTarget();
		}
	}

	private void AsyncGetOthersInfo() throws NetworkException {
		UserGetOtherInfoRequestParam param = new UserGetOtherInfoRequestParam(
				user.getUserInfo().getUid(), userInfo.getUid());
		AbstractRequestListener<UserGetInfoResponseBean> listener = new AbstractRequestListener<UserGetInfoResponseBean>() {

			@Override
			public void onNetworkError(NetworkError networkError) {
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
			public void onComplete(UserGetInfoResponseBean bean) {
				if (getActivity() != null) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							if (homeTitle != null) {

								homeTitle.applyView();
							}
						}

					});
				}

			}
		};
		async.getOthersInfo(param, listener);
	}

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
