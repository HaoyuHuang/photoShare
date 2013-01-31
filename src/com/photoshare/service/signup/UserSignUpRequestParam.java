/**
 * 
 */
package com.photoshare.service.signup;

import android.os.Bundle;

import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.login.LoginType;
import com.photoshare.service.users.UserInfo;

/**
 * @author Aron
 * 
 */
public class UserSignUpRequestParam extends RequestParam {

	public static final String KEY_PWD = "pwd";

	@Deprecated
	private static final String METHOD = "signUp.do";

	private String mail;
	private String pwd;
	private String name;
	private String pseudoName;
	private String phone;

	private LoginType type = LoginType.SIGN_UP;

	private static final String ACTION = "/LoginAction_";

	public String getAction() {
		return ACTION + type.getTag();
	}

	@Deprecated
	public String getMethod() {
		return "signUp.do";
	}

	public static final String FIELD_DEFAULT = UserInfo.KEY_NAME + ","
			+ KEY_PWD;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.cgema.common.RequestParam#getParams()
	 */
	@Override
	public Bundle getParams() throws NetworkException {
		// TODO Auto-generated method stub
		Bundle parameters = new Bundle();
		parameters.putString("method", METHOD);
		parameters.putString(UserInfo.KEY_USER_INFO + "." + UserInfo.KEY_MAIL,
				mail);
		parameters.putString(UserInfo.KEY_USER_INFO + "." + KEY_PWD, pwd);
		parameters.putString(UserInfo.KEY_USER_INFO + "." + UserInfo.KEY_NAME,
				name);
		parameters.putString(UserInfo.KEY_USER_INFO + "."
				+ UserInfo.KEY_PHONE_NUMBER, phone);
		parameters.putString(UserInfo.KEY_USER_INFO + "."
				+ UserInfo.KEY_PSEUDO_NAME, pseudoName);
		return parameters;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPseudoName(String pseudoName) {
		this.pseudoName = pseudoName;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setType(LoginType type) {
		this.type = type;
	}

}
