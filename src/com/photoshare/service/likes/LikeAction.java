package com.photoshare.service.likes;

public enum LikeAction {
	LIKE(1, "getLikeInfo"), DATED_LIKE(2, "getLikeInfoByDatediff");

	private String tag;

	private int code;

	private LikeAction(int code, String tag) {
		this.code = code;
		this.tag = tag;
	}

	public static LikeAction SWITCH(int code) {
		if (code == 1)
			return LIKE;
		if (code == 2)
			return DATED_LIKE;
		return null;
	}

	public String getTag() {
		return tag;
	}

	public int getCode() {
		return code;
	}

}
