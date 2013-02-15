/**
 * 
 */
package com.photoshare.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.PhotoType;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;

/**
 * @author Aron
 * 
 *         查看用户的详细信息
 * 
 */
public class UserHomeFragment extends BaseFragment {

	private UserInfo info;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";
	private int leftBtnVisibility = View.GONE;
	private int rightBtnVisibility = View.VISIBLE;

	public static UserHomeFragment newInstance(int fragmentViewId) {
		UserHomeFragment uh = new UserHomeFragment();
		uh.setFragmentViewId(fragmentViewId);
		return uh;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(UserInfo.KEY_USER_INFO)) {
				info = savedInstanceState.getParcelable(UserInfo.KEY_USER_INFO);
			}
		}
		super.onActivityCreated(savedInstanceState);
		initViews();
	}

	private void initViews() {
		ArrayList<PhotoBean> photos = null;
		Bundle bundle = getArguments();
		if (bundle != null) {
			if (bundle.containsKey(UserInfo.KEY_USER_INFO)) {
				info = bundle.getParcelable(UserInfo.KEY_USER_INFO);
			}
			if (bundle.containsKey(PhotoBean.KEY_PHOTOS)) {
				photos = bundle.getParcelableArrayList(PhotoBean.KEY_PHOTOS);
			}
		}
		rightBtnText = getSettingsText();
		titlebarText = getHomeText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		PhotoType type = PhotoType.MyPhotos;

		if (info != null) {
			ShowOtherHomeTitleBarFragment(R.id.userHomeTitleBarId, info);
		} else {
			ShowUserHomeTitleBarFragment(R.id.userHomeTitleBarId);
		}
		ShowPhotoBarFragment(R.id.userHomeShowPhotoBarId, PhotoType.MyPhotos,
				info, photos);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelable(UserInfo.KEY_USER_INFO, info);
		}
		super.onSaveInstanceState(outState);
	}

	private String getSettingsText() {
		return getString(R.string.settings);
	}

	private String getHomeText() {
		return getString(R.string.home);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("UserHomeFragment", "titleBarCreated");
		if (!processArguments()) {
			container.addView(super.onCreateView(inflater, container,
					savedInstanceState));
		}
		return inflater.inflate(R.layout.user_home_layout, container, false);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void OnRightBtnClicked() {
		Bundle args = new Bundle();
		args.putParcelable(UserInfo.KEY_USER_INFO, info);
		forward(getPreferenceSettingsFragment(), args);
	}

	private String getPreferenceSettingsFragment() {
		return getString(R.string.fpreferenceSettingsFragment);
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
