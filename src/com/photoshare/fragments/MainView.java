/**
 * 
 */
package com.photoshare.fragments;

import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.photoshare.tabHost.R;

/**
 * @author Aron
 * 
 *         The MainView dispatches actions of sign in and sign up, respectively.
 * 
 */
public class MainView {

	private Button mSignUpView;
	private Button mSignInView;
	private View baseView;

	/**
	 * @param baseView
	 */
	public MainView(View baseView) {
		super();
		this.baseView = baseView;
	}

	public void applyView() {
		mSignInView = (Button) baseView.findViewById(R.id.mainSignIn);
		mSignUpView = (Button) baseView.findViewById(R.id.mainSignUp);

		mSignInView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (onLogClick != null) {
					onLogClick.OnSignInClick();
				}
			}
		});

		mSignUpView.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (onLogClick != null) {
					onLogClick.OnSignUpClick();
				}
			}
		});

	}

	private OnLogClick onLogClick;

	public void registerListener(OnLogClick listener) {
		this.onLogClick = listener;
	}

	public interface OnLogClick {
		public void OnSignInClick();

		public void OnSignUpClick();
	}

}
