/**
 * 
 */
package com.photoshare.service.news;

/**
 * @author czj_yy
 * 
 */
public enum EventType {

	NULL(0), LIKE(1), FOLLOW(2), POPULAR(3), COMMENT(4);

	private final int type;

	EventType(int type) {
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public static EventType Switch(int type) {
		switch (type) {
		case 1:
			return EventType.LIKE;
		case 2:
			return EventType.FOLLOW;
		case 3:
			return EventType.POPULAR;
		case 4:
			return EventType.COMMENT;
		default:
			return EventType.NULL;
		}
	}
}
