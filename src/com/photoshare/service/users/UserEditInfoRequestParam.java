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
public class UserEditInfoRequestParam extends RequestParam {

	@Deprecated
	public static final String METHOD = "userEditInfo.do";

	private static final String ACTION = "/UserHome_";

	private UserInfoType type = UserInfoType.EditInfo;

	public String getAction() {
		return ACTION + type.getTag();
	}

	private long id;
	private String website;
	private String bio;
	private String birthday;
	private String phone;
	private String mail;

	public UserEditInfoRequestParam(UserInfo info) {
		this.bio = info.getBio();
		this.birthday = info.getBirthday();
		this.id = info.getUid();
		this.mail = info.getMail();
		this.phone = info.getPhoneNumber();
		this.website = info.getWebsite();
	}

	public void fill(UserInfo info) {
		this.bio = info.getBio();
		this.birthday = info.getBirthday();
		this.id = info.getUid();
		this.mail = info.getMail();
		this.phone = info.getPhoneNumber();
		this.website = info.getWebsite();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.RequestParam#getParams()
	 */
	@Override
	public Bundle getParams() throws NetworkException {
		// TODO Auto-generated method stub
		Bundle parameters = new Bundle();
		parameters.putString("method", METHOD);
		parameters.putString(UserInfo.KEY_USER_INFO + "." + UserInfo.KEY_UID,
				id + "");
		parameters.putString(UserInfo.KEY_USER_INFO + "."
				+ UserInfo.KEY_WEBSITE, website);
		parameters.putString(UserInfo.KEY_USER_INFO + "." + UserInfo.KEY_BIO,
				bio);
		parameters.putString(UserInfo.KEY_USER_INFO + "."
				+ UserInfo.KEY_BIRTHDAY, birthday);
		parameters.putString(UserInfo.KEY_USER_INFO + "."
				+ UserInfo.KEY_PHONE_NUMBER, phone);
		parameters.putString(UserInfo.KEY_USER_INFO + "." + UserInfo.KEY_MAIL,
				mail);
		return parameters;
	}

	public void setType(UserInfoType type) {
		this.type = type;
	}

}
