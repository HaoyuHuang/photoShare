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
public class UserGetOtherInfoRequestParam extends RequestParam {

	@Deprecated
	public static final String METHOD = "userGetOtherInfo.do";

	private long uid;

	private long fid;

	private String fields = FIELDS_ALL;

	private static final String ACTION = "/UserHome_";

	private UserInfoType type = UserInfoType.OtherInfo;

	public String getAction() {
		return ACTION + type.getTag();
	}

	/**
	 * 所有字段
	 */
	public static final String FIELDS_ALL = UserInfo.KEY_UID + ","
			+ UserInfo.KEY_NAME + "," + UserInfo.KEY_GENDER + ","
			+ UserInfo.KEY_WEBSITE + "," + UserInfo.KEY_BIO + ","
			+ UserInfo.KEY_BIRTHDAY + "," + UserInfo.KEY_PHONE_NUMBER + ","
			+ UserInfo.KEY_PRIVACY + "," + UserInfo.KEY_TINY_HEAD_URL + ","
			+ UserInfo.KEY_MIDDLE_HEAD_URL + "," + UserInfo.KEY_LARGE_HEAD_URL
			+ "," + UserInfo.KEY_PHOTOS_CNT + "," + UserInfo.KEY_LIKES_CNT
			+ "," + UserInfo.KEY_FOLLOWER_CNT + ","
			+ UserInfo.KEY_FOLLOWING_CNT + "," + UserInfo.KEY_IS_FOLLOWING;

	/**
	 * 默认字段<br>
	 * 不添加fields参数也按此字段返回
	 */
	public static final String FIELD_DEFAULT = UserInfo.KEY_UID + ","
			+ UserInfo.KEY_NAME + "," + UserInfo.KEY_TINY_HEAD_URL + ","
			+ UserInfo.KEY_MIDDLE_HEAD_URL + "," + UserInfo.KEY_LARGE_HEAD_URL;

	private static final String KEY_FID = "fid";

	/**
	 * @param uid
	 * @param fid
	 */
	public UserGetOtherInfoRequestParam(long uid, long fid) {
		super();
		this.uid = uid;
		this.fid = fid;
	}

	public long getUid() {
		return uid;
	}

	public void setUid(long uid) {
		this.uid = uid;
	}

	public long getFid() {
		return fid;
	}

	public void setFid(long fid) {
		this.fid = fid;
	}

	public String getFields() {
		return fields;
	}

	public UserInfoType getType() {
		return type;
	}

	public void setType(UserInfoType type) {
		this.type = type;
	}

	public void setFields(String fields) {
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
		bundle.putString("method", METHOD);
		bundle.putString(UserInfo.KEY_USER_INFO + "." + UserInfo.KEY_UID, uid
				+ "");
		bundle.putString(UserInfo.KEY_USER_INFO + "." + KEY_FID, fid + "");
		if (fields != null) {
			bundle.putString("fields", fields);
		}
		return bundle;
	}

}
