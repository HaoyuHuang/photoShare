/**
 * 
 */
package com.photoshare.service.login;

/**
 * @author Aron
 * 
 */
public enum LoginType {

	SIGN_IN("signIn") {
		@Override
		public String getTag() {
			// TODO Auto-generated method stub
			return tag;
		}
	},
	SIGN_OFF("signOff") {
		@Override
		public String getTag() {
			// TODO Auto-generated method stub
			return tag;
		}
	},
	SIGN_UP("signUp") {
		@Override
		public String getTag() {
			// TODO Auto-generated method stub
			return tag;
		}
	};

	public String tag;

	public abstract String getTag();

	LoginType(String tag) {
		this.tag = tag;
	}
}
