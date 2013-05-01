/**
 * 
 */
package com.photoshare.service.follow;

/**
 * @author czj_yy
 * 
 */
public enum FollowAction {
	FOLLOWER(1, "getFollowerInfo"), FOLLOWING(2, "getFollowingInfo"), DATED_FOLLOWER(
			3, "getFollowerInfoByDatediff"), DATED_FOLLOWING(4,
			"getFollowingInfoByDatediff");

	private String tag;

	private int code;

	private FollowAction(int code, String tag) {
		this.code = code;
		this.tag = tag;
	}

	public static FollowAction SWITCH(String str) {
		if (str.equals("FOLLOWER")) {
			return FOLLOWER;
		}
		if (str.equals("FOLLOWING")) {
			return FOLLOWING;
		}
		return null;
	}

	public static FollowAction SWITCH(int code) {
		if (code == 1)
			return FOLLOWER;
		if (code == 2)
			return FOLLOWING;
		if (code == 3)
			return DATED_FOLLOWER;
		if (code == 4)
			return DATED_FOLLOWING;
		return null;
	}

	public String getTag() {
		return tag;
	}

	public int getCode() {
		return code;
	}

}