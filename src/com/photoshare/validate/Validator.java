package com.photoshare.validate;

import com.photoshare.service.users.UserInfo;
import com.photoshare.utils.Utils;

public class Validator {
	public static boolean isValid(UserInfo info) {
		boolean isValid = Utils.isBlank(info.getBio())
				|| Utils.isBlank(info.getWebsite());
		return !isValid;
	}
}
