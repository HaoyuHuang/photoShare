/**
 * 
 */
package com.photoshare.service.news;

/**
 * @author czj_yy
 * 
 */
public enum NewsType {

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

	NewsType(String tag) {
		this.tag = tag;
	}
}
