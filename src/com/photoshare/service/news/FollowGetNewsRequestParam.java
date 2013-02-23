/**
 * 
 */
package com.photoshare.service.news;

import android.os.Bundle;

import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.users.UserInfo;

/**
 * @author czj_yy
 *
 */
@Deprecated
public class FollowGetNewsRequestParam extends RequestParam {


	private static final String METHOD = "getMyNews";

	public String getMethod() {
		return "getNews" + "?method=" + "getUserNews";
	}

	/**
	 * 所有字段
	 */
	public static final String FIELDS_ALL = NewsBean.KEY_EVENT_TIME + ","
			+ NewsBean.KEY_EVENT_TYPE + "," + NewsBean.KEY_USER_ID + ","
			+ NewsBean.KEY_USER_NAME + "," + NewsBean.KEY_URLS;

	/**
	 * 默认字段<br>
	 * 不添加fields参数也按此字段返回
	 */
	public static final String FIELD_DEFAULT = NewsBean.KEY_EVENT_TIME + ","
			+ NewsBean.KEY_EVENT_TYPE + "," + NewsBean.KEY_USER_ID + ","
			+ NewsBean.KEY_USER_NAME + "," + NewsBean.KEY_URLS;

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
	public FollowGetNewsRequestParam(long uid) {
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
	public FollowGetNewsRequestParam(long uid, String fields) {
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
		parameters.putString("method", METHOD);
		if (fields != null) {
			parameters.putString("fields", fields);
		}
		parameters.putString(UserInfo.KEY_USER_INFO + "." + UserInfo.KEY_UID,
				uid + "");
		return parameters;
	}


}
