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
import com.photoshare.utils.async.AsyncUtils;

/**
 * @author Aron
 * 
 */
public class PipelineMsgHandler {

	public PipelineMsgHandler() {
		register();
	}

	private AsyncUtils async;

	private OutboundPipeline pipeline = OutboundPipeline.getInstance();

	private MessageList messageList = MessageList.getInstance();

	private AbstractRequestListener<String> listener;

	public static final int MAX_TRIAL = 3;

	private OutboundPipeline.Listener msgListener = new OutboundPipeline.Listener() {

		public void add(RequestMsg<? extends RequestParam> request) {
			// TODO Auto-generated method stub
			asyncPublishRequest(request, listener);
		}
	};

	private void asyncPublishRequest(
			RequestMsg<? extends RequestParam> request,
			AbstractRequestListener<String> listener) {
		if (request == null)
			return;
		String resp = null;
		try {
			resp = async.request(request.getType().getAction(), request
					.getAMsg().getParams());
			request.setSend(true);
			pipeline.scanAndDiscard();
			if (listener != null) {
				listener.onComplete(resp);
			}
		} catch (NetworkError e) {
			// TODO Auto-generated catch block
			if (listener != null) {
				listener.onNetworkError(e);
			}
		} catch (RuntimeException e) {
			// TODO Auto-generated catch block
			if (listener != null) {
				listener.onFault(e);
			}
			if (request.getTrial() <= MAX_TRIAL) {
				request.tryAgain();
				pipeline.notifySend(request);
			} else {
				MessageList.getInstance().add(request);
				pipeline.moveToLast();
			}

		} catch (NetworkException e) {

		}
	}

	public void addMsg(RequestMsg<? extends RequestParam> AMsg) {
		pipeline.add(AMsg);
	}

	private void register() {
		pipeline.registerListener(msgListener);
	}

	public void registerRequestListener(AbstractRequestListener<String> listener) {
		this.listener = listener;
	}

}
