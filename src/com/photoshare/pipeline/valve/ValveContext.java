package com.photoshare.pipeline.valve;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.common.RequestParam;
import com.photoshare.exception.ValveException;
import com.photoshare.msg.RequestMsg;

public interface ValveContext {
	public String getInfo();

	public void invokeNext(final RequestMsg<? extends RequestParam> request,
			final AbstractRequestListener<String> listener)
			throws ValveException;
}
