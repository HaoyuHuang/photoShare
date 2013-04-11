package com.photoshare.pipeline.valve;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.common.RequestParam;
import com.photoshare.exception.ValveException;
import com.photoshare.msg.RequestMsg;

/**
 * the valve for validation check
 * 
 * @author Aron
 * 
 */
public class ValidationValve implements Valve {

	public String getInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public void invoke(RequestMsg<? extends RequestParam> request,
			AbstractRequestListener<String> listener, ValveContext context)
			throws ValveException {
		// TODO Auto-generated method stub

	}

}
