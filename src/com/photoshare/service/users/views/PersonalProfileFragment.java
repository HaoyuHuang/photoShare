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
import com.photoshare.service.users.UserEditInfoRequestParam;
import com.photoshare.service.users.UserGetInfoResponseBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;

/**
 * @author Aron
 * 
 */
public class PersonalProfileFragment extends BaseFragment {

	public static PersonalProfileFragment newInstance(int fragmentViewId) {
		PersonalProfileFragment pp = new PersonalProfileFragment();
		pp.setFragmentViewId(fragmentViewId);
		return pp;
	}

	private UserInfoEditView mUserInfoEditView;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";
	private int leftBtnVisibility = View.VISIBLE;
	private int rightBtnVisibility = View.GONE;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(UserInfo.KEY_USER_INFO)) {
				UserInfo info = savedInstanceState
						.getParcelable(UserInfo.KEY_USER_INFO);
				user.setUserInfo(info);
			}
		}
		super.onActivityCreated(savedInstanceState);

		initViews();
	}

	/**
	 * 
	 */
	private void initViews() {
		leftBtnText = getPreferencesText();
		titlebarText = getProfileText();
		rightBtnText = getSubmitText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		mUserInfoEditView = new UserInfoEditView(user.getUserInfo(),
				getActivity().findViewById(R.id.personalProfileId));
		mUserInfoEditView.registerListener(listener);
		mUserInfoEditView.applyView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.personal_profile_layout, container,
				false);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelable(UserInfo.KEY_USER_INFO, user.getUserInfo());
		}
		super.onSaveInstanceState(outState);
	}

	private void AsyncSubmitUserInfo(UserInfo info) throws NetworkException {
		UserEditInfoRequestParam param = new UserEditInfoRequestParam(info);
		AbstractRequestListener<UserGetInfoResponseBean> listener = new AbstractRequestListener<UserGetInfoResponseBean>() {

			@Override
			public void onComplete(final UserGetInfoResponseBean bean) {
				if (bean != null) {
					user.setUserInfo(bean.getUserInfo());
					user.setLogging(true);
				}
				getActivity().runOnUiThread(new Runnable() {

					public void run() {

					}

				});
			}

			@Override
			public void onNetworkError(final NetworkError networkError) {
				mExceptionHandler
						.obtainMessage(NetworkError.ERROR_EDIT_PROFILE)
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

		};
		async.getEditUserInfo(param, listener);
	}

	private UserInfoEditView.OnAsyncClickListener listener = new UserInfoEditView.OnAsyncClickListener() {

		public void AsyncSubmit(UserInfo info) {
			try {
				AsyncSubmitUserInfo(info);
			} catch (NetworkException e) {
				AsyncSignIn();
			}
		}
	};

	private String getProfileText() {
		return getString(R.string.profile);
	}

	private String getPreferencesText() {
		return getString(R.string.preferences);
	}

	private String getSubmitText() {
		return getString(R.string.submit);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void OnRightBtnClicked() {
		try {
			AsyncSubmitUserInfo(user.getUserInfo());
		} catch (NetworkException e) {
			AsyncSignIn();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnLeftBtnClicked()
	 */
	@Override
	protected void OnLeftBtnClicked() {
		backward(null);
	}

}
