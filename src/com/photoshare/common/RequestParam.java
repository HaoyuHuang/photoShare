
package com.photoshare.common;

import android.os.Bundle;
import android.text.TextUtils;

import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;

/*
 * 所有请求参数的基类
 * */
public abstract class RequestParam {
	
	public abstract Bundle getParams() throws NetworkException;
	
	public void checkNullParams (String... params) throws NetworkException {
		
		for (String param : params) {
			if (TextUtils.isEmpty(param)) {
				String errorMsg = "required parameter MUST NOT be null";
				throw new NetworkException(
						NetworkError.ERROR_CODE_NULL_PARAMETER, errorMsg,
						errorMsg);
			}
		}
		
	}
}
