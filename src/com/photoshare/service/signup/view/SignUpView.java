/**
 * 
 */
package com.photoshare.service.signup.view;

import android.view.View;
import android.widget.EditText;

import com.photoshare.tabHost.R;

/**
 * @author czj_yy
 * 
 */
public class SignUpView {

	private View baseView;
	private EditText mMail;
	private EditText mPwd;
	private EditText mName;
	private EditText mPseudoName;
	private EditText mPhone;

	/**
	 * @param baseView
	 */
	public SignUpView(View baseView) {
		super();
		this.baseView = baseView;
	}

	public void applyView() {
		mMail = (EditText) baseView.findViewById(R.id.signupMail);
		mPwd = (EditText) baseView.findViewById(R.id.signupPwd);
		mName = (EditText) baseView.findViewById(R.id.signupName);
		mPseudoName = (EditText) baseView.findViewById(R.id.signupPseudoName);
		mPhone = (EditText) baseView.findViewById(R.id.signupPhone);
	}

	public String getMail() {
		return mMail.getText().toString();
	}

	public String getPwd() {
		return mPwd.getText().toString();
	}

	public String getName() {
		return mName.getText().toString();
	}

	public String getPseudoName() {
		return mPseudoName.getText().toString();
	}

	public String getPhone() {
		return mPhone.getText().toString();
	}
}
