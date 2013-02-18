package com.photoshare.service.comments;

import android.os.Bundle;

import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;

public class CommentsGetInfoRequestParam extends RequestParam {

	@Deprecated
	private static final String METHOD = "commentGetInfo.do";

	private static final String ACTION = "/CommentGetInfoAction";

	public String getAction() {
		return ACTION;
	}

	/**
	 * 所有字段
	 * */
	public static final String FIELDS_ALL = CommentInfo.KEY_CID + ","
			+ CommentInfo.KEY_PID + "," + CommentInfo.KEY_UID + ","
			+ CommentInfo.KEY_UNAME + "," + CommentInfo.KEY_CONTENT + ","
			+ CommentInfo.KEY_CREATE_TIME;
	/**
	 * 默认字段
	 * */
	public static final String FIELDS_DEFAULT = CommentInfo.KEY_PID + ","
			+ CommentInfo.KEY_UNAME + "," + CommentInfo.KEY_CONTENT + ","
			+ CommentInfo.KEY_CREATE_TIME;

	private long pid;

	private int currentPage;

	private int demandPage;

	public static final String CURRENT_PAGE = "currentPage";

	public static final String DEMAND_PAGE = "demandPage";

	private String fields = FIELDS_DEFAULT;

	public CommentsGetInfoRequestParam(long pid) {
		this.pid = pid;
	}

	public CommentsGetInfoRequestParam(long pid, String fields) {
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
		parameters.putString(CommentInfo.KEY_COMMENT + "."
				+ CommentInfo.KEY_PID, pid + "");
		parameters.putString(CommentInfo.KEY_COMMENT + "." + CURRENT_PAGE,
				currentPage + "");
		parameters.putString(CommentInfo.KEY_COMMENT + "." + DEMAND_PAGE,
				demandPage + "");
		return parameters;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public void setDemandPage(int demandPage) {
		this.demandPage = demandPage;
	}

}
