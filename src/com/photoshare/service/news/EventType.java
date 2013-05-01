/**
 * 
 */
package com.photoshare.service.news;

/**
 * @author czj_yy
 * 
 */
public enum EventType {

	NULL(0, ""), LIKE(4, "喜欢"), FOLLOW(3, "跟随"), PHOTO(2, "发布"), COMMENT(1,
			"评论");

	private final int type;

	private final String cnTag;

	EventType(int type, String cnTag) {
		this.type = type;
		this.cnTag = cnTag;
	}

	public int getType() {
		return type;
	}

	public String getCnTag() {
		return cnTag;
	}

	public static EventType Switch(int type) {
		switch (type) {
		case 1:
			return EventType.COMMENT;
		case 2:
			return EventType.PHOTO;
		case 3:
			return EventType.FOLLOW;
		case 4:
			return EventType.LIKE;
		default:
			return EventType.NULL;
		}
	}
}
