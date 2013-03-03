/**
 * 
 */
package com.photoshare.service.users;

import org.json.JSONException;
import org.json.JSONObject;

import com.photoshare.common.ResponseBean;

/**
 * @author czj_yy
 * 
 */
public class UserPrivacyResponseBean extends ResponseBean {

	private long uid;
	private boolean isPrivacy;

	/**
	 * @param response
	 */
	public UserPrivacyResponseBean(String response) {
		super(response);

		if (response == null)
			return;

		try {
			JSONObject obj = new JSONObject(response);
			JSONObject json = obj.optJSONObject(UserInfo.KEY_USER_INFO);
			if (json != null) {
				isPrivacy = json.optBoolean(UserInfo.KEY_PRIVACY);
				uid = json.optLong(UserInfo.KEY_UID);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public long getUid() {
		return uid;
	}

	public boolean isPrivacy() {
		return isPrivacy;
	}

}
