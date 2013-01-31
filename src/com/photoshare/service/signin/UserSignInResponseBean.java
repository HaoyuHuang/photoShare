/**
 * 
 */
package com.photoshare.service.signin;

import org.json.JSONException;
import org.json.JSONObject;

import com.photoshare.common.ResponseBean;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.users.UserInfo;

/**
 * @author czj_yy
 * 
 */
public class UserSignInResponseBean extends ResponseBean {

	private UserInfo userInfo;

	/**
	 * @param response
	 */
	public UserSignInResponseBean(String response) {
		super(response);
		// TODO Auto-generated constructor stub
		if (response == null)
			return;
		try {
			JSONObject obj = new JSONObject(response);
			if (obj != null) {
				userInfo = new UserInfo();
				userInfo.parse(obj);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NetworkException e) {
			e.printStackTrace();
		}
	}

	public UserInfo getUserInfo() {
		return userInfo;
	}

	@Override
	public String toString() {
		return userInfo.toString();
	}

}
