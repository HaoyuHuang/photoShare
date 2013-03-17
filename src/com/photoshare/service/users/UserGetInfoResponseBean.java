package com.photoshare.service.users;

import org.json.JSONException;
import org.json.JSONObject;

import com.photoshare.common.ResponseBean;
import com.photoshare.exception.NetworkException;
import com.renren.api.connect.android.exception.RenrenException;

public class UserGetInfoResponseBean extends ResponseBean {

	/**
	 * 构造UsersGetInfoResponseBean对象
	 * 
	 * @param response
	 *            返回的数据 json格式
	 * @throws RenrenException
	 */
	public UserGetInfoResponseBean(String response) {
		super(response);
		if (response == null) {
			return;
		}

		try {
			JSONObject obj = new JSONObject(response);
			JSONObject json = obj.optJSONObject(UserInfo.KEY_USER_INFO);
			if (json != null) {
				user = new UserInfo();
				user.parse(json);
				System.out.println(user);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NetworkException e) {
			e.printStackTrace();
		}
	}

	/**
	 * user数组
	 */
	private UserInfo user;

	public UserInfo getUserInfo() {
		return user;
	}

	@Override
	public String toString() {
		return user.toString();
	}

}
