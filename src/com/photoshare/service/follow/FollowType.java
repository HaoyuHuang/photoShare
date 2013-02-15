/**
 * 
 */
package com.photoshare.service.follow;

/**
 * @author czj_yy
 * 
 */
public enum FollowType {
	FOLLOWER("getFollowerInfo") {
		@Override
		public String getTag() {
			// TODO Auto-generated method stub
			return tag;
		}
	},
	FOLLOWING("getFollowingInfo") {
		@Override
		public String getTag() {
			// TODO Auto-generated method stub
			return tag;
		}
	};

	public String tag;

	FollowType(String tag) {
		this.tag = tag;
	}

	public abstract String getTag();

	public static FollowType SWITCH(String str) {
		if (str.equals("FOLLOWER")) {
			return FOLLOWER;
		}
		if (str.equals("FOLLOWING")) {
			return FOLLOWING;
		}
		return null;
	}
}