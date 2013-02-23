/**
 * 
 */
package com.photoshare.service.signin.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.photoshare.command.Command;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.tabHost.R;

/**
 * @author Aron
 * 
 */
public class SignInFragment extends BaseFragment {

	private SignInView signInView;
	private String leftBtnText;
	private String rightBtnText;
	private String titlebarText;
	private int leftBtnVisibility;
	private int rightBtnVisibility;

	public static SignInFragment newInstance(int fragmentViewId) {
		SignInFragment sf = new SignInFragment();
		sf.setFragmentViewId(fragmentViewId);
		return sf;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.signin_layout, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initViews();
	}

	private String getMainText() {
		return getString(R.string.main);
	}

	private String getSigninText() {
		return getString(R.string.signIn);
	}

	private void initViews() {
		leftBtnText = getMainText();
		rightBtnText = getSigninText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		signInView = new SignInView(getActivity().findViewById(
				R.id.signInLayoutId));
		signInView.applyView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void onRightBtnClicked() {
		AsyncSignIn(signInView.getNameString(), signInView.getPwdString());
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

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub
		Command.TabHost(getActivity());
	}

}
