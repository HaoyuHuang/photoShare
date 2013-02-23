/**
 * 
 */
package com.photoshare.service;

import java.util.concurrent.Executor;

import android.os.Bundle;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.msg.MsgType;
import com.photoshare.msg.RequestMsg;
import com.photoshare.service.follow.UserFollowRequestParam;
import com.photoshare.service.follow.UserFollowResponseBean;
import com.photoshare.service.follow.UserGetFollowInfoRequestParam;
import com.photoshare.service.follow.UserGetFollowInfoResponseBean;
import com.photoshare.utils.User;
import com.photoshare.utils.Utils;
import com.renren.api.connect.android.exception.RenrenException;

/**
 * @author Aron
 * 
 */
public class FollowHelper {

	private User user;

	public FollowHelper(User user) {
		this.user = user;
	}

	/**
	 * 将param 放入管道进行传输
	 * 
	 * @param param
	 * @param mCallback
	 */
	public void publishFollow(UserFollowRequestParam param,
			final ICallback mCallback) {

		if (param == null)
			return;
		RequestMsg<UserFollowRequestParam> AMsg = new RequestMsg<UserFollowRequestParam>(
				param, MsgType.FOLLOW);

		AbstractRequestListener<String> listener = new AbstractRequestListener<String>() {

			@Override
			public void onComplete(String bean) {
				// TODO Auto-generated method stub
				if (mCallback != null) {
					mCallback.OnComplete(new UserFollowResponseBean(bean));
				}
			}

			@Override
			public void onNetworkError(NetworkError networkError) {
				// TODO Auto-generated method stub
				if (mCallback != null) {
					mCallback.OnNetworkError(networkError);
				}
			}

			@Override
			public void onFault(Throwable fault) {
				// TODO Auto-generated method stub
				if (mCallback != null) {
					mCallback.OnFault(fault);
				}
			}

		};
		user.addMsg(AMsg, listener);
	}

	public interface ICallback {
		public void OnComplete(UserFollowResponseBean bean);

		public void OnNetworkError(NetworkError error);

		public void OnFault(Throwable fault);
	}

	/**
	 * 同步调用getUsersInfo接口<br>
	 * 
	 * @param param
	 *            请求对象
	 * @return 返回服务器响应的UserInfoBean
	 * @throws RenrenException
	 */
	public UserGetFollowInfoResponseBean getFollowInfo(
			UserGetFollowInfoRequestParam param) throws NetworkException,
			Throwable {

		Bundle parameters = param.getParams();
		UserGetFollowInfoResponseBean usersBean = null;
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
			usersBean = new UserGetFollowInfoResponseBean(response);
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
	public void asyncGetFollowInfo(
			Executor pool,
			final UserGetFollowInfoRequestParam param,
			final AbstractRequestListener<UserGetFollowInfoResponseBean> listener) {

		pool.execute(new Runnable() {

			public void run() {

				try {
					UserGetFollowInfoResponseBean usersBean = getFollowInfo(param);
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
