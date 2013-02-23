/**
 * 
 */
package com.photoshare.service.users.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.users.UserGetInfoRequestParam;
import com.photoshare.service.users.UserGetInfoResponseBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;

/**
 * @author Aron
 * 
 */
public class UserProfileFragment extends BaseFragment {

	private UserInfoView userInfoView;
	private UserInfo userInfo;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";
	private int leftBtnVisibility = View.VISIBLE;
	private int rightBtnVisibility = View.GONE;

	public static UserProfileFragment newInstance(int fragmentViewId) {
		UserProfileFragment up = new UserProfileFragment();
		up.setFragmentViewId(fragmentViewId);
		return up;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
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
		}
		leftBtnText = getHomeText();
		titlebarText = getProfileText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		try {
			AsyncGetUserInfo();
		} catch (NetworkException e) {
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

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.user_profile_layout, container, false);
	}

	private String getProfileText() {
		return getString(R.string.profile);
	}

	private String getHomeText() {
		return getString(R.string.home);
	}

	private void AsyncGetUserInfo() throws NetworkException {
		UserGetInfoRequestParam param = new UserGetInfoRequestParam(
				userInfo.getUid());
		AbstractRequestListener<UserGetInfoResponseBean> listener = new AbstractRequestListener<UserGetInfoResponseBean>() {

			@Override
			public void onNetworkError(NetworkError networkError) {
				mExceptionHandler
						.obtainMessage(NetworkError.ERROR_REFRESH_DATA)
						.sendToTarget();
			}

			@Override
			public void onFault(Throwable fault) {
				mExceptionHandler.obtainMessage(NetworkError.ERROR_NETWORK)
						.sendToTarget();
			}

			@Override
			public void onComplete(final UserGetInfoResponseBean bean) {
				userInfo = bean.getUserInfo();
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						userInfoView = new UserInfoView(getActivity(),
								getActivity()
										.findViewById(R.id.userProfileView),
								userInfo);
						userInfoView.applyView();
					}

				});
			}
		};
		async.getUsersInfo(param, listener);
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

	private String getUserHomeFragment() {
		return getString(R.string.fuserHomeFragment);
	}

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub
		
	}

}
