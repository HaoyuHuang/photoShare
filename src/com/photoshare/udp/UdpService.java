package com.photoshare.udp;

import java.net.DatagramSocket;
import java.util.UUID;

@Deprecated
public class UdpService {

	public static String getGuid() {
		return UUID.randomUUID().toString().replace("-", "");
	}

	public static void DoFreeSocket(DatagramSocket skt) {
		if (skt != null) {
			try {
				skt.disconnect();
			} catch (Exception e) {
				System.out.print(e.toString());
			}
			try {
				skt.close();
			} catch (Exception e) {
				System.out.print(e.toString());
			}
			skt = null;
		}
	}

}
