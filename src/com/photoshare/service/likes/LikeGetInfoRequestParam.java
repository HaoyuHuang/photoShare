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

	private static final String ACTION = "/LikeGetInfoAction_";

	private LikeAction likeAction;

	public String getAction() {
		return ACTION + likeAction.getTag();
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

	private int currentPage = 1;

	private int demandPage;

	private int datediff;

	public static final String CURRENT_PAGE = "currentPage";

	public static final String DEMAND_PAGE = "demandPage";

	public static final String DATE_DIFF = "datediff";

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
		parameters.putString(LikeBean.KEY_LIKE + "." + LikeBean.KEY_PID,
				String.valueOf(pid));
		switch (likeAction) {
		case DATED_LIKE:
			parameters.putString(DATE_DIFF, String.valueOf(datediff));
			break;
		case LIKE:
			parameters.putString(LikeBean.KEY_LIKE + "." + CURRENT_PAGE,
					String.valueOf(currentPage));
			parameters.putString(LikeBean.KEY_LIKE + "." + DEMAND_PAGE,
					String.valueOf(demandPage));
			break;
		default:
			break;
		}
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

	public int getDatediff() {
		return datediff;
	}

	public void setDatediff(int datediff) {
		this.datediff = datediff;
	}

	public LikeAction getLikeAction() {
		return likeAction;
	}

	public void setLikeAction(LikeAction likeAction) {
		this.likeAction = likeAction;
	}

}
