package com.photoshare.service.comments;

public enum CommentAction {
	COMMENTS(1, "getCommentInfo"), DATED_COMMENTS(2, "getCommentInfoByDatediff");

	private String tag;

	private int code;

	private CommentAction(int code, String tag) {
		this.code = code;
		this.tag = tag;
	}

	public static CommentAction SWITCH(int code) {
		if (code == 1)
			return COMMENTS;
		if (code == 2)
			return DATED_COMMENTS;
		return null;
	}

	public String getTag() {
		return tag;
	}

	public int getCode() {
		return code;
	}

}
