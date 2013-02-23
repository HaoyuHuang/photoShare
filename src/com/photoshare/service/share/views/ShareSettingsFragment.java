/**
 * 
 */
package com.photoshare.service.share.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.share.ShareBean;
import com.photoshare.tabHost.R;

/**
 * @author Aron
 * 
 */
@Deprecated
public class ShareSettingsFragment extends BaseFragment {

	private ShareSettingView view;
	private ShareBean bean;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";
	private int leftBtnVisibility = View.VISIBLE;
	private int rightBtnVisibility = View.VISIBLE;

	public static ShareSettingsFragment newInstance(int fragmentViewId) {
		ShareSettingsFragment ss = new ShareSettingsFragment();
		ss.setFragmentViewId(fragmentViewId);
		return ss;
	}

	public ShareBean getBean() {
		return bean;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			bean = savedInstanceState.getParcelable(bean.getmShareType()
					.toString());
		}
		super.onActivityCreated(savedInstanceState);
		initViews();
	}

	/**
	 * 
	 */
	private void initViews() {
		leftBtnText = getShareText();
		if (bean != null) {
			titlebarText = bean.getmShareType().toString();
		} else {
			titlebarText = getSettingsText();
		}
		rightBtnText = getSubmitText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		view = new ShareSettingView(getActivity().findViewById(
				R.id.personalShareSettingId), bean);
		view.registerListener(listener);
		view.applyView();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelable(bean.getmShareType().toString(), bean);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.share_settings_layout, container,
				false);
	}

	private String getShareText() {
		return getString(R.string.share);
	}

	private String getSettingsText() {
		return getString(R.string.settings);
	}

	private String getSubmitText() {
		return getString(R.string.submit);
	}

	private String getSharePreferencesFragment() {
		return getString(R.string.fsharePreferenceFragment);
	}

	private ShareSettingView.OnAsyncClickListener listener = new ShareSettingView.OnAsyncClickListener() {

		public void AsyncSubmit(ShareBean info) {

		}

		public void AsyncCancle(ShareBean info) {

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
		forward(getSharePreferencesFragment(), null);
	}

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub
		
	}

}
