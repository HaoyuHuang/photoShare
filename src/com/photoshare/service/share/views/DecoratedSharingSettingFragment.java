/**
 * 
 */
package com.photoshare.service.share.views;

import android.graphics.Bitmap;
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
public class DecoratedSharingSettingFragment extends BaseFragment {
	private ShareSettingView ssView;
	private ShareBean bean;
	private Bitmap photo;

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
	}

	private void initViews() {
		ssView = new ShareSettingView(getActivity().findViewById(
				R.id.personalShareSettingId), bean);
		ssView.registerListener(listener);
		ssView.applyView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.sharing_preference_layout, container,
				false);
	}

	private ShareSettingView.OnAsyncClickListener listener = new ShareSettingView.OnAsyncClickListener() {

		public void AsyncSubmit(ShareBean info) {

		}

		public void AsyncCancle(ShareBean info) {
			Bundle args = new Bundle();
			args.putParcelable(ShareBean.KEY_SHARE_BEAN, info);
			forward(getDecoratedSharing(), info.param());
		}
	};

	/**
	 * @param fragmentViewId2
	 * @param bean2
	 * @return
	 */
	public static DecoratedSharingSettingFragment newInstance(
			int fragmentViewId2, ShareBean bean2) {
		DecoratedSharingSettingFragment dssf = new DecoratedSharingSettingFragment();
		dssf.bean = bean2;
		return dssf;
	}

	private String getDecoratedSharing() {
		return getString(R.string.fdecoratedSharingPreferenceFragment);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void onRightBtnClicked(View view) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnLeftBtnClicked()
	 */
	@Override
	protected void onLeftBtnClicked(View view) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub

	}
}
