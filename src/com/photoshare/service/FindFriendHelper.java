/**
 * 
 */
package com.photoshare.service;

import java.util.concurrent.Executor;

import android.os.Bundle;
import android.util.Log;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.findfriends.FindFriendsRequestParam;
import com.photoshare.service.findfriends.FindFriendsResponseBean;
import com.photoshare.utils.User;
import com.photoshare.utils.Utils;

/**
 * @author Aron
 * 
 */
public class FindFriendHelper {
	private User user;

	public FindFriendHelper(User user) {
		this.user = user;
	}

	public FindFriendsResponseBean getFriendsInfo(FindFriendsRequestParam param)
			throws NetworkException, Throwable {
		Bundle parameters = param.getParams();
		FindFriendsResponseBean responseBean = null;

		try {
			String response = user.request(param.getAction(),
					parameters);

			if (response != null) {
				Utils.checkResponse(response);
			} else {
				Utils.logger("null response");
				throw new NetworkException(
						NetworkError.ERROR_CODE_UNKNOWN_ERROR, "null response",
						"null response");
			}
			responseBean = new FindFriendsResponseBean(response);
		} catch (RuntimeException e) {
			Log.e("runtimeException", e.getMessage());
			throw new Throwable(e);
		}
		return responseBean;
	}

	public void asyncGetFriendsInfo(Executor pool,
			final FindFriendsRequestParam param,
			final AbstractRequestListener<FindFriendsResponseBean> listener) {

		pool.execute(new Runnable() {

			public void run() {

				try {
					FindFriendsResponseBean bean = getFriendsInfo(param);
					if (listener != null) {
						listener.onComplete(bean);
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
