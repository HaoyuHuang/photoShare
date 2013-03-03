/**
 * 
 */
package com.photoshare.service.users.views;

import java.util.ArrayList;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.photoshare.fragments.BaseFragment;
import com.photoshare.fragments.stacktrace.TraceConfig;
import com.photoshare.fragments.stacktrace.TracePhase;
import com.photoshare.fragments.stacktrace.TraceStack;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.PhotoType;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.renren.api.connect.android.Util;

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
	private int leftBtnVisibility = View.VISIBLE;
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

	private void initUserHomeTitleBar() {
		rightBtnText = getSettingsText();
		titlebarText = getHomeText();
		leftBtnVisibility = View.GONE;
		rightBtnVisibility = View.VISIBLE;
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		Util.logger("ShowUserHomeTitleBarFragment");
		Bundle args = new Bundle();
		args.putInt(KEY_WRAPPED_ID, R.id.userHomeWrapperId);
		args.putIntArray(KEY_WRAPPED_ID, new int[] { R.id.userHomeTitleBarId,
				R.id.userHomeShowPhotoBarId });
		System.out
				.println("-------------------------------------------info != null");
		ShowUserHomeTitleBarFragment(R.id.userHomeTitleBarId, args);
	}

	private void initOtherHomeTitleBar() {
		leftBtnText = getBackText();
		titlebarText = getHomeText();
		leftBtnVisibility = View.VISIBLE;
		rightBtnVisibility = View.GONE;
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		Util.logger("ShowUserHomeTitleBarFragment");
		Bundle args = new Bundle();
		args.putInt(KEY_WRAPPED_ID, R.id.userHomeWrapperId);
		args.putIntArray(KEY_WRAPPED_ID, new int[] { R.id.userHomeTitleBarId,
				R.id.userHomeShowPhotoBarId });
		args.putParcelable(UserInfo.KEY_USER_INFO, info);
		System.out
				.println("-------------------------------------------info == null");
		ShowOtherHomeTitleBarFragment(R.id.userHomeTitleBarId, args);
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
		PhotoType type = PhotoType.MyPhotos;

		if (user.isCurrentUser(info)) {
			initUserHomeTitleBar();
		} else {
			initOtherHomeTitleBar();
		}
		Bundle args = new Bundle();
		args.putParcelable(UserInfo.KEY_USER_INFO, info);
		args.putParcelableArrayList(PhotoBean.KEY_PHOTOS, photos);
		args.putString(PhotoBean.KEY_PHOTO_TYPE, PhotoType.MyPhotos.toString());
		args.putBoolean(TraceConfig.getTrackBackward(), true);
		args.putInt(KEY_WRAPPED_ID, R.id.userHomeWrapperId);
		args.putIntArray(KEY_WRAPPED_ID, new int[] { R.id.userHomeTitleBarId,
				R.id.userHomeShowPhotoBarId });
		ShowPopularFragment(R.id.userHomeShowPhotoBarId, args);
		// ShowPhotoBarFragment(R.id.userHomeShowPhotoBarId, PhotoType.MyPhotos,
		// info, photos);
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

	private String getBackText() {
		return getString(R.string.back);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("UserHomeFragment", "titleBarCreated");
		Bundle bundle = getArguments();
		if (bundle != null) {
			if (bundle.containsKey(UserInfo.KEY_USER_INFO)) {
				info = bundle.getParcelable(UserInfo.KEY_USER_INFO);
			}
		}
		// 不是返回Action, 是当前用户，并且是在最后一个phase时加入导航栏
		if (!processArguments()
				&& user.isCurrentUser(info)
				&& TraceStack.getInstance().getCurrentPhase()
						.equals(TracePhase.HOME_PAGE)) {
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
	protected void onRightBtnClicked() {
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
	protected void onLeftBtnClicked() {
		// do nothing
		backward(getArguments());
	}

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub

	}

}
