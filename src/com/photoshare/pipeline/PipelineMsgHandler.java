/**
 * 
 */
package com.photoshare.pipeline;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.msg.MessageList;
import com.photoshare.msg.RequestMsg;
import com.photoshare.utils.Utils;
import com.photoshare.utils.async.AsyncUtils;

/**
 * @author Aron
 * 
 */
public class PipelineMsgHandler {

	public PipelineMsgHandler() {
		register();
	}

	private AsyncUtils async = AsyncUtils.getInstance();

	private OutboundPipeline pipeline = OutboundPipeline.getInstance();

	private MessageList messageList = MessageList.getInstance();

	public static final int MAX_TRIAL = 3;

	private OutboundPipeline.Listener msgListener = new OutboundPipeline.Listener() {

		public void onFreshMsgBoard(RequestMsg<? extends RequestParam> request,
				AbstractRequestListener<String> listener) {
			asyncPublishRequest(request, listener);
		}
	};

	private void asyncPublishRequest(
			final RequestMsg<? extends RequestParam> request,
			final AbstractRequestListener<String> listener) {
		Utils.logger("asyncPublishRequest");
		async = AsyncUtils.getInstance();
		if (request == null)
			return;
		final AbstractRequestListener<String> httpListener = new AbstractRequestListener<String>() {

			@Override
			public void onNetworkError(NetworkError networkError) {
				// TODO Auto-generated method stub
				if (listener != null) {
					listener.onNetworkError(networkError);
				}
			}

			@Override
			public void onFault(Throwable fault) {
				// TODO Auto-generated method stub
				if (listener != null) {
					listener.onFault(fault);
				}
				if (request.getTrial() <= MAX_TRIAL) {
					request.tryAgain();
					pipeline.notifySendToTargetHandler(request, listener);
				} else {
					messageList.add(request);
					pipeline.moveToLast();
				}
			}

			@Override
			public void onComplete(String bean) {
				// TODO Auto-generated method stub
				request.setSend(true);
				pipeline.scanAndDiscard();
				if (listener != null) {
					listener.onComplete(bean);
				}
			}
		};

		try {
			if (async != null) {
				Utils.logger("async not null-----------------");
			}
			
			if (request.getType() != null) {
				Utils.logger(request.getType().getAction() + " not null-----------------");
			}
			
			if (request.getAMsg() != null) {
				Utils.logger("amsg not null-----------------");
			}
			
			async.request(request.getType().getAction(), request.getAMsg()
					.getParams(), httpListener);
		} catch (NetworkException e) {
			if (listener != null) {
				listener.onNetworkError(new NetworkError(e.getMessage()));
			}
		}

	}

	public void addMsg(RequestMsg<? extends RequestParam> AMsg,
			final AbstractRequestListener<String> listener) {
		Utils.logger(AMsg.toString());
		pipeline.add(AMsg, listener);
	}

	private void register() {
		pipeline.registerListener(msgListener);
	}

}
