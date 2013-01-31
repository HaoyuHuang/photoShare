/**
 * 
 */
package com.photoshare.service.users;

import android.os.Bundle;

import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;

/**
 * @author Aron
 * 
 */
public class UserPrivacyRequestParam extends RequestParam {

	@Deprecated
	public static final String METHOD = "userPrivacy.do";

	private static final String ACTION = "/UserHome_";

	private UserInfoType type = UserInfoType.UserPrivacy;

	public String getAction() {
		return ACTION + type.getTag();
	}

	private boolean isPrivacy;
	private long uid;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.RequestParam#getParams()
	 */
	@Override
	public Bundle getParams() throws NetworkException {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle.putString("method", METHOD);
		bundle.putString(UserInfo.KEY_USER_INFO + "." + UserInfo.KEY_UID, uid
				+ "");
		bundle.putString(UserInfo.KEY_USER_INFO + "." + UserInfo.KEY_PRIVACY,
				isPrivacy + "");
		return null;
	}

	public void setPrivacy(boolean isPrivacy) {
		this.isPrivacy = isPrivacy;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public void setType(UserInfoType type) {
		this.type = type;
	}

}
