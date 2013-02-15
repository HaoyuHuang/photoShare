/**
 * 
 */
package com.photoshare.service.follow;

import android.os.Bundle;

import com.photoshare.common.Builder;
import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.users.UserInfo;

/**
 * @author czj_yy
 * 
 */
public class UserFollowRequestParam extends RequestParam {

	public static final String KEY_FOLLOW_ID = "fid";

	@Deprecated
	private static final String METHOD = "userFollow.do";

	private static final String ACTION = "/FollowAction";

	private static final String KEY_FOLLOW_TAG = "follow";

	public String getAction() {
		return ACTION;
	}

	private long userId;

	private long followId;

	private boolean isFollowing;

	private String tinyUrl;

	private UserFollowRequestParam(FollowBuilder builder) {
		this.userId = builder.userId;
		this.followId = builder.followId;
		this.isFollowing = builder.isFollowing;
		this.tinyUrl = builder.tinyUrl;
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
		parameters.putString(KEY_FOLLOW_TAG + "." + UserInfo.KEY_UID, userId
				+ "");
		parameters.putString(KEY_FOLLOW_TAG + "." + KEY_FOLLOW_ID, followId
				+ "");
		parameters.putString(KEY_FOLLOW_TAG + "." + UserInfo.KEY_IS_FOLLOWING,
				isFollowing + "");
		return parameters;
	}

	public static class FollowBuilder implements
			Builder<UserFollowRequestParam> {

		private long userId;

		private String tinyUrl;

		private long followId;

		private boolean isFollowing;

		public FollowBuilder UserId(long userId) {
			this.userId = userId;
			return this;
		}

		public FollowBuilder FollowId(long followId) {
			this.followId = followId;
			return this;
		}

		public FollowBuilder isFollowing(boolean isFollowing) {
			this.isFollowing = isFollowing;
			return this;
		}

		public FollowBuilder TinyFollowingUrl(String url) {
			this.tinyUrl = url;
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.photoshare.common.Builder#build()
		 */
		public UserFollowRequestParam build() {
			// TODO Auto-generated method stub
			return new UserFollowRequestParam(this);
		}
	}

	public long getUserId() {
		return userId;
	}

	public long getFollowId() {
		return followId;
	}

	public boolean isFollowing() {
		return isFollowing;
	}

	public String getTinyUrl() {
		return tinyUrl;
	}

}
