/**
 * 
 */
package com.photoshare.service.signup;

import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.photoshare.common.ResponseBean;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.users.UserInfo;

/**
 * @author czj_yy
 * 
 */
public class UserSignUpResponseBean extends ResponseBean {

	private UserInfo signupInfo;

	/**
	 * @param response
	 */
	public UserSignUpResponseBean(String response) {
		super(response);
		// TODO Auto-generated constructor stub
		Log.i("jsonResponse", response);
		if (response == null)
			return;
		try {
			JSONObject obj = new JSONObject(response);
			if (obj != null) {
				signupInfo = new UserInfo();
				signupInfo.parse(obj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NetworkException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return signupInfo.toString();
	}

	public UserInfo getSignupInfo() {
		return signupInfo;
	}

}
