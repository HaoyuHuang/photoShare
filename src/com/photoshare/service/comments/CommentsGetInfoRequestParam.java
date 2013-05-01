package com.photoshare.service.comments;

import android.os.Bundle;

import com.photoshare.common.Builder;
import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;

public class CommentsGetInfoRequestParam extends RequestParam {

	@Deprecated
	private static final String METHOD = "commentGetInfo.do";

	private static final String ACTION = "/CommentGetInfoAction_";

	private CommentAction commentAction;

	public String getAction() {
		return ACTION + commentAction.getTag();
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

	private int currentPage = 1;

	private int demandPage;

	private int datediff;

	public static final String CURRENT_PAGE = "currentPage";

	public static final String DEMAND_PAGE = "demandPage";

	public static final String DATE_DIFF = "datediff";

	private String fields = FIELDS_DEFAULT;

	public CommentsGetInfoRequestParam(long pid) {
		this.pid = pid;
	}

	public CommentsGetInfoRequestParam(long pid, String fields) {
		this.pid = pid;
		this.fields = fields;
	}

	public CommentAction getCommentAction() {
		return commentAction;
	}

	public void setCommentAction(CommentAction commentAction) {
		this.commentAction = commentAction;
	}

	public static class CommentsGetInfoRqeustParamBuilder implements
			Builder<CommentsGetInfoRequestParam> {

		public CommentsGetInfoRequestParam build() {
			return null;
		}

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
		switch (commentAction) {
		case COMMENTS:
			parameters.putString(CommentInfo.KEY_COMMENT + "." + CURRENT_PAGE,
					String.valueOf(currentPage));
			parameters.putString(CommentInfo.KEY_COMMENT + "." + DEMAND_PAGE,
					String.valueOf(demandPage));
			break;
		case DATED_COMMENTS:
			parameters.putString(DATE_DIFF, String.valueOf(datediff));
			break;
		default:
			break;
		}
		return parameters;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
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

}
