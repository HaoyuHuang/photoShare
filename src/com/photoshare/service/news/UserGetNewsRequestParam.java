/**
 * 
 */
package com.photoshare.service.news;

import android.os.Bundle;

import com.photoshare.common.Builder;
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

	private static final String ACTION = "/BaseNewsAction_";

	public String getAction() {
		return ACTION + newsAction.getTag();
	}

	/**
	 * 所有字段
	 */
	public static final String FIELDS_ALL = NewsBean.KEY_EVENT_TIME + ","
			+ NewsBean.KEY_EVENT_TYPE + "," + NewsBean.KEY_USER_ID + ","
			+ NewsBean.KEY_USER_NAME;

	/**
	 * 默认字段<br>
	 * 不添加fields参数也按此字段返回
	 */
	public static final String FIELD_DEFAULT = NewsBean.KEY_EVENT_TIME + ","
			+ NewsBean.KEY_EVENT_TYPE + "," + NewsBean.KEY_USER_ID + ","
			+ NewsBean.KEY_USER_NAME;

	/**
	 * 需要获取的用户uid的数组
	 */
	private long uid;

	private int datediff;

	private NewsAction newsAction;

	/**
	 * 需要获取的字段
	 */
	private String fields = FIELD_DEFAULT;

	public UserGetNewsRequestParam(UserGetNewsBuilder builder) {
		this.datediff = builder.datediff;
		this.fields = builder.fields;
		this.newsAction = builder.newsAction;
		this.uid = builder.uid;
	}

	public static class UserGetNewsBuilder implements
			Builder<UserGetNewsRequestParam> {

		private long uid;

		private int datediff;

		private NewsAction newsAction;

		private String fields;

		public UserGetNewsBuilder UserId(long userId) {
			this.uid = userId;
			return this;
		}

		public UserGetNewsBuilder DateDiff(int datediff) {
			this.datediff = datediff;
			return this;
		}

		public UserGetNewsBuilder NewsAction(NewsAction newsAction) {
			this.newsAction = newsAction;
			return this;
		}

		public UserGetNewsBuilder Fields(String fields) {
			this.fields = fields;
			return this;
		}

		public UserGetNewsRequestParam build() {
			return new UserGetNewsRequestParam(this);
		}

	}

	public long getUid() {
		return uid;
	}

	public int getDatediff() {
		return datediff;
	}

	public NewsAction getNewsAction() {
		return newsAction;
	}

	public String getFields() {
		return fields;
	}

	@Override
	public Bundle getParams() throws NetworkException {

		Bundle parameters = new Bundle();
		parameters.putString("method", METHOD);
		if (fields != null) {
			parameters.putString("fields", fields);
		}
		parameters.putString(UserInfo.KEY_UID, String.valueOf(uid));
		parameters.putString("datediff", String.valueOf(datediff));
		return parameters;
	}
}
