package com.photoshare.service.photos;

import android.os.Bundle;

import com.photoshare.common.Builder;
import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.users.UserInfo;

public class PhotosGetInfoRequestParam extends RequestParam {

	private static final String KEY_CURRENT_PAGE = "currentPage";
	private static final String KEY_DEMAND_PAGE = "demandPage";

	/**
	 * 所有字段
	 */
	public static final String FIELDS_ALL = PhotoBean.KEY_PID + ","
			+ PhotoBean.KEY_UID + "," + PhotoBean.KEY_UNAME + ","
			+ PhotoBean.KEY_CAPTION + "," + PhotoBean.KEY_FILE_NAME + ","
			+ PhotoBean.KEY_CREATE_TIME + "," + PhotoBean.KEY_COMMENT_COUNT
			+ "," + PhotoBean.KEY_LIKES_COUNT + "," + PhotoBean.KEY_TINY_URL
			+ "," + PhotoBean.KEY_MIDDLE_URL + "," + PhotoBean.KEY_LARGE_URL
			+ "," + PhotoBean.KEY_COMMENTS;

	/**
	 * 默认字段<br>
	 * 不添加fields参数也按此字段返回
	 */
	public static final String FIELD_DEFAULT = PhotoBean.KEY_PID + ","
			+ PhotoBean.KEY_UID + "," + PhotoBean.KEY_UNAME + ","
			+ PhotoBean.KEY_CAPTION + "," + PhotoBean.KEY_CREATE_TIME + ","
			+ PhotoBean.KEY_COMMENT_COUNT + "," + PhotoBean.KEY_LIKES_COUNT
			+ "," + PhotoBean.KEY_TINY_URL + "," + PhotoBean.KEY_MIDDLE_URL
			+ "," + PhotoBean.KEY_LARGE_URL;

	/**
	 * 需要获取跟随者uid的数组
	 */
	private long uid;

	private int currentPage = 1;

	private int demandPage;

	private PhotoAction type;

	@Deprecated
	public String getMethod() {
		return "photosGetInfo.do" + "?method=" + type.toString();
	}

	private static final String ACTION = "/PhotosGetInfoAction_";

	public String getAction() {
		return ACTION + type.getTag();
	}

	/**
	 * 需要获取的字段
	 */
	private String fields = FIELD_DEFAULT;

	public PhotosGetInfoRequestParam(PhotoRequestBuilder builder) {
		this.currentPage = builder.currentPage;
		this.demandPage = builder.demandPage;
		this.fields = builder.fields;
		this.type = builder.method;
		this.uid = builder.uid;
	}

	public static class PhotoRequestBuilder implements
			Builder<PhotosGetInfoRequestParam> {
		private long uid;

		private int currentPage;

		private int demandPage;

		private PhotoAction method;

		private String fields;

		public PhotoRequestBuilder UserId(long uid) {
			this.uid = uid;
			return this;
		}

		public PhotoRequestBuilder CurrentPage(int cPage) {
			this.currentPage = cPage;
			return this;
		}

		public PhotoRequestBuilder DemandPage(int dPage) {
			this.demandPage = dPage;
			return this;
		}

		public PhotoRequestBuilder Method(PhotoAction method) {
			this.method = method;
			return this;
		}

		public PhotoRequestBuilder Field(String fields) {
			this.fields = fields;
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.photoshare.common.Builder#build()
		 */
		public PhotosGetInfoRequestParam build() {
			// TODO Auto-generated method stub
			return new PhotosGetInfoRequestParam(this);
		}

	}

	@Override
	public Bundle getParams() throws NetworkException {
		// TODO Auto-generated method stub
		Bundle parameters = new Bundle();
		parameters.putString("method",
				type.toString());
		if (fields != null) {
			parameters.putString(UserInfo.KEY_USER_INFO + "." + "fields",
					fields);
		}
		parameters.putString(UserInfo.KEY_USER_INFO + "." + KEY_CURRENT_PAGE,
				currentPage + "");
		parameters.putString(UserInfo.KEY_USER_INFO + "." + KEY_DEMAND_PAGE,
				demandPage + "");
		parameters.putString(UserInfo.KEY_USER_INFO + "." + UserInfo.KEY_UID,
				uid + "");
		return parameters;
	}

}