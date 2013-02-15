/**
 * 
 */
package com.photoshare.pipeline;

import java.util.ArrayList;
import java.util.LinkedList;

import com.photoshare.common.RequestParam;
import com.photoshare.msg.RequestMsg;

/**
 * @author czj_yy
 * 
 */
public class OutboundPipeline {

	private static OutboundPipeline pipeline = null;

	public static OutboundPipeline getInstance() {
		if (pipeline == null)
			pipeline = new OutboundPipeline();
		return pipeline;
	}

	private OutboundPipeline() {

	}

	private LinkedList<RequestMsg<? extends RequestParam>> MsgQueue = new LinkedList<RequestMsg<? extends RequestParam>>();

	public boolean add(RequestMsg<? extends RequestParam> request) {
		notifySend(request);
		return MsgQueue.add(request);
	}

	public void poll() {
		if (!MsgQueue.peek().isExpired()) {
			MsgQueue.remove();
		}
	}

	public interface Listener {
		public void add(RequestMsg<? extends RequestParam> request);
	}

	private ArrayList<Listener> listeners = new ArrayList<Listener>();

	public void notifySend(RequestMsg<? extends RequestParam> request) {
		for (Listener listener : listeners) {
			listener.add(request);
		}
	}

	public void registerListener(Listener listener) {
		listeners.add(listener);
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
}
