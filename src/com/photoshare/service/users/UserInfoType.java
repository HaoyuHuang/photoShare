/**
 * 
 */
package com.photoshare.service.users;

/**
 * @author Aron
 *
 */
public enum UserInfoType {
	
	EditInfo("editUserInfo") {
		@Override
		public String getTag() {
			// TODO Auto-generated method stub
			return tag;
		}
	}, UserInfo("getUserProfile") {
		@Override
		public String getTag() {
			// TODO Auto-generated method stub
			return tag;
		}
	}, OtherInfo("getOtherProfile") {
		@Override
		public String getTag() {
			// TODO Auto-generated method stub
			return tag;
		}
	}, UserPrivacy("setPrivacy") {
		@Override
		public String getTag() {
			// TODO Auto-generated method stub
			return tag;
		}
	};
	
	public String tag;
	
	public abstract String getTag();
	
	UserInfoType(String tag) {
		this.tag = tag;
	}
}
