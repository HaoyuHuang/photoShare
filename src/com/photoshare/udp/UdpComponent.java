package com.photoshare.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.concurrent.Executor;

import com.photoshare.common.MsgListener;

@Deprecated
public class UdpComponent {

	public DatagramSocket recSkt = null;

	private int port = 1000;

	private Boolean Running = false;

	private MsgListener msgListener;

	public UdpComponent(MsgListener mg) {
		// msgListener = (MsgListener)proxy.getProxyer(new OnMsgListener());
		msgListener = mg;
	}

	public void doStart() {

		if (!Running) {
			try {
				UdpService.DoFreeSocket(recSkt);
				InetAddress address = InetAddress.getLocalHost();
				recSkt = new DatagramSocket(port, address);

				Running = true;

				RecThread thread = new RecThread();
				thread.setMsgListener(msgListener);
				thread.setDaemon(true);
				thread.setRecSkt(recSkt);
				thread.setRunning(Running);
				thread.setUdpComm(this);
				thread.start();
			} catch (Exception e) {

			}
		}
		doStop();
	}

	public void doStop() {
		if (Running) {
			Running = false;
			UdpService.DoFreeSocket(recSkt);
		}
	}

	public boolean DoSendMsg(Object sender, String ToIp, int ToPort, TMsg Msg,
			int TimeOut) {
		boolean Rec = false;
		try {
			DatagramSocket skt = new DatagramSocket();
			skt.setSoTimeout(TimeOut);
			InetSocketAddress Address = new InetSocketAddress(ToIp, ToPort);
			byte[] bytes = Msg.getBytes();
			try {
				DatagramPacket p = new DatagramPacket(bytes, bytes.length,
						Address.getAddress(), Address.getPort());
				skt.send(p);
				Rec = true;
				msgListener.OnSendMsg(this, ToIp, ToPort, Msg, TimeOut, Rec);
			} catch (SocketTimeoutException ste) {
				UdpService.DoFreeSocket(skt);
			} catch (IOException e) {

			}

			UdpService.DoFreeSocket(skt);
			msgListener.OnSendMsg(sender, ToIp, ToPort, Msg, TimeOut, true);
		} catch (SocketException e) {

		}
		return Rec;
	}

	public void AsncSendMsg(Executor pool, final String ToIp, final int ToPort,
			final TMsg Msg, final int TimeOut) {

		AsnycSendRunnable runnable = new AsnycSendRunnable();
		runnable.setMsg(Msg);
		runnable.setMsgListener(msgListener);
		runnable.setTimeOut(TimeOut);
		runnable.setToIp(ToIp);
		runnable.setToPort(ToPort);
		pool.execute(runnable);
	}

}

class RecThread extends Thread {

	private UdpComponent udpComm = null;

	private boolean Running;

	private DatagramSocket recSkt = null;

	private MsgListener msgListener;

	public UdpComponent getUdpComm() {
		return udpComm;
	}

	public void setUdpComm(UdpComponent udpComm) {
		this.udpComm = udpComm;
	}

	public boolean isRunning() {
		return Running;
	}

	public void setRunning(boolean running) {
		Running = running;
	}

	public DatagramSocket getRecSkt() {
		return recSkt;
	}

	public void setRecSkt(DatagramSocket recSkt) {
		this.recSkt = recSkt;
	}

	public MsgListener getMsgListener() {
		return msgListener;
	}

	public void setMsgListener(MsgListener msgListener) {
		this.msgListener = msgListener;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		byte[] buffer = new byte[1024];

		while (Running) {
			DatagramPacket p = new DatagramPacket(buffer, buffer.length);
			try {
				recSkt.receive(p);
				TMsg AMsg = TMsg.getMsg(buffer);
				if (AMsg != null) {
					msgListener.OnReceiveMsg(this, p.getAddress()
							.getHostAddress(), p.getPort(), AMsg);
				}

			} catch (IOException e) {

			}
		}
	}
}

class AsnycSendRunnable implements Runnable {

	private int TimeOut;

	private String ToIp;

	private int ToPort;

	private TMsg Msg;

	private MsgListener msgListener;

	public int getTimeOut() {
		return TimeOut;
	}

	public void setTimeOut(int timeOut) {
		TimeOut = timeOut;
	}

	public String getToIp() {
		return ToIp;
	}

	public void setToIp(String toIp) {
		ToIp = toIp;
	}

	public int getToPort() {
		return ToPort;
	}

	public void setToPort(int toPort) {
		ToPort = toPort;
	}

	public TMsg getMsg() {
		return Msg;
	}

	public void setMsg(TMsg msg) {
		Msg = msg;
	}

	public MsgListener getMsgListener() {
		return msgListener;
	}

	public void setMsgListener(MsgListener msgListener) {
		this.msgListener = msgListener;
	}

	public void run() {
		// TODO Auto-generated method stub
		boolean recFlag = false;

		try {
			DatagramSocket skt = new DatagramSocket();
			skt.setSoTimeout(TimeOut);
			InetSocketAddress Address = new InetSocketAddress(ToIp, ToPort);
			byte[] bytes = Msg.getBytes();
			try {
				DatagramPacket packet = new DatagramPacket(bytes, bytes.length,
						Address.getAddress(), Address.getPort());
				skt.send(packet);
				recFlag = true;
			} catch (SocketTimeoutException ste) {

			} catch (IOException e) {

			} finally {
				msgListener
						.OnSendMsg(this, ToIp, ToPort, Msg, TimeOut, recFlag);
			}
			UdpService.DoFreeSocket(skt);
		} catch (SocketException e) {

		}
	}

}
