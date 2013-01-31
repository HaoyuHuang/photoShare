/**
 * 
 */
package com.photoshare.service.signin.view;

import android.view.View;
import android.widget.EditText;

import com.photoshare.tabHost.R;

/**
 * @author Aron
 * 
 */
public class SignInView {

	private View baseView;
	private EditText mName;
	private EditText mPwd;

	/**
	 * @param baseView
	 */
	public SignInView(View baseView) {
		super();
		this.baseView = baseView;
	}

	public void applyView() {
		mName = (EditText) baseView.findViewById(R.id.signinName);
		mPwd = (EditText) baseView.findViewById(R.id.signinPwd);
	}

	public String getNameString() {
		return mName.getText().toString();
	}

	public String getPwdString() {
		return mPwd.getText().toString();
	}
}
