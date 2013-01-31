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
		public String getEnabledString() {
			// TODO Auto-generated method stub
			return "错误消息";
		}

		@Override
		public String getDisabledString() {
			// TODO Auto-generated method stub
			return "错误消息";
		}

		@Override
		public String getIntermediateString() {
			// TODO Auto-generated method stub
			return "错误消息";
		}

		@Override
		public String getAction() {
			// TODO Auto-generated method stub
			return action;
		}

	},
	PHOTO("/UploadFileAction") {

		@Override
		public String getEnabledString() {
			// TODO Auto-generated method stub
			return "上传照片";
		}

		@Override
		public String getDisabledString() {
			// TODO Auto-generated method stub
			return "上传成功";
		}

		@Override
		public String getIntermediateString() {
			// TODO Auto-generated method stub
			return "正在上传..";
		}

		@Override
		public String getAction() {
			// TODO Auto-generated method stub
			return action;
		}

	},
	COMMENT("/CommentAction_putComment") {

		@Override
		public String getEnabledString() {
			// TODO Auto-generated method stub
			return "添加評論";
		}

		@Override
		public String getDisabledString() {
			// TODO Auto-generated method stub
			return "評論成功";
		}

		@Override
		public String getIntermediateString() {
			// TODO Auto-generated method stub
			return "正在评论..";
		}

		@Override
		public String getAction() {
			// TODO Auto-generated method stub
			return action;
		}
	},
	FOLLOW("/FollowAction") {

		@Override
		public String getEnabledString() {
			// TODO Auto-generated method stub
			return "開始跟隨";
		}

		@Override
		public String getDisabledString() {
			// TODO Auto-generated method stub
			return "跟隨成功";
		}

		@Override
		public String getIntermediateString() {
			// TODO Auto-generated method stub
			return "正在跟随..";
		}

		@Override
		public String getAction() {
			// TODO Auto-generated method stub
			return action;
		}
	},
	LIKE("/LikeAction") {

		@Override
		public String getEnabledString() {
			// TODO Auto-generated method stub
			return "添加喜歡";
		}

		@Override
		public String getDisabledString() {
			// TODO Auto-generated method stub
			return "喜歡成功";
		}

		@Override
		public String getIntermediateString() {
			// TODO Auto-generated method stub
			return "正在喜欢..";
		}

		@Override
		public String getAction() {
			// TODO Auto-generated method stub
			return action;
		}
	};

	public String action;

	MsgType(String action) {
		this.action = action;
	}

	public abstract String getAction();

	public abstract String getEnabledString();

	public abstract String getIntermediateString();

	public abstract String getDisabledString();

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
