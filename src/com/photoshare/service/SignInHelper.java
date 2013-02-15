/**
 * 
 */
package com.photoshare.service;

import java.util.concurrent.Executor;

import android.os.Bundle;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.signin.UserSignInRequestParam;
import com.photoshare.service.signin.UserSignInResponseBean;
import com.photoshare.utils.User;
import com.photoshare.utils.Utils;

/**
 * @author Aron
 * 
 */
public class SignInHelper {

	private User user;

	public SignInHelper(User user) {
		this.user = user;
	}

	public UserSignInResponseBean getUserInfo(UserSignInRequestParam param)
			throws NetworkException, Throwable {
		Bundle params = param.getParams();
		UserSignInResponseBean respBean = null;
		try {
			String response = user.request(param.getAction(), params);
			if (response != null) {
				Utils.checkResponse(response);
			} else {
				Utils.logger("null response");
				throw new NetworkException(
						NetworkError.ERROR_CODE_UNKNOWN_ERROR, "null response",
						"null response");
			}
			respBean = new UserSignInResponseBean(response);
		} catch (RuntimeException re) {
			// TODO Auto-generated catch block
			Utils.logger("runtimeException: " + re.getMessage());
			throw new Throwable(re);
		}
		return respBean;
	}

	public void asyncSignIn(Executor pool, final UserSignInRequestParam param,
			final AbstractRequestListener<UserSignInResponseBean> listener) {
		pool.execute(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				try {
					UserSignInResponseBean signinBean = getUserInfo(param);
					if (listener != null) {
						listener.onComplete(signinBean);
					}
				} catch (NetworkException e) {
					Utils.logger("network exception " + e.getMessage());
					if (listener != null) {
						listener.onNetworkError(new NetworkError(e.getMessage()));
						e.printStackTrace();
					}
				} catch (Throwable e) {
					Utils.logger("on fault " + e.getMessage());
					if (listener != null) {
						listener.onFault(e);
					}
				}

			}

		});
	}

}
