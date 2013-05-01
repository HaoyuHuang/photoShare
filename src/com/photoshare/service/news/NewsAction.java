/**
 * 
 */
package com.photoshare.service.news;

/**
 * @author czj_yy
 * 
 */
public enum NewsAction {

	MY_NEWS("getUserNews") {
		@Override
		public String getTag() {
			return tag;
		}
	},
	FOLLOWING_NEWS("getFollowingNews") {
		@Override
		public String getTag() {
			return tag;
		}
	};

	public String tag;

	public abstract String getTag();

	NewsAction(String tag) {
		this.tag = tag;
	}

	public static NewsAction SWITCH(String action) {
		if (action.equals(MY_NEWS.getTag()))
			return MY_NEWS;
		if (action.equals(FOLLOWING_NEWS.getTag()))
			return FOLLOWING_NEWS;
		return null;
	}
}
