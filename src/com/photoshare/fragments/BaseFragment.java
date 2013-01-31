/**
 * 
 */
package com.photoshare.fragments;

import java.util.ArrayList;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.photoshare.command.Command;
import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.fragments.stacktrace.TraceConfig;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.PhotoType;
import com.photoshare.service.photos.views.FeedsFragment;
import com.photoshare.service.photos.views.PopularPhotosFragment;
import com.photoshare.service.signin.UserSignInRequestParam;
import com.photoshare.service.signin.UserSignInResponseBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.User;
import com.photoshare.utils.Utils;
import com.photoshare.utils.async.AsyncUtils;
import com.photoshare.view.AppTitleBarView;

/**
 * @author Aron
 * 
 */
public abstract class BaseFragment extends Fragment {
	public static final String KEY_FRAGMENT_VIEW_ID = "fragmentViewId";
	public static final String KEY_TAG = "tag";
	protected int fragmentViewId;
	protected AsyncUtils async = AsyncUtils.getInstance();
	protected User user = User.getInstance();
	private AppTitleBarView titleView;
	protected String Tag;

	public int getFragmentViewId() {
		return fragmentViewId;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	public void setFragmentViewId(int fragmentViewId) {
		Bundle bundle = new Bundle();
		bundle.putInt(KEY_FRAGMENT_VIEW_ID, fragmentViewId);
		this.setArguments(bundle);
		this.fragmentViewId = fragmentViewId;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater
				.inflate(R.layout.app_title_bar_layout, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}

	protected void initTitleBar(String leftBtnText, String rightBtnText,
			String titlebarText) {
		titleView = new AppTitleBarView(getActivity(), getActivity()
				.findViewById(R.id.appTitleBarLayoutId), leftBtnText,
				rightBtnText, titlebarText);
		titleView.registerListener(onTitleBarBtnClickedListener);
		titleView.applyView();
	}

	protected void initTitleBar(String leftBtnText, String rightBtnText,
			String titlebarText, int leftBtnVisibility, int rightBtnVisibility) {
		titleView = new AppTitleBarView(getActivity(), getActivity()
				.findViewById(R.id.appTitleBarLayoutId), leftBtnText,
				rightBtnText, titlebarText, leftBtnVisibility,
				rightBtnVisibility);
		titleView.registerListener(onTitleBarBtnClickedListener);

		titleView.applyView();
	}

	protected void setTitleBarText(String leftBtnText, String rightBtnText,
			String titlebarText) {
		if (titleView == null) {
			throw new IllegalStateException("title hasn't been initialized!");
		}
		titleView.setLeftBtnText(leftBtnText);
		titleView.setRightBtnText(rightBtnText);
		titleView.setTitlebarText(titlebarText);
	}

	protected void AsyncSignIn() {
		UserSignInRequestParam param = new UserSignInRequestParam(
				user.getMail(), user.getPwd());
		AbstractRequestListener<UserSignInResponseBean> listener = new AbstractRequestListener<UserSignInResponseBean>() {

			@Override
			public void onNetworkError(NetworkError networkError) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						mExceptionHandler.obtainMessage(
								NetworkError.ERROR_SIGN_IN).sendToTarget();
					}

				});
			}

			@Override
			public void onFault(Throwable fault) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						mExceptionHandler.obtainMessage(
								NetworkError.ERROR_NETWORK).sendToTarget();
					}

				});
			}

			@Override
			public void onComplete(UserSignInResponseBean bean) {
				if (bean != null) {
					UserInfo info = bean.getUserInfo();
					if (info != null) {
						user.setUserInfo(info);
						user.setLogging(true);
					}
				}
				getActivity().runOnUiThread(new Runnable() {

					public void run() {

					}

				});
			}
		};
		async.SignIn(param, listener);
	}

	protected void AsyncSignIn(final String mail, final String pwd) {
		UserSignInRequestParam param = new UserSignInRequestParam(mail, pwd);
		AbstractRequestListener<UserSignInResponseBean> listener = new AbstractRequestListener<UserSignInResponseBean>() {

			@Override
			public void onNetworkError(NetworkError networkError) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						mExceptionHandler.obtainMessage(
								NetworkError.ERROR_SIGN_IN).sendToTarget();
					}

				});
			}

			@Override
			public void onFault(Throwable fault) {
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						mExceptionHandler.obtainMessage(
								NetworkError.ERROR_NETWORK).sendToTarget();

					}

				});
			}

			@Override
			public void onComplete(UserSignInResponseBean bean) {
				if (bean != null) {
					UserInfo info = bean.getUserInfo();
					if (info != null) {
						user.setMail(mail);
						user.setPwd(pwd);
						user.setUserInfo(info);
						user.setLogging(true);
					}
				}
				getActivity().runOnUiThread(new Runnable() {

					public void run() {

					}

				});
			}
		};
		async.SignIn(param, listener);
	}

	/**
	 * 
	 */
	protected abstract void OnRightBtnClicked();

	/**
	 * 
	 */
	protected abstract void OnLeftBtnClicked();

	protected final Handler mExceptionHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			String error = "";
			switch (msg.what) {
			case NetworkError.ERROR_SIGN_IN_NULL:
				error = getSignInNullError();
				break;
			case NetworkError.ERROR_CODE_ILLEGAL_PARAMETER:
				break;
			case NetworkError.ERROR_CODE_LOG_ERROR:
				break;
			case NetworkError.ERROR_CODE_NULL_PARAMETER:
				break;
			case NetworkError.ERROR_CODE_PARAMETER_EXTENDS_LIMIT:
				break;
			case NetworkError.ERROR_CODE_UNABLE_PARSE_RESPONSE:
				break;
			case NetworkError.ERROR_CODE_UNKNOWN_ERROR:
				break;
			case NetworkError.ERROR_COMMENT:
				error = getCommentError();
				break;
			case NetworkError.ERROR_EDIT_PROFILE:
				error = getEditProfileError();
				break;
			case NetworkError.ERROR_FOLLOW:
				error = getFollowError();
				break;
			case NetworkError.ERROR_MAIL_NULL:
				error = getMailError();
				break;
			case NetworkError.ERROR_NAME_NULL:
				error = getNameError();
				break;
			case NetworkError.ERROR_PHOTO:
				error = getPhotoError();
				break;
			case NetworkError.ERROR_PSEUDO_NAME_NULL:
				error = getPseudoNameError();
				break;
			case NetworkError.ERROR_PWD_NULL:
				error = getPwdError();
				break;
			case NetworkError.ERROR_REFRESH_DATA:
				error = getRefreshError();
				break;
			case NetworkError.ERROR_SIGN_IN:
				error = getSignInError();
				break;
			case NetworkError.ERROR_SIGN_UP:
				error = getSignUpError();
				break;
			case NetworkError.ERROR_SIGN_UP_MAIL:
				error = getSignUpMailExistError();
				break;
			case NetworkError.ERROR_LIKE:
				error = getLikeError();
				break;
			case NetworkError.ERROR_NETWORK:
				error = getNetworkError();
				break;
			case NetworkError.ERROR_RENREN_AUTHORIZE:
				error = getRenRenAuthorizeError();
				break;
			case NetworkError.ERROR_RENREN_PUBLISH_PHOTO:
				error = getRenRenPublishError();
				break;
			case NetworkError.ERROR_SINA_WEIBO_AUTHORIZE:
				error = getSinaWeiboAuthorizeError();
				break;
			case NetworkError.ERROR_SINA_WEIBO_PUBLISH_PHOTO:
				error = getSinaWeiboPublishError();
				break;
			case NetworkError.ERROR_TX_WEIBO_AUTHORIZE:
				error = getTxWeiboAuthorizeError();
				break;
			case NetworkError.ERROR_TX_WEIBO_PUBLISH_PHOTO:
				error = getTxWeiboPublishError();
				break;
			default:
				error = getError();
				super.handleMessage(msg);
			}
			if (!"".equals(error)) {
				displayErrorMsg(error);
			}
		}
	};

	private String getRefreshError() {
		return getString(R.string.erefreshError);
	}

	private String getSignInError() {
		return getString(R.string.esignInError);
	}

	private String getSignInNullError() {
		return getString(R.string.esignIn);
	}

	private String getSignUpError() {
		return getString(R.string.esignUpError);
	}

	private String getSignUpMailExistError() {
		return getString(R.string.esignUpMailExistError);
	}

	private String getNameError() {
		return getString(R.string.enameError);
	}

	private String getMailError() {
		return getString(R.string.emailError);
	}

	private String getPwdError() {
		return getString(R.string.epwdError);
	}

	private String getPseudoNameError() {
		return getString(R.string.epseudoNameError);
	}

	private String getPhotoError() {
		return getString(R.string.ephotoError);
	}

	private String getLikeError() {
		return getString(R.string.elikeError);
	}

	private String getFollowError() {
		return getString(R.string.efollowError);
	}

	private String getCommentError() {
		return getString(R.string.ecommentError);
	}

	private String getEditProfileError() {
		return getString(R.string.eeditProfileError);
	}

	private String getError() {
		return getString(R.string.eError);
	}

	private String getNetworkError() {
		return getString(R.string.eNetworkError);
	}

	private String getRenRenAuthorizeError() {
		return getString(R.string.error_renren_authorize);
	}

	private String getRenRenPublishError() {
		return getString(R.string.error_renren_publish_photo);
	}

	private String getSinaWeiboAuthorizeError() {
		return getString(R.string.error_sina_weibo_authorize);
	}

	private String getSinaWeiboPublishError() {
		return getString(R.string.error_sina_weibo_publish_photo);
	}

	private String getTxWeiboAuthorizeError() {
		return getString(R.string.error_tx_weibo_authorize);
	}

	private String getTxWeiboPublishError() {
		return getString(R.string.error_tx_weibo_publish_photo);
	}

	protected String getRefreshTicker() {
		return getString(R.string.nRefreshTicker);
	}

	protected String getRefreshTag() {
		return getString(R.string.nRefreshTag);
	}

	protected String getSuccessTicker() {
		return getString(R.string.nSuccessTicker);
	}

	protected String getSuccessTag() {
		return getString(R.string.nSuccessTag);
	}

	private void displayErrorMsg(final String error) {
		getActivity().runOnUiThread(new Runnable() {

			public void run() {
				if (titleView != null) {
					titleView.displayErrorView(error);
				}
			}
		});
	}

	public String getCanonicalTag() {
		return Tag;
	}

	public void setCanonicalTag(String canonicalTag) {
		this.Tag = canonicalTag;
	}

	protected void ShowOtherHomeTitleBarFragment(int fragmentViewId,
			UserInfo info) {
		OtherHomeTitleBarFragment ohtbf = OtherHomeTitleBarFragment
				.newInstance(fragmentViewId);
		Bundle args = new Bundle();
		args.putParcelable(UserInfo.KEY_USER_INFO, info);
		ohtbf.setArguments(args);
		persist(fragmentViewId, ohtbf);
	}

	protected void ShowUserHomeTitleBarFragment(int fragmentViewId) {
		UserHomeTitleBarFragment uhtbf = UserHomeTitleBarFragment
				.newInstance(fragmentViewId);
		persist(fragmentViewId, uhtbf);
	}

	protected void ShowPhotoBarFragment(int fragmentViewId, PhotoType type,
			UserInfo info, ArrayList<PhotoBean> photos) {
		PhotoBarFragment pbf = PhotoBarFragment.newInstance(fragmentViewId);
		Bundle args = new Bundle();
		args.putParcelable(UserInfo.KEY_USER_INFO, info);
		args.putString(PhotoBean.KEY_PHOTO_TYPE, type.toString());
		args.putParcelableArrayList(PhotoBean.KEY_PHOTOS, photos);
		pbf.setArguments(args);
		persist(fragmentViewId, pbf);
	}

	protected void ShowPopularFragment(int fragmentViewId, Bundle args) {
		PopularPhotosFragment ppf = PopularPhotosFragment
				.newInstance(fragmentViewId);
		ppf.setArguments(args);
		persist(fragmentViewId, ppf);
	}

	protected void ShowFeedsFragment(int fragmentViewId, Bundle args) {
		FeedsFragment ff = FeedsFragment.newInstance(fragmentViewId);
		ff.setArguments(args);
		persist(fragmentViewId, ff);
	}

	private void persist(int fragmentViewId, BaseFragment ff) {
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(fragmentViewId, ff);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}

	/**
	 * Process arguments. Set the result to be true if the arguments contains
	 * backwards action; otherwise, set it false.
	 * {@link TraceConfig#getTrackBackward()}
	 * 
	 * @return result
	 */
	protected boolean processArguments() {
		Bundle params = getArguments();
		if (params != null) {
			Utils.logger("Fragment ----------- backward"
					+ params.getBoolean(TraceConfig.getTrackBackward()));
			if (params.containsKey(TraceConfig.getTrackBackward())) {
				Utils.logger("Fragment ----------- backward"
						+ params.getBoolean(TraceConfig.getTrackBackward()));
				return params.getBoolean(TraceConfig.getTrackBackward());
			}
		}
		return false;
	}

	protected void forward(String invokeName, Bundle args) {
		Command.forward(this, invokeName, args);
	}

	protected void backward(Bundle args) {
		Utils.logger("Back from " + this.getCanonicalTag());
		Command.backward(this, args);
	}

	private AppTitleBarView.OnTitleBarBtnClickedListener onTitleBarBtnClickedListener = new AppTitleBarView.OnTitleBarBtnClickedListener() {

		public void OnRightBtnClick() {
			OnRightBtnClicked();
		}

		public void OnLeftBtnClick() {
			OnLeftBtnClicked();
		}
	};
}
