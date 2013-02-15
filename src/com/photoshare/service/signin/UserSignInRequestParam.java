/**
 * 
 */
package com.photoshare.service.signin;

import android.os.Bundle;

import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.login.LoginType;
import com.photoshare.service.users.UserInfo;

/**
 * @author Aron
 * 
 */
public class UserSignInRequestParam extends RequestParam {

	@Deprecated
	public static final String METHOD = "userLogin.do";

	public static final String KEY_PWD = "pwd";
	private String name;
	private String pwd;

	private LoginType type = LoginType.SIGN_IN;

	private static final String ACTION = "/LoginAction_";

	public String getAction() {
		return ACTION + type.getTag();
	}

	@Deprecated
	public String getMethod() {
		return METHOD + "?method=" + "signIn";
	}

	public static final String FIELD_DEFAULT = UserInfo.KEY_NAME + ","
			+ KEY_PWD;

	private String fields = FIELD_DEFAULT;

	public UserSignInRequestParam(String fields) {
		this.fields = fields;
	}

	public UserSignInRequestParam(String name, String pwd) {
		this.name = name;
		this.pwd = pwd;
	}

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
		if (fields != null) {
			parameters.putString("fields", fields);
		}
		if (name != null) {
			parameters.putString(UserInfo.KEY_USER_INFO + "."
					+ UserInfo.KEY_NAME, name);
		}
		if (pwd != null) {
			parameters.putString(UserInfo.KEY_USER_INFO + "." + KEY_PWD, pwd);
		}
		return parameters;
	}

}
