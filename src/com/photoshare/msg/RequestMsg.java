/**
 * 
 */
package com.photoshare.msg;

import com.photoshare.common.RequestParam;

/**
 * @author Aron
 * 
 *         RequestMsg is designed for encapsulate request parameters and used to
 *         resend failed message.
 * 
 */
public class RequestMsg<Type extends RequestParam> {

	public RequestMsg(Type request, MsgType type) {
		this.AMsg = request;
		this.isSend = false;
		this.type = type;
	}

	private Type AMsg;

	private boolean isSend;

	private MsgType type;

	private int trial;

	private boolean expired;

	public Type getAMsg() {
		return AMsg;
	}

	public void setAMsg(Type aMsg) {
		AMsg = aMsg;
	}

	public boolean isSend() {
		return isSend;
	}

	public void setSend(boolean isSend) {
		this.isSend = isSend;
	}

	public void tryAgain() {
		this.trial = this.trial + 1;
	}

	public int getTrial() {
		return trial;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public MsgType getType() {
		return type;
	}

	public void setType(MsgType type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "RequestMsg [AMsg=" + AMsg + ", isSend=" + isSend + ", type="
				+ type + ", trial=" + trial + ", expired=" + expired + "]";
	}

}
