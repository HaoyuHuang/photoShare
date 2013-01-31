/**
 * 
 */
package com.photoshare.service.photos;

/**
 * @author czj_yy
 * 
 */
public enum PhotoType {
	MyLikedPhotos("getUserLikedPhotos") {
		@Override
		public String getTag() {
			// TODO Auto-generated method stub
			return tag;
		}
	},
	MyPhotos("getUserPhotos") {
		@Override
		public String getTag() {
			// TODO Auto-generated method stub
			return tag;
		}
	},
	PopularPhotos("getPopularPhotos") {
		@Override
		public String getTag() {
			// TODO Auto-generated method stub
			return tag;
		}
	},
	MyFeeds("getPhotosFeeds") {
		@Override
		public String getTag() {
			// TODO Auto-generated method stub
			return tag;
		}
	};

	public String tag;

	PhotoType(String tag) {
		this.tag = tag;
	}

	public abstract String getTag();

	public static PhotoType SWITCH(String str) {
		if (str.equals("MyLikedPhotos")) {
			return MyLikedPhotos;
		}
		if (str.equals("MyPhotos")) {
			return MyPhotos;
		}
		if (str.equals("PopularPhotos")) {
			return PopularPhotos;
		}
		if (str.equals("MyFeeds")) {
			return MyFeeds;
		}
		return null;
	}
}
