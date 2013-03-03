/**
 * 
 */
package com.photoshare.service.likes;

import android.os.Bundle;

import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;

/**
 * @author Aron
 * 
 */
public class LikeGetInfoRequestParam extends RequestParam {

	@Deprecated
	private static final String METHOD = "likesGetInfo.do";

	private static final String ACTION = "/LikeGetInfoAction";

	public String getAction() {
		return ACTION;
	}

	/**
	 * 所有字段
	 * */
	public static final String FIELDS_ALL = LikeBean.KEY_LID + ","
			+ LikeBean.KEY_UID + "," + LikeBean.KEY_PID + ","
			+ LikeBean.KEY_UNAME + "," + LikeBean.KEY_TINY_UHEAD + ","
			+ LikeBean.KEY_CREATE_TIME;

	/**
	 * 默认字段
	 * */
	public static final String FIELDS_DEFAULT = LikeBean.KEY_UID + ","
			+ LikeBean.KEY_PID + "," + LikeBean.KEY_UNAME + ","
			+ LikeBean.KEY_TINY_UHEAD + "," + LikeBean.KEY_CREATE_TIME;

	private long pid;

	private int currentPage;

	private int demandPage;

	public static final String CURRENT_PAGE = "currentPage";

	public static final String DEMAND_PAGE = "demandPage";

	private String fields = FIELDS_DEFAULT;

	public LikeGetInfoRequestParam(long pid) {
		this.pid = pid;
	}

	public LikeGetInfoRequestParam(long pid, String fields) {
		this.pid = pid;
		this.fields = fields;
	}

	@Override
	public Bundle getParams() throws NetworkException {
		// TODO Auto-generated method stub
		Bundle parameters = new Bundle();
		parameters.putString("method", METHOD);
		if (fields != null) {
			parameters.putString("fields", fields);
		}
		parameters.putString(LikeBean.KEY_LIKE + "." + LikeBean.KEY_PID, pid
				+ "");
		return parameters;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getDemandPage() {
		return demandPage;
	}

	public void setDemandPage(int demandPage) {
		this.demandPage = demandPage;
	}

}
