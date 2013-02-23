/**
 * 
 */
package com.photoshare.service.share.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.photoshare.exception.NetworkError;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.share.ShareBean;
import com.photoshare.service.share.ShareType;
import com.photoshare.service.share.SinaWeiboToken;
import com.photoshare.tabHost.R;
import com.renren.api.connect.android.Renren;
import com.renren.api.connect.android.Util;
import com.renren.api.connect.android.exception.RenrenAuthError;
import com.renren.api.connect.android.view.RenrenAuthListener;
import com.weibo.sdk.android.Oauth2AccessToken;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.sso.SsoHandler;
import com.weibo.sdk.android.util.Utility;

/**
 * @author Aron
 * 
 */
public class SharePreferencesFragment extends BaseFragment {

	private SharePreferencesView view;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";
	private int leftBtnVisibility = View.VISIBLE;
	private int rightBtnVisibility = View.GONE;

	private Renren mRenren;

	private Weibo mWeibo;
	private SinaWeiboToken wToken;
	private SsoHandler mSsoHandler;

	public static SharePreferencesFragment newInstance(int fragmentViewId) {
		SharePreferencesFragment ss = new SharePreferencesFragment();
		ss.setFragmentViewId(fragmentViewId);
		return ss;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
	}

	/**
	 * 
	 */
	private void initViews() {
		leftBtnText = getPreferencesText();
		titlebarText = getShareText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		view = new SharePreferencesView(getActivity().findViewById(
				R.id.sharingPreferenceId), getActivity());
		view.registerListener(listener);
		view.applyView();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.sharing_preference_layout, container,
				false);
	}

	private String getShareText() {
		return getString(R.string.share);
	}

	private String getPreferencesText() {
		return getString(R.string.preferences);
	}

	private SharePreferencesView.OnAsyncItemClickListener listener = new SharePreferencesView.OnAsyncItemClickListener() {

		public void AsyncShareSettings(View view, ShareBean info) {
			ShareType type = info.getmShareType();
			switch (type) {
			case NULL:
				break;
			case RenRen:
				mRenren = new Renren(type.getApiKey(), type.getSecretKey(),
						type.getAppId(), getActivity());
				mRenren.init(getActivity());
				mRenren.authorize(getActivity(), new RenrenAuthListenerImpl());
				break;
			case SinaWeibo:
				mWeibo = Weibo.getInstance(type.getApiKey(),
						ShareBean.KEY_SINA_WEIBO_REDIRECT_URL);
				try {
					Class sso = Class
							.forName("com.weibo.sdk.android.sso.SsoHandler");
					mSsoHandler = new SsoHandler(getActivity(), mWeibo);
					mSsoHandler.authorize(new SinaWeiboAuthListener());
					
				} catch (ClassNotFoundException e) {
					mWeibo.authorize(getActivity(), new SinaWeiboAuthListener());
				}

				SinaWeiboToken.readAccessToken(getActivity());
				if (SinaWeiboToken.isSeesionValid()) {
					Weibo.isWifi = Utility.isWifi(getActivity());
					try {
						Class.forName("com.weibo.sdk.android.api.WeiboAPI");
					} catch (ClassNotFoundException e) {
						mExceptionHandler.obtainMessage(
								NetworkError.ERROR_SINA_WEIBO_AUTHORIZE)
								.sendToTarget();
					}
				} else {
//					mSsoHandler = new SsoHandler(getActivity(), mWeibo);
//					mSsoHandler.authorize(mWeiboAuthListener);
				}
				break;
			case TxWeibo:
				break;
			default:
				break;
			}
			if (info != null && info.isValid()) {

			} else {

			}
		}
	};

	class RenrenAuthListenerImpl implements RenrenAuthListener {

		public void onComplete(Bundle values) {
			Util.logger("complete");
		}

		public void onRenrenAuthError(RenrenAuthError renrenAuthError) {
			Util.logger("AuthError");
			mExceptionHandler
					.obtainMessage(NetworkError.ERROR_RENREN_AUTHORIZE)
					.sendToTarget();
		}

		public void onCancelLogin() {
			Util.logger("cancle");
			mExceptionHandler
					.obtainMessage(NetworkError.ERROR_RENREN_AUTHORIZE)
					.sendToTarget();
		}

		public void onCancelAuth(Bundle values) {
			Util.logger("AuthCancle");
			mExceptionHandler
					.obtainMessage(NetworkError.ERROR_RENREN_AUTHORIZE)
					.sendToTarget();
		}

	};

	class SinaWeiboAuthListener implements WeiboAuthListener {

		public void onWeiboException(WeiboException arg0) {
			mExceptionHandler.obtainMessage(
					NetworkError.ERROR_SINA_WEIBO_AUTHORIZE).sendToTarget();
		}

		public void onError(WeiboDialogError arg0) {
			mExceptionHandler.obtainMessage(
					NetworkError.ERROR_SINA_WEIBO_AUTHORIZE).sendToTarget();
		}

		public void onComplete(Bundle values) {
			String token = values.getString("access_token");
			String expires_in = values.getString("expires_in");
			Oauth2AccessToken wToken = SinaWeiboToken.createToken(token,
					expires_in);

			if (wToken.isSessionValid()) {
				try {
					Class.forName("com.weibo.sdk.android.api.WeiboAPI");
				} catch (ClassNotFoundException e) {
					mExceptionHandler
							.obtainMessage(NetworkError.ERROR_SINA_WEIBO_AUTHORIZE);
				}
				SinaWeiboToken.keepAccessToken(getActivity(), wToken);
			}

		}

		public void onCancel() {
			mExceptionHandler.obtainMessage(
					NetworkError.ERROR_SINA_WEIBO_AUTHORIZE).sendToTarget();
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

	private String getPreferenceSettingsFragment() {
		return getString(R.string.fpreferenceSettingsFragment);
	}

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub
		
	}

}
