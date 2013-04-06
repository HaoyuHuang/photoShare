/**
 * 
 */
package com.photoshare.pipeline;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.common.RequestParam;
import com.photoshare.exception.ValveException;
import com.photoshare.msg.RequestMsg;
import com.photoshare.pipeline.valve.Valve;
import com.photoshare.pipeline.valve.ValveContext;

/**
 * The Outbound Pipeline is responsible for check the requests messages and
 * forward them to the server via the basic valve.
 * 
 * @author Aron
 * 
 */
public class OutboundPipeline implements Pipeline {

	private static OutboundPipeline pipeline = null;

	public static OutboundPipeline getInstance() {
		if (pipeline == null)
			pipeline = new OutboundPipeline();
		return pipeline;
	}

	private OutboundPipeline() {

	}

	private LinkedList<RequestMsg<? extends RequestParam>> MsgQueue = new LinkedList<RequestMsg<? extends RequestParam>>();

	private List<Valve> valves = new ArrayList<Valve>();

	private Valve basicValve;

	public boolean add(RequestMsg<? extends RequestParam> request,
			final AbstractRequestListener<String> listener) {
		return MsgQueue.add(request);
	}

	public void poll() {
		if (!MsgQueue.peek().isExpired()) {
			MsgQueue.remove();
		}
	}

	public void scanAndDiscard() {
		for (int i = 0; i < MsgQueue.size(); i++) {
			if (MsgQueue.get(i).isSend()) {
				MsgQueue.remove(i);
				i--;
			}
		}
	}

	public void moveToLast() {
		if (MsgQueue.isEmpty() || MsgQueue.peek() == null)
			return;
		MsgQueue.peek().setExpired(true);
		MsgQueue.add(MsgQueue.poll());
	}

	public Valve getBasic() {
		// TODO Auto-generated method stub
		return basicValve;
	}

	public void setBasic(Valve valve) {
		// TODO Auto-generated method stub
		this.basicValve = valve;
	}

	public void addValve(Valve valve) {
		// TODO Auto-generated method stub
		this.valves.add(valve);
	}

	public Collection<Valve> getValves() {
		// TODO Auto-generated method stub
		return this.valves;
	}

	public void removeValve(Valve valve) {
		// TODO Auto-generated method stub
		this.valves.remove(valve);
	}

	public void invoke(RequestMsg<? extends RequestParam> request,
			AbstractRequestListener<String> listener) throws ValveException {

		new SimplePipelineValveContext().invokeNext(request, listener);
	}

	protected class SimplePipelineValveContext implements ValveContext {

		protected int stage = 0;

		public String getInfo() {
			// TODO Auto-generated method stub
			return null;
		}

		public void invokeNext(RequestMsg<? extends RequestParam> request,
				AbstractRequestListener<String> listener) throws ValveException {
			int subscript = stage;
			stage++;

			if (subscript < valves.size()) {
				valves.get(subscript).invoke(request, listener, this);
			} else if ((subscript == valves.size()) && (basicValve != null)) {
				basicValve.invoke(request, listener, this);
			} else {
				throw new ValveException(ValveException.KEY_LOGGING_EXCEPTION,
						ValveException.LOGGING_EXCEPTION, "");
			}
		}
	}
}
