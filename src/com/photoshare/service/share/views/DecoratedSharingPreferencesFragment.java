/**
 * 
 */
package com.photoshare.service.share.views;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.photoshare.exception.NetworkError;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.ShareHelper;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.share.ShareBean;
import com.photoshare.service.share.ShareType;
import com.photoshare.service.share.SinaWeiboToken;
import com.photoshare.tabHost.R;
import com.photoshare.utils.Utils;
import com.photoshare.view.NotificationDisplayer;
import com.renren.api.connect.android.Renren;
import com.renren.api.connect.android.common.AbstractRequestListener;
import com.renren.api.connect.android.exception.RenrenAuthError;
import com.renren.api.connect.android.exception.RenrenError;
import com.renren.api.connect.android.photos.PhotoUploadResponseBean;
import com.renren.api.connect.android.view.RenrenAuthListener;
import com.weibo.sdk.android.Weibo;
import com.weibo.sdk.android.WeiboAuthListener;
import com.weibo.sdk.android.WeiboDialogError;
import com.weibo.sdk.android.WeiboException;
import com.weibo.sdk.android.api.StatusesAPI;
import com.weibo.sdk.android.net.RequestListener;
import com.weibo.sdk.android.sso.SsoHandler;
import com.weibo.sdk.android.util.Utility;

/**
 * @author Aron
 * 
 */
public class DecoratedSharingPreferencesFragment extends BaseFragment {
	private SharePreferencesView view;
	private Bitmap photo;
	private String caption = "";
	private Renren mRenren;

	private Weibo mWeibo;
	private SinaWeiboToken wToken;
	private SsoHandler mSsoHandler;

	private NotificationDisplayer mNotificationDisplayer;

	public static DecoratedSharingPreferencesFragment newInstance(
			int fragmentViewId) {
		DecoratedSharingPreferencesFragment ss = new DecoratedSharingPreferencesFragment();
		ss.setFragmentViewId(fragmentViewId);
		return ss;
	}

	private String getDecoratedSharingPreferencesFragment() {
		return getString(R.string.fdecoratedSharingPreferenceFragment);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(PhotoBean.KEY_PHOTO)) {
				photo = savedInstanceState.getParcelable(PhotoBean.KEY_PHOTO);
			}
			if (savedInstanceState.containsKey(PhotoBean.KEY_CAPTION)) {
				caption = savedInstanceState.getString(PhotoBean.KEY_CAPTION);
			}
		}
		super.onActivityCreated(savedInstanceState);
		Bundle params = getArguments();
		if (params != null) {
			if (params.containsKey(PhotoBean.KEY_PHOTO)) {
				photo = params.getParcelable(PhotoBean.KEY_PHOTO);
			}
			if (params.containsKey(PhotoBean.KEY_CAPTION)) {
				caption = params.getString(PhotoBean.KEY_CAPTION);
			}
		}
		initViews();
	}

	/**
	 * 
	 */
	private void initViews() {
		mNotificationDisplayer = new NotificationDisplayer.NotificationBuilder()
				.Context(getActivity()).Ticker(getSuccessTicker()).build();
		view = new SharePreferencesView(getActivity().findViewById(
				R.id.sharingPreferenceId), getActivity());
		view.registerListener(listener);
		view.applyView();
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelable(PhotoBean.KEY_PHOTO, photo);
			outState.putString(PhotoBean.KEY_CAPTION, caption);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.sharing_preference_layout, container,
				false);
	}

	private String getSinaWeiboTag() {
		return getString(R.string.nSinaWeibo);
	}

	private String getRenrenTag() {
		return getString(R.string.nRenren);
	}

	private String getTxWeiboTag() {
		return getString(R.string.nTxWeibo);
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
				mRenren.authorize(getActivity(), null, mRenrenAuthListener,
						type.getType());
				mNotificationDisplayer.setTag(getRenrenTag());
				break;
			case SinaWeibo:
				wToken = SinaWeiboToken.getInstance();
				wToken.readAccessToken(getActivity());
				mNotificationDisplayer.setTag(getSinaWeiboTag());
				if (wToken.isTokenValid()) {
					Weibo.isWifi = Utility.isWifi(getActivity());
					try {
						Class.forName("com.weibo.sdk.android.api.WeiboAPI");
					} catch (ClassNotFoundException e) {
						mExceptionHandler.obtainMessage(
								NetworkError.ERROR_SINA_WEIBO_AUTHORIZE)
								.sendToTarget();
					}
				} else {
					mSsoHandler = new SsoHandler(getActivity(), mWeibo);
					mSsoHandler.authorize(mWeiboAuthListener);
				}
				break;
			case TxWeibo:
				mNotificationDisplayer.setTag(getTxWeiboTag());
				break;
			default:
				break;
			}
			if (info != null && info.isValid()) {

			} else {

			}
		}
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (mRenren != null) {
			mRenren.authorizeCallback(requestCode, resultCode, data);
			return;
		}
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	final RenrenAuthListener mRenrenAuthListener = new RenrenAuthListener() {

		public void onComplete(Bundle values) {
			ShareHelper helper = new ShareHelper();
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			photo.compress(CompressFormat.PNG, 100, baos);
			File file = Utils.getFileFromBytes(baos.toByteArray(), "bitmap");

			AbstractRequestListener<PhotoUploadResponseBean> listener = new AbstractRequestListener<PhotoUploadResponseBean>() {

				@Override
				public void onRenrenError(RenrenError renrenError) {
					mExceptionHandler.obtainMessage(
							NetworkError.ERROR_RENREN_PUBLISH_PHOTO)
							.sendToTarget();

				}

				@Override
				public void onFault(Throwable fault) {
					mExceptionHandler.obtainMessage(
							NetworkError.ERROR_RENREN_PUBLISH_PHOTO)
							.sendToTarget();
				}

				@Override
				public void onComplete(PhotoUploadResponseBean bean) {
					// TODO Auto-generated method stub
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mNotificationDisplayer.displayNotification();
							mNotificationDisplayer.cancleNotification();
						}
					});
				}
			};
			helper.uploadPhotoToRenRen(file, caption, listener, mRenren);
		}

		public void onRenrenAuthError(RenrenAuthError renrenAuthError) {
			mExceptionHandler
					.obtainMessage(NetworkError.ERROR_RENREN_AUTHORIZE)
					.sendToTarget();
		}

		public void onCancelLogin() {
			mExceptionHandler
					.obtainMessage(NetworkError.ERROR_RENREN_AUTHORIZE)
					.sendToTarget();
		}

		public void onCancelAuth(Bundle values) {
			mExceptionHandler
					.obtainMessage(NetworkError.ERROR_RENREN_AUTHORIZE)
					.sendToTarget();
		}

	};

	final WeiboAuthListener mWeiboAuthListener = new WeiboAuthListener() {

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
			wToken = SinaWeiboToken.getInstance();
			wToken.newToken(token, expires_in);

			if (wToken.isTokenValid()) {
				try {
					Class.forName("com.weibo.sdk.android.api.WeiboAPI");
				} catch (ClassNotFoundException e) {
					mExceptionHandler
							.obtainMessage(NetworkError.ERROR_SINA_WEIBO_AUTHORIZE);
				}
				wToken.keepAccessToken(getActivity());
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			photo.compress(CompressFormat.PNG, 100, baos);
			File file = Utils.getFileFromBytes(baos.toByteArray(), "bitmap");

			final RequestListener listener = new RequestListener() {

				public void onIOException(IOException arg0) {
					mExceptionHandler.obtainMessage(
							NetworkError.ERROR_SINA_WEIBO_PUBLISH_PHOTO)
							.sendToTarget();
				}

				public void onError(WeiboException arg0) {
					mExceptionHandler.obtainMessage(
							NetworkError.ERROR_SINA_WEIBO_PUBLISH_PHOTO)
							.sendToTarget();
				}

				public void onComplete(String arg0) {
					getActivity().runOnUiThread(new Runnable() {

						public void run() {
							mNotificationDisplayer.displayNotification();
							mNotificationDisplayer.cancleNotification();
						}
					});
				}
			};

			StatusesAPI api = new StatusesAPI(wToken.getAccessToken());
			if (file != null) {
				api.upload(caption, file.getAbsolutePath(), "90.0", "90.0",
						listener);
			} else {
				caption += "";
				api.update(caption, "90.0", "90.0", listener);
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
	protected void OnRightBtnClicked() {

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
