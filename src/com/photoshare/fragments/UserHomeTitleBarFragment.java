/**
 * 
 */
package com.photoshare.fragments;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.photoshare.service.follow.FollowType;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.view.UserHomeTitleBarView;

/**
 * @author Aron
 * 
 *         UserHomeTitleBarFragment displays views contained in
 *         {@link UserHomeTitleBarView} and handle accompanied operations.
 * 
 */
public class UserHomeTitleBarFragment extends BaseFragment {

	private UserHomeTitleBarView barView;

	public static UserHomeTitleBarFragment newInstance(int fragmentViewId) {
		UserHomeTitleBarFragment uhtbf = new UserHomeTitleBarFragment();
		uhtbf.setFragmentViewId(fragmentViewId);
		return uhtbf;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.user_home_title_bar, container, false);
	}

	private void initView() {
		barView = new UserHomeTitleBarView(((View) getActivity().findViewById(
				R.id.userHomeTitlebarLayoutId)), user.getUserInfo(), async);
		barView.registerCallback(mCallback);
		barView.applyView();
	}

	private UserHomeTitleBarView.ICallback mCallback = new UserHomeTitleBarView.ICallback() {

		public void OnPhotosCntClick() {
			// do nothing
		}

		public void OnFollowingCntClick() {
			Bundle param = new Bundle();
			param.putParcelable(UserInfo.KEY_USER_INFO, user.getUserInfo());
			param.putString(UserInfo.KEY_FOLLOW_TYPE,
					FollowType.FOLLOWING.toString());
			forward(getFollowingFragment(), param);
		}

		public void OnFollowerCntClick() {
			Bundle param = new Bundle();
			param.putParcelable(UserInfo.KEY_USER_INFO, user.getUserInfo());
			param.putString(UserInfo.KEY_FOLLOW_TYPE,
					FollowType.FOLLOWER.toString());
			forward(getFollowingFragment(), param);
		}

		public void OnEditInfoClick() {
			forward(getProfileFragment(), null);
		}

		public void OnUserHeadLoaded(final ImageView imageView,
				final Drawable photo, String url) {
			getActivity().runOnUiThread(new Runnable() {

				public void run() {
					imageView.setImageDrawable(photo);
				}
			});
		}

		public void OnDefault(final ImageView imageView) {
			imageView.setImageResource(R.drawable.icon);
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
	protected void OnRightBtnClicked() {
		forward(getPreferencesSettingsFragment(), null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnLeftBtnClicked()
	 */
	@Override
	protected void OnLeftBtnClicked() {
		// do nothing
	}
}