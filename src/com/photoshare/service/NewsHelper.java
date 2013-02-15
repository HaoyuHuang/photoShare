/**
 * 
 */
package com.photoshare.service;

import java.util.concurrent.Executor;

import android.os.Bundle;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.news.FollowGetNewsRequestParam;
import com.photoshare.service.news.FollowGetNewsResponseBean;
import com.photoshare.service.news.UserGetNewsRequestParam;
import com.photoshare.service.news.UserGetNewsResponseBean;
import com.photoshare.utils.User;
import com.photoshare.utils.Utils;

/**
 * @author czj_yy
 * 
 */
public class NewsHelper {
	private User user;

	public NewsHelper(User user) {
		this.user = user;
	}

	@Deprecated
	public FollowGetNewsResponseBean getFollowNews(
			FollowGetNewsRequestParam param) throws NetworkException, Throwable {

		Bundle parameters = param.getParams();
		FollowGetNewsResponseBean usersBean = null;
		try {
			String response = user.request(param.getMethod(), parameters);
			if (response != null) {
				Utils.checkResponse(response);

			} else {
				Utils.logger("null response");
				throw new NetworkException(
						NetworkError.ERROR_CODE_UNKNOWN_ERROR, "null response",
						"null response");
			}
			usersBean = new FollowGetNewsResponseBean(response);
		} catch (RuntimeException re) {
			Utils.logger("runtime exception " + re.getMessage());
			throw new Throwable(re);
		}
		return usersBean;
	}

	/**
	 * 异步调用获取用户信息接口<br>
	 * 
	 * @param pool
	 *            线程池
	 * @param param
	 *            请求对象
	 * @param listener
	 *            回调
	 */
	@Deprecated
	public void asyncGetFollowNews(Executor pool,
			final FollowGetNewsRequestParam param,
			final AbstractRequestListener<FollowGetNewsResponseBean> listener) {

		pool.execute(new Runnable() {

			public void run() {

				try {
					FollowGetNewsResponseBean usersBean = getFollowNews(param);
					if (listener != null) {
						listener.onComplete(usersBean);
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

	public UserGetNewsResponseBean getUserNews(UserGetNewsRequestParam param)
			throws NetworkException, Throwable {

		Bundle parameters = param.getParams();
		UserGetNewsResponseBean usersBean = null;
		try {
			String response = user.request(param.getAction(), parameters);
			if (response != null) {
				Utils.checkResponse(response);

			} else {
				Utils.logger("null response");
				throw new NetworkException(
						NetworkError.ERROR_CODE_UNKNOWN_ERROR, "null response",
						"null response");
			}
			usersBean = new UserGetNewsResponseBean(response);
		} catch (RuntimeException re) {
			Utils.logger("runtime exception " + re.getMessage());
			throw new Throwable(re);
		}
		return usersBean;
	}

	/**
	 * 异步调用获取用户信息接口<br>
	 * 
	 * @param pool
	 *            线程池
	 * @param param
	 *            请求对象
	 * @param listener
	 *            回调
	 */
	public void asyncGetUserNews(Executor pool,
			final UserGetNewsRequestParam param,
			final AbstractRequestListener<UserGetNewsResponseBean> listener) {

		pool.execute(new Runnable() {

			public void run() {

				try {
					UserGetNewsResponseBean usersBean = getUserNews(param);
					if (listener != null) {
						listener.onComplete(usersBean);
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
