/**
 * 
 */
package com.photoshare.service.comments;

import android.os.Bundle;

import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;

/**
 * @author czj_yy
 * 
 */
public class PutCommentRequestParam extends RequestParam {

	private long mUserId;

	private long mPhotoId;

	private String tinyUrl;

	private String comment;

	@Deprecated
	private static final String METHOD = "putComment";

	private static final String ACTION = "/CommentAction";

	public String getAction() {
		return ACTION;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.RequestParam#getParams()
	 */
	@Override
	public Bundle getParams() throws NetworkException {
		// TODO Auto-generated method stub
		Bundle parameters = new Bundle();
		parameters.putString("method", METHOD);
		parameters.putString(CommentInfo.KEY_COMMENT + "."
				+ CommentInfo.KEY_UID, mUserId + "");
		parameters.putString(CommentInfo.KEY_COMMENT + "."
				+ CommentInfo.KEY_PID, mPhotoId + "");
		parameters.putString(CommentInfo.KEY_COMMENT + "."
				+ CommentInfo.KEY_CONTENT, comment);
		return parameters;
	}

	public long getmUserId() {
		return mUserId;
	}

	public void setmUserId(long mUserId) {
		this.mUserId = mUserId;
	}

	public long getmPhotoId() {
		return mPhotoId;
	}

	public void setmPhotoId(long mPhotoId) {
		this.mPhotoId = mPhotoId;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getTinyUrl() {
		return tinyUrl;
	}

	public void setTinyUrl(String tinyUrl) {
		this.tinyUrl = tinyUrl;
	}

}
