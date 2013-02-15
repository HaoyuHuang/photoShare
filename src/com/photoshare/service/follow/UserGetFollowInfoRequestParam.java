/**
 * 
 */
package com.photoshare.service.follow;

import android.os.Bundle;

import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.users.UserInfo;

/**
 * @author czj_yy
 * 
 */
public class UserGetFollowInfoRequestParam extends RequestParam {

	private long uid;

	public static final String FIELDS_DEFAULT = UserInfo.KEY_TINY_HEAD_URL
			+ "," + UserInfo.KEY_NAME + "," + UserInfo.KEY_PSEUDO_NAME + ","
			+ UserInfo.KEY_UID;
	/**
	 * 所有字段
	 */
	public static final String FIELDS_ALL = UserInfo.KEY_UID + ","
			+ UserInfo.KEY_NAME + "," + UserInfo.KEY_GENDER + ","
			+ UserInfo.KEY_WEBSITE + "," + UserInfo.KEY_BIO + ","
			+ UserInfo.KEY_BIRTHDAY + "," + UserInfo.KEY_PHONE_NUMBER + ","
			+ UserInfo.KEY_PRIVACY + "," + UserInfo.KEY_TINY_HEAD_URL + ","
			+ UserInfo.KEY_MIDDLE_HEAD_URL + "," + UserInfo.KEY_LARGE_HEAD_URL;

	private String fields = FIELDS_DEFAULT;

	private FollowType type;

	@Deprecated
	public String getMethod() {
		return "userFollowGetInfo.do" + "?method=" + type;
	}

	private static final String ACTION = "/FollowGetInfoAction_";

	public String getAction() {
		return ACTION + type.getTag();
	}

	/**
	 * @param ids
	 */
	public UserGetFollowInfoRequestParam(long ids) {
		super();
		this.uid = ids;
	}

	/**
	 * @param ids
	 * @param fields
	 */
	public UserGetFollowInfoRequestParam(long ids, String fields) {
		super();
		this.uid = ids;
		this.fields = fields;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.RequestParam#getParams()
	 */
	@Override
	public Bundle getParams() throws NetworkException {
		Bundle bundle = new Bundle();
		if (fields != null) {
			bundle.putString("fields", fields);
		}
		bundle.putLong(UserInfo.KEY_USER_INFO + "." + UserInfo.KEY_UID, uid);
		if (type != null) {
			bundle.putString("method", type.toString());
		}
		return bundle;
	}

	public FollowType getType() {
		return type;
	}

	public void setType(FollowType type) {
		this.type = type;
	}

}
