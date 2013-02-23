package com.photoshare.service;

import java.util.concurrent.Executor;

import android.os.Bundle;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.msg.MsgType;
import com.photoshare.msg.RequestMsg;
import com.photoshare.service.comments.CommentsGetInfoRequestParam;
import com.photoshare.service.comments.CommentsGetInfoResponseBean;
import com.photoshare.service.comments.PutCommentRequestParam;
import com.photoshare.service.comments.PutCommentResponseBean;
import com.photoshare.utils.User;
import com.photoshare.utils.Utils;
import com.renren.api.connect.android.exception.RenrenException;

public class CommentHelper {

	private User user;

	public CommentHelper(User user) {
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
	public CommentsGetInfoResponseBean getCommentsInfo(
			CommentsGetInfoRequestParam param) throws NetworkException,
			Throwable {

		Bundle parameters = param.getParams();
		CommentsGetInfoResponseBean commentsBean = null;
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
			commentsBean = new CommentsGetInfoResponseBean(response);
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
	public void asyncGetCommentsInfo(Executor pool,
			final CommentsGetInfoRequestParam param,
			final AbstractRequestListener<CommentsGetInfoResponseBean> listener) {

		pool.execute(new Runnable() {

			public void run() {

				try {
					CommentsGetInfoResponseBean commentsBean = getCommentsInfo(param);
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
	 * 将params 放入管道中进行传输
	 * 
	 * @param params
	 * @param mCallback
	 */
	public void publishComment(PutCommentRequestParam params,
			final ICallback mCallback) {
		if (params == null)
			return;

		// AMsg 及 Listener 将一起放入管道中
		RequestMsg<PutCommentRequestParam> AMsg = new RequestMsg<PutCommentRequestParam>(
				params, MsgType.COMMENT);
		AbstractRequestListener<String> listener = new AbstractRequestListener<String>() {

			@Override
			public void onComplete(String bean) {
				// TODO Auto-generated method stub
				if (mCallback != null) {
					mCallback.OnComplete(new PutCommentResponseBean(bean));
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
		public void OnComplete(PutCommentResponseBean comment);

		public void OnFault(Throwable fault);

		public void OnNetworkError(NetworkError networkError);
	}

}
