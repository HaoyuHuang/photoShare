/**
 * 
 */
package com.photoshare.service.signup.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.photoshare.command.Command;
import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.service.signup.UserSignUpRequestParam;
import com.photoshare.service.signup.UserSignUpResponseBean;
import com.photoshare.tabHost.R;
import com.photoshare.utils.Format;
import com.photoshare.utils.async.AsyncUtils;

/**
 * @author Aron
 * 
 */
public class SignUpFragment extends BaseFragment {

	private SignUpView signUpView;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private int leftBtnVisibility = View.VISIBLE;
	private String titlebarText = "";
	private int rightBtnVisibility = View.VISIBLE;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.signup_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
	}

	private void initViews() {
		leftBtnText = getMainText();
		titlebarText = getSignUpText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		signUpView = new SignUpView(getActivity().findViewById(
				R.id.signupLayoutId));
		signUpView.applyView();
	}

	private String getSignUpText() {
		return getString(R.string.signUp);
	}

	private String getMainText() {
		return getString(R.string.main);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void OnRightBtnClicked() {
		AsyncSignUp();
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

	private void AsyncSignUp() {
		AsyncUtils async = AsyncUtils.getInstance();
		String mail = signUpView.getMail();
		String pwd = signUpView.getPwd();
		String name = signUpView.getName();
		String pseudoName = signUpView.getPseudoName();
		String phone = signUpView.getPhone();
		mail = "asdavewsdavewvew";
		pwd = "wdfwfef";
		name = "wefefw";
		pseudoName = "wefqqw";
		phone = "1242424";
		UserSignUpRequestParam request = new UserSignUpRequestParam();

		if (Format.isNullorEmpty(mail)) {
			return;
		}
		if (Format.isNullorEmpty(pwd)) {
			return;
		}
		request.setMail(mail);
		request.setName(name);
		request.setPhone(phone);
		request.setPseudoName(pseudoName);
		request.setPwd(pwd);

		AbstractRequestListener<UserSignUpResponseBean> listener = new AbstractRequestListener<UserSignUpResponseBean>() {

			@Override
			public void onComplete(UserSignUpResponseBean bean) {
				if (bean != null) {
					user.setUserInfo(bean.getSignupInfo());
					user.setLogging(true);
				}
				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						Command.TabHost(getActivity());
					}

				});
			}

			@Override
			public void onNetworkError(final NetworkError networkError) {

				getActivity().runOnUiThread(new Runnable() {

					public void run() {
						mExceptionHandler.obtainMessage(
								NetworkError.ERROR_SIGN_UP).sendToTarget();
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

		};
		async.SignUp(request, listener);
	}

	/**
	 * @param fragmentViewId
	 * @return
	 */
	public static SignUpFragment newInstance(int fragmentViewId) {
		SignUpFragment sf = new SignUpFragment();
		sf.setFragmentViewId(fragmentViewId);
		return sf;
	}

}
