/**
 * 
 */
package com.photoshare.fragments;

import java.util.ArrayList;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.photoshare.fragments.stacktrace.TraceConfig;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.PhotoType;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.view.PhotoBarView;

/**
 * @author Aron
 * 
 *         PhotoBarFragment displays the view and handle accompanied operations.
 *         {@link PhotoBarView}
 * 
 */
public class PhotoBarFragment extends BaseFragment {
	private PhotoBarView photoBar;
	private UserInfo userInfo;
	private PhotoType type;
	private ArrayList<PhotoBean> photos;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(PhotoBean.KEY_PHOTOS)) {
				photos = savedInstanceState
						.getParcelableArrayList(PhotoBean.KEY_PHOTOS);
			}
		}
		super.onActivityCreated(savedInstanceState);
		initView();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelableArrayList(PhotoBean.KEY_PHOTOS, photos);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.show_photo_bar, container, false);
	}

	public static PhotoBarFragment newInstance(int fragmentViewId) {
		PhotoBarFragment pf = new PhotoBarFragment();
		pf.setFragmentViewId(fragmentViewId);
		return pf;
	}

	public PhotoType getType() {
		return type;
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	public int getLayoutHolderId() {
		return R.id.showPhotoFrameLayoutHolderId;
	}

	private void initView() {
		Bundle bundle = getArguments();
		if (bundle != null) {
			if (bundle.containsKey(UserInfo.KEY_USER_INFO)) {
				userInfo = bundle.getParcelable(UserInfo.KEY_USER_INFO);
			}
			if (bundle.containsKey(PhotoBean.KEY_PHOTO_TYPE)) {
				type = PhotoType.SWITCH(bundle
						.getString(PhotoBean.KEY_PHOTO_TYPE));
			}
			if (bundle.containsKey(PhotoBean.KEY_PHOTOS)) {
				photos = bundle.getParcelableArrayList(PhotoBean.KEY_PHOTOS);
			}
		}
		photoBar = new PhotoBarView(getActivity().findViewById(
				R.id.showPhotoBarId), type, userInfo);
		photoBar.registerListener(ShowPhotoActionListener);
		photoBar.applyView();
		ShowPopularView();
	}
	
	private void ShowPopularView() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(UserInfo.KEY_USER_INFO, userInfo);
		bundle.putString(PhotoBean.KEY_PHOTO_TYPE, type.toString());
		bundle.putParcelableArrayList(PhotoBean.KEY_PHOTOS, photos);
		bundle.putBoolean(TraceConfig.getTrackBackward(), true);
		ShowPopularFragment(getLayoutHolderId(), bundle);
	}
	
	private void ShowFeedsView() {
		Bundle bundle = new Bundle();
		bundle.putParcelable(UserInfo.KEY_USER_INFO, userInfo);
		bundle.putString(PhotoBean.KEY_PHOTO_TYPE,
				type.toString());
		if (photos != null) {
			bundle.putParcelableArrayList(PhotoBean.KEY_PHOTOS, photos);
		}
		bundle.putBoolean(TraceConfig.getTrackBackward(), true);
		ShowFeedsFragment(getLayoutHolderId(), bundle);
	}

	private PhotoBarView.OnActionListener ShowPhotoActionListener = new PhotoBarView.OnActionListener() {

		public void ShowProfile(UserInfo info) {
			forward(getProfileFragment(), info.params());
		}
		
		public void ShowPopularItems() {
			ShowPopularView();
		}

		
		public void ShowFeedsItem() {
			ShowFeedsView();
		}

	};

	private String getProfileFragment() {
		return getString(R.string.fpersonalProfileFragment);
	}

	private String getPopularFragment() {
		return getString(R.string.fpopularPhotosFragment);
	}

	private String getFeedsFragment() {
		return getString(R.string.ffeedsFragment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void onRightBtnClicked() {
		// do nothing
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnLeftBtnClicked()
	 */
	@Override
	protected void onLeftBtnClicked() {
		// do nothing
	}

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub
		
	}

}
