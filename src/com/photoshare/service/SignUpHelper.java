/**
 * 
 */
package com.photoshare.service;

import java.util.concurrent.Executor;

import android.os.Bundle;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.signup.UserSignUpRequestParam;
import com.photoshare.service.signup.UserSignUpResponseBean;
import com.photoshare.utils.User;
import com.photoshare.utils.Utils;

/**
 * @author Aron
 * 
 */
public class SignUpHelper {
	private User user;

	public SignUpHelper(User user) {
		this.user = user;
	}

	public UserSignUpResponseBean getUserName(UserSignUpRequestParam param)
			throws NetworkException, Throwable {
		Bundle params = param.getParams();
		UserSignUpResponseBean respBean = null;
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
			respBean = new UserSignUpResponseBean(response);
		} catch (RuntimeException re) {
			// TODO Auto-generated catch block
			Utils.logger("runtimeException: " + re.getMessage());
			throw new Throwable(re);
		}
		return respBean;
	}

	public void asyncSignUp(Executor pool, final UserSignUpRequestParam param,
			final AbstractRequestListener<UserSignUpResponseBean> listener) {
		pool.execute(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				try {
					UserSignUpResponseBean signupBean = getUserName(param);
					if (listener != null) {
						listener.onComplete(signupBean);
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
