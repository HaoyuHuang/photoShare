package com.photoshare.pipeline;

import java.util.Collection;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.common.RequestParam;
import com.photoshare.exception.ValveException;
import com.photoshare.msg.RequestMsg;
import com.photoshare.pipeline.valve.Valve;

public interface Pipeline {
	public Valve getBasic();

	public void setBasic(Valve valve);

	public void addValve(Valve valve);

	public Collection<Valve> getValves();

	public void removeValve(Valve valve);

	public void invoke(final RequestMsg<? extends RequestParam> request,
			final AbstractRequestListener<String> listener)
			throws ValveException;
}
