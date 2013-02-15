package com.photoshare.service.users;

import android.os.Bundle;

import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;

public class UserGetInfoRequestParam extends RequestParam {

	@Deprecated
	public static final String METHOD_GETINFO = "userGetInfo.do";

	private static final String ACTION = "/UserHome_";

	private UserInfoType type = UserInfoType.UserInfo;

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
			+ UserInfo.KEY_FOLLOWING_CNT;

	/**
	 * 默认字段<br>
	 * 不添加fields参数也按此字段返回
	 */
	public static final String FIELD_DEFAULT = UserInfo.KEY_UID + ","
			+ UserInfo.KEY_NAME + "," + UserInfo.KEY_TINY_HEAD_URL + ","
			+ UserInfo.KEY_MIDDLE_HEAD_URL + "," + UserInfo.KEY_LARGE_HEAD_URL;

	/**
	 * 需要获取的用户uid的数组
	 */
	private long uid;

	/**
	 * 需要获取的字段
	 */
	private String fields = FIELD_DEFAULT;

	/**
	 * 构造一个users.getInfo接口请求参数
	 * 
	 * @param uids
	 *            需要获取的用户uid的数组
	 */
	public UserGetInfoRequestParam(long uid) {
		this.uid = uid;
	}

	/**
	 * getUserInfo接口请求参数
	 * 
	 * @param uids
	 *            需要获取的用户uid的数组
	 * @param fields
	 *            需要获取的字段
	 */
	public UserGetInfoRequestParam(long uid, String fields) {
		this.uid = uid;
		this.setFields(fields);
	}

	/**
	 * 获取uids
	 * 
	 * @return
	 */
	public long getUid() {
		return uid;
	}

	/**
	 * 设置uids
	 * 
	 * @param uids
	 */
	public void setUid(long uid) {
		this.uid = uid;
	}

	/**
	 * 获取fields
	 * 
	 * @return
	 */
	public String getFields() {
		return fields;
	}

	/**
	 * 设置fields
	 * 
	 * @param fields
	 */
	public void setFields(String fields) {
		this.fields = fields;
	}

	@Override
	public Bundle getParams() throws NetworkException {

		Bundle parameters = new Bundle();
		parameters.putString("method", METHOD_GETINFO);
		if (fields != null) {
			parameters.putString("fields", fields);
		}
		parameters.putString(UserInfo.KEY_UID, uid + "");
		return parameters;
	}

	public void setType(UserInfoType type) {
		this.type = type;
	}

}
