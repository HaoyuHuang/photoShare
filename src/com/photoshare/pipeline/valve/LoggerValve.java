package com.photoshare.pipeline.valve;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.common.RequestParam;
import com.photoshare.exception.ValveException;
import com.photoshare.msg.RequestMsg;
import com.photoshare.utils.Utils;

/**
 * the valve for log the request message info.
 * 
 * @author Aron
 * 
 */
public class LoggerValve implements Valve {

	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void invoke(RequestMsg<? extends RequestParam> request,
			AbstractRequestListener<String> listener, ValveContext context)
			throws ValveException {
		context.invokeNext(request, listener);
		Utils.logger(request.toString());
	}

}
