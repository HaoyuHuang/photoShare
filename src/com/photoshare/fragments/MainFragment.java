/**
 * 
 */
package com.photoshare.fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.photoshare.tabHost.R;

/**
 * @author Aron
 * 
 *         MainFragment displays views for navigation of sign in and sign up.
 *         {@link MainView}
 * 
 */
public class MainFragment extends BaseFragment {

	private MainView mainView;
	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "Welcome";
	private int leftBtnVisibility = View.GONE;
	private int rightBtnVisibility = View.GONE;

	public static MainFragment newInstance(int fragmentViewId) {
		MainFragment mf = new MainFragment();
		mf.setFragmentViewId(fragmentViewId);
		return mf;
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Log.i("init", "init");
		if (!hideTitleBarView()) {
			container.addView(super.onCreateView(inflater, container,
					savedInstanceState));
		}
		return inflater.inflate(R.layout.main_layout, container, false);
	}

	@Override
	public void onDestroy() {
		Log.i("OnDestroy", "destroy");
		super.onDestroy();
	}

	@Override
	public void onPause() {
		Log.i("OnPause", "pause");
		super.onPause();
	}

	@Override
	public void onResume() {
		Log.i("OnResume", "resume");
		super.onResume();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		Log.i("OnActivityCreated", "activity");
		super.onActivityCreated(savedInstanceState);
		initTitleBar(leftBtnText, rightBtnText, titlebarText,
				leftBtnVisibility, rightBtnVisibility);
		initViews();
	}

	private void initViews() {
		Tag = getMainFragment();
		mainView = new MainView(getActivity().findViewById(R.id.mainLogLayout));
		mainView.registerListener(onLogClick);
		mainView.applyView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnRightBtnClicked()
	 */
	@Override
	protected void onRightBtnClicked(View view) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.fragments.BaseFragment#OnLeftBtnClicked()
	 */
	@Override
	protected void onLeftBtnClicked(View view) {

	}

	private MainView.OnLogClick onLogClick = new MainView.OnLogClick() {

		public void OnSignUpClick() {
			forward(getSignUpFragment(), null);
		}

		public void OnSignInClick() {
			forward(getSignInFragment(), null);
		}
	};

	private String getSignInFragment() {
		return getString(R.string.fsignInFragment);
	}

	private String getSignUpFragment() {
		return getString(R.string.fsignUpFragment);
	}

	private String getMainFragment() {
		return getString(R.string.fmainFragment);
	}

	@Override
	protected void onLoginSuccess() {
		// TODO Auto-generated method stub
		
	}

}
