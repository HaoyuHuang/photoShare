/**
 * 
 */
package com.photoshare.service.photos;

import android.os.Bundle;

import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;

/**
 * @author Aron
 * 
 */
public class PhotoGetInfoRequestParam extends RequestParam {

	@Deprecated
	private static final String METHOD = "photoGetInfo.do";

	private static final String ACTION = "/PhotoAction";

	public String getAction() {
		return ACTION;
	}

	private long pid;

	/**
	 * 所有字段
	 */
	public static final String FIELDS_ALL = PhotoBean.KEY_PID + ","
			+ PhotoBean.KEY_UID + "," + PhotoBean.KEY_UNAME + ","
			+ PhotoBean.KEY_CAPTION + "," + PhotoBean.KEY_FILE_NAME + ","
			+ PhotoBean.KEY_CONTENT + "," + PhotoBean.KEY_CREATE_TIME + ","
			+ PhotoBean.KEY_COMMENT_COUNT + "," + PhotoBean.KEY_LIKES_COUNT
			+ "," + PhotoBean.KEY_TINY_URL + "," + PhotoBean.KEY_MIDDLE_URL
			+ "," + PhotoBean.KEY_LARGE_URL + "," + PhotoBean.KEY_COMMENTS;

	/**
	 * 默认字段<br>
	 * 不添加fields参数也按此字段返回
	 */
	public static final String FIELDS_DEFAULT = PhotoBean.KEY_PID + ","
			+ PhotoBean.KEY_UID + "," + PhotoBean.KEY_UNAME + ","
			+ PhotoBean.KEY_MIDDLE_URL;

	private String fields = FIELDS_DEFAULT;

	/**
	 * @param pid
	 */
	public PhotoGetInfoRequestParam(long pid) {
		super();
		this.pid = pid;
	}

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
		bundle.putString("fields", fields);
		bundle.putString(PhotoBean.KEY_PHOTO + "Bean." + PhotoBean.KEY_PID, pid
				+ "");
		return bundle;
	}

}
