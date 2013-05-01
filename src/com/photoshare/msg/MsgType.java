/**
 * 
 */
package com.photoshare.msg;

/**
 * @author Aron
 * 
 *         MsgType defines Message types of Photo, Like, Comment and Follow. It
 *         is used to distinguish each message item {@link MessageItem}.
 * 
 */
public enum MsgType {

	NULL("") {

		@Override
		public String getStartText() {
			// TODO Auto-generated method stub
			return "错误消息";
		}

		@Override
		public String getSuccessText() {
			// TODO Auto-generated method stub
			return "错误消息";
		}

		@Override
		public String getPendingText() {
			// TODO Auto-generated method stub
			return "错误消息";
		}

		@Override
		public String getAction() {
			// TODO Auto-generated method stub
			return action;
		}

		@Override
		public String getFailText() {
			// TODO Auto-generated method stub
			return "错误消息";
		}

	},
	PHOTO("/uploadPhoto") {

		@Override
		public String getStartText() {
			// TODO Auto-generated method stub
			return "上传照片";
		}

		@Override
		public String getSuccessText() {
			// TODO Auto-generated method stub
			return "上传成功";
		}

		@Override
		public String getPendingText() {
			// TODO Auto-generated method stub
			return "正在上传..";
		}

		@Override
		public String getAction() {
			// TODO Auto-generated method stub
			return action;
		}

		@Override
		public String getFailText() {
			// TODO Auto-generated method stub
			return "上传失败";
		}

	},
	COMMENT("/CommentAction") {

		@Override
		public String getStartText() {
			// TODO Auto-generated method stub
			return "添加评论";
		}

		@Override
		public String getSuccessText() {
			// TODO Auto-generated method stub
			return "评论成功";
		}

		@Override
		public String getPendingText() {
			// TODO Auto-generated method stub
			return "正在评论..";
		}

		@Override
		public String getAction() {
			// TODO Auto-generated method stub
			return action;
		}

		@Override
		public String getFailText() {
			// TODO Auto-generated method stub
			return "评论失败";
		}
	},
	FOLLOW("/FollowAction") {

		@Override
		public String getStartText() {
			// TODO Auto-generated method stub
			return "开始跟随";
		}

		@Override
		public String getSuccessText() {
			// TODO Auto-generated method stub
			return "跟隨成功";
		}

		@Override
		public String getPendingText() {
			// TODO Auto-generated method stub
			return "正在跟随..";
		}

		@Override
		public String getAction() {
			// TODO Auto-generated method stub
			return action;
		}

		@Override
		public String getFailText() {
			// TODO Auto-generated method stub
			return "跟随失败";
		}
	},
	LIKE("/LikeAction") {

		@Override
		public String getStartText() {
			// TODO Auto-generated method stub
			return "添加喜欢";
		}

		@Override
		public String getSuccessText() {
			// TODO Auto-generated method stub
			return "喜欢成功";
		}

		@Override
		public String getPendingText() {
			// TODO Auto-generated method stub
			return "正在喜欢..";
		}

		@Override
		public String getAction() {
			// TODO Auto-generated method stub
			return action;
		}

		@Override
		public String getFailText() {
			// TODO Auto-generated method stub
			return "喜欢失败";
		}
	};

	public String action;

	MsgType(String action) {
		this.action = action;
	}

	public abstract String getAction();

	public abstract String getStartText();

	public abstract String getPendingText();

	public abstract String getSuccessText();

	public abstract String getFailText();

	public static MsgType SWITCH(String type) {
		if (type.equals(COMMENT)) {
			return COMMENT;
		} else if (type.equals(FOLLOW)) {
			return FOLLOW;
		} else if (type.equals(LIKE)) {
			return LIKE;
		} else if (type.equals(PHOTO)) {
			return PHOTO;
		}
		return NULL;
	}
}
