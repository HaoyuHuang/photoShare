package com.photoshare.common;

import com.photoshare.udp.TMsg;

public interface MsgListener {
	abstract public void OnReceiveMsg(Object sender, String fromIP,
			int fromPort, TMsg Msg);

	abstract public void OnSendMsg(Object sender, String ToIp, int ToPort,
			TMsg Msg, int TimeOut, boolean FLag);
}
