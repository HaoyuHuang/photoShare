/**
 * 
 */
package com.photoshare.service.news;

import android.os.Bundle;

import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.users.UserInfo;

/**
 * @author Aron
 * 
 */
public class UserGetNewsRequestParam extends RequestParam {

	@Deprecated
	private static final String METHOD = "getMyNews";

	@Deprecated
	public String getMethod() {
		return "getNews" + "?method=" + "getUserNews";
	}

	private static final String ACTION = "/NewsAction_";

	public String getAction() {
		return ACTION + type.getTag();
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

	private NewsType type;

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
	public UserGetNewsRequestParam(long uid) {
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
	public UserGetNewsRequestParam(long uid, String fields) {
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

	public NewsType getType() {
		return type;
	}

	public void setType(NewsType type) {
		this.type = type;
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
