package com.photoshare.udp;

import com.photoshare.common.MsgListener;
import com.photoshare.tabHost.TabHostActivity;

@Deprecated
public class OnMsgListener implements MsgListener {

	private TabHostActivity baseActivity;

	public OnMsgListener(TabHostActivity baseActivity) {
		this.baseActivity = baseActivity;
	}

	public void OnReceiveMsg(Object sender, String fromIP, int fromPort,
			TMsg Msg) {
		// TODO Auto-generated method stub
		int responseCode = Msg.getCmdId();

		switch (responseCode) {

		}

	}

	public void OnSendMsg(Object sender, String ToIp, int ToPort, TMsg Msg,
			int TimeOut, boolean Flag) {
		// TODO Auto-generated method stub

	}

}
