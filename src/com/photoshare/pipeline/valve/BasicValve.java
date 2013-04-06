/**
 * 
 */
package com.photoshare.pipeline.valve;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.exception.ValveException;
import com.photoshare.msg.MessageList;
import com.photoshare.msg.RequestMsg;
import com.photoshare.pipeline.OutboundPipeline;
import com.photoshare.utils.Utils;
import com.photoshare.utils.async.AsyncUtils;

/**
 * The Basic Valve is responsible for sending the request to the server.
 * 
 * @author Aron
 * 
 */
public class BasicValve implements Valve {

	public BasicValve() {
		
	}

	private AsyncUtils async = AsyncUtils.getInstance();

	private OutboundPipeline pipeline = OutboundPipeline.getInstance();

	private MessageList messageList = MessageList.getInstance();

	public static final int MAX_TRIAL = 3;

	public void addMsg(RequestMsg<? extends RequestParam> AMsg,
			final AbstractRequestListener<String> listener) {
		Utils.logger(AMsg.toString());
		pipeline.add(AMsg, listener);
	}

	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.photoshare.pipeline.valve.Valve#invoke(com.photoshare.msg.RequestMsg,
	 * com.photoshare.common.AbstractRequestListener,
	 * com.photoshare.pipeline.valve.ValveContext)
	 */
	public void invoke(final RequestMsg<? extends RequestParam> request,
			final AbstractRequestListener<String> listener, ValveContext context)
			throws ValveException {
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
					try {
						pipeline.invoke(request, listener);
					} catch (ValveException e) {
						messageList.add(request);
						pipeline.moveToLast();
						e.printStackTrace();
					}
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
			async.request(request.getType().getAction(), request.getAMsg()
					.getParams(), httpListener);
		} catch (NetworkException e) {
			if (listener != null) {
				listener.onNetworkError(new NetworkError(e.getMessage()));
			}
		}

	}

}
