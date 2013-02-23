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
import com.photoshare.service.likes.LikeGetInfoRequestParam;
import com.photoshare.service.likes.LikeGetInfoResponseBean;
import com.photoshare.service.likes.PhotoLikeRequestParam;
import com.photoshare.service.likes.PhotoLikeResponseBean;
import com.photoshare.utils.User;
import com.photoshare.utils.Utils;
import com.renren.api.connect.android.exception.RenrenException;

/**
 * @author Aron
 * 
 */
public class LikeHelper {
	private User user;

	public LikeHelper(User user) {
		this.user = user;
	}

	/**
	 * 获取指定请求对象的CommentsResponseBean<br>
	 * 
	 * @param param
	 *            请求对象
	 * @return 返回服务器响应的CommentInfoBean
	 * @throws RenrenException
	 */
	public LikeGetInfoResponseBean getLikeInfo(LikeGetInfoRequestParam param)
			throws NetworkException, Throwable {

		Bundle parameters = param.getParams();
		LikeGetInfoResponseBean commentsBean = null;
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
			commentsBean = new LikeGetInfoResponseBean(response);
		} catch (RuntimeException re) {
			Utils.logger("runtime exception " + re.getMessage());
			throw new Throwable(re);
		}
		return commentsBean;
	}

	/**
	 * 异步获取指定请求的CommentsResponseBean<br>
	 * 
	 * @param pool
	 *            线程池
	 * @param param
	 *            请求对象
	 * @param listener
	 *            回调
	 */
	public void asyncGetLikesInfo(Executor pool,
			final LikeGetInfoRequestParam param,
			final AbstractRequestListener<LikeGetInfoResponseBean> listener) {

		pool.execute(new Runnable() {

			public void run() {

				try {
					LikeGetInfoResponseBean commentsBean = getLikeInfo(param);
					if (listener != null) {
						listener.onComplete(commentsBean);
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

	/**
	 * 将param 放入管道中进行传输
	 * 
	 * @param param
	 * @param mCallback
	 */
	public void publishLikePhoto(PhotoLikeRequestParam params,
			final ICallback mCallback) {
		
		if (params == null)
			return;
		
		Utils.logger(params.toString());
		
		// AMsg 及 Listener 将一起放入管道中
		RequestMsg<PhotoLikeRequestParam> AMsg = new RequestMsg<PhotoLikeRequestParam>(
				params, MsgType.LIKE);
		
		AbstractRequestListener<String> listener = new AbstractRequestListener<String>() {

			@Override
			public void onComplete(String bean) {
				// TODO Auto-generated method stub
				if (mCallback != null) {
					mCallback.OnComplete(new PhotoLikeResponseBean(bean));
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
		public void OnComplete(PhotoLikeResponseBean bean);

		public void OnNetworkError(NetworkError error);

		public void OnFault(Throwable fault);
	}

}
