package com.photoshare.pipeline.valve;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.common.RequestParam;
import com.photoshare.exception.ValveException;
import com.photoshare.msg.RequestMsg;
import com.photoshare.pipeline.OutboundPipeline;

/**
 * The Stored Valve is responsible for storing the request message for later
 * use.
 * 
 * @author Aron
 * 
 */
public class StoreValve implements Valve {

	public String getInfo() {
		return null;
	}

	public void invoke(RequestMsg<? extends RequestParam> request,
			AbstractRequestListener<String> listener, ValveContext context)
			throws ValveException {
		OutboundPipeline.getInstance().add(request, listener);
	}

}
