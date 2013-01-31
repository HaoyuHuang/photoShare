package com.photoshare.service;

import java.util.concurrent.Executor;

import android.os.Bundle;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.users.UserEditInfoRequestParam;
import com.photoshare.service.users.UserGetInfoRequestParam;
import com.photoshare.service.users.UserGetInfoResponseBean;
import com.photoshare.service.users.UserGetOtherInfoRequestParam;
import com.photoshare.service.users.UserPrivacyRequestParam;
import com.photoshare.service.users.UserPrivacyResponseBean;
import com.photoshare.utils.User;
import com.photoshare.utils.Utils;
import com.renren.api.connect.android.exception.RenrenException;

public class UserInfoHelper {

	/**
	 * User对象
	 */
	private User user;

	public UserInfoHelper(User user) {
		this.user = user;
	}

	/**
	 * 同步调用getUsersInfo接口<br>
	 * 
	 * @param param
	 *            请求对象
	 * @return 返回服务器响应的UserInfoBean
	 * @throws RenrenException
	 */
	public UserGetInfoResponseBean getUsersInfo(UserGetInfoRequestParam param)
			throws NetworkException, Throwable {

		Bundle parameters = param.getParams();
		UserGetInfoResponseBean usersBean = null;
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
			usersBean = new UserGetInfoResponseBean(response);
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
	public void asyncGetUsersInfo(Executor pool,
			final UserGetInfoRequestParam param,
			final AbstractRequestListener<UserGetInfoResponseBean> listener) {

		pool.execute(new Runnable() {

			public void run() {

				try {
					UserGetInfoResponseBean usersBean = getUsersInfo(param);
					if (listener != null) {
						listener.onComplete(usersBean);
					}
				} catch (NetworkException e) {
					Utils.logger("network exception " + e.getMessage());
					if (listener != null) {
						listener.onNetworkError(new NetworkError(e.getMessage()));
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

	/**
	 * 同步调用getUsersInfo接口<br>
	 * 
	 * @param param
	 *            请求对象
	 * @return 返回服务器响应的UserInfoBean
	 * @throws RenrenException
	 */
	public UserPrivacyResponseBean setPrivacy(UserPrivacyRequestParam param)
			throws NetworkException, Throwable {

		Bundle parameters = param.getParams();
		UserPrivacyResponseBean usersBean = null;
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
			usersBean = new UserPrivacyResponseBean(response);
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
	public void asyncSetPrivacy(Executor pool,
			final UserPrivacyRequestParam param,
			final AbstractRequestListener<UserPrivacyResponseBean> listener) {

		pool.execute(new Runnable() {

			public void run() {

				try {
					UserPrivacyResponseBean usersBean = setPrivacy(param);
					if (listener != null) {
						listener.onComplete(usersBean);
					}
				} catch (NetworkException e) {
					Utils.logger("network exception " + e.getMessage());
					if (listener != null) {
						listener.onNetworkError(new NetworkError(e.getMessage()));
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

	/**
	 * 同步调用getUsersInfo接口<br>
	 * 
	 * @param param
	 *            请求对象
	 * @return 返回服务器响应的UserInfoBean
	 * @throws RenrenException
	 */
	public UserGetInfoResponseBean getOthersInfo(
			UserGetOtherInfoRequestParam param) throws NetworkException,
			Throwable {

		Bundle parameters = param.getParams();
		UserGetInfoResponseBean usersBean = null;
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
			usersBean = new UserGetInfoResponseBean(response);
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
	public void asyncGetOthersInfo(Executor pool,
			final UserGetOtherInfoRequestParam param,
			final AbstractRequestListener<UserGetInfoResponseBean> listener) {

		pool.execute(new Runnable() {

			public void run() {

				try {
					UserGetInfoResponseBean usersBean = getOthersInfo(param);
					if (listener != null) {
						listener.onComplete(usersBean);
					}
				} catch (NetworkException e) {
					Utils.logger("network exception " + e.getMessage());
					if (listener != null) {
						listener.onNetworkError(new NetworkError(e.getMessage()));
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

	public UserGetInfoResponseBean SubmitUsersInfo(
			UserEditInfoRequestParam param) throws NetworkException, Throwable {

		Bundle parameters = param.getParams();
		UserGetInfoResponseBean usersBean = null;
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
			usersBean = new UserGetInfoResponseBean(response);
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
	public void asyncEditUsersInfo(Executor pool,
			final UserEditInfoRequestParam param,
			final AbstractRequestListener<UserGetInfoResponseBean> listener) {

		pool.execute(new Runnable() {

			public void run() {

				try {
					UserGetInfoResponseBean usersBean = SubmitUsersInfo(param);
					if (listener != null) {
						listener.onComplete(usersBean);
					}
				} catch (NetworkException e) {
					Utils.logger("network exception " + e.getMessage());
					if (listener != null) {
						listener.onNetworkError(new NetworkError(e.getMessage()));
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
