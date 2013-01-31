package com.photoshare.udp;

@Deprecated
public class TMsg {

	public static final int MsgHeadFlag = 147258369;

	public static final String USER_ID = "id";
	public static final String CONNECTED_ID = "toid";
	public static final String COMMAND_ID = "cmdid";
	public static final String DATA = "data";

	public static final int CmdId_None = 0;
	public static final int REQ_LOG = 101;
	public static final int RSP_SUCCESS = 102;
	public static final int RSP_FAIL = 103;

	public static final int REQ_FOLLOWER_ID = 104;
	public static final int RSP_FOLLOWER_ID = 105;

	public static final int REQ_FOLLOW_SOMEONE = 106;
	public static final int RSP_FOLLOW_SOMEONE = 107;

	public static final int REQ_COMMENT = 108;
	public static final int RSP_COMMENT = 109;

	public static final int REQ_LIKE_PHOTO = 110;
	public static final int RSP_LIKE_PHOTO = 111;

	public static final int REQ_EDIT_PROFILE = 112;
	public static final int RSP_EDIT_PROFILE = 113;

	private int CmdId = TMsg.CmdId_None;
	private String MsgGuid = UdpService.getGuid();
	private long UserId;
	private long ConnectId;
	private byte[] data;

	public int getCmdId() {
		return CmdId;
	}

	public void setCmdId(int cmdId) {
		CmdId = cmdId;
	}

	public long getUserId() {
		return UserId;
	}

	public void setUserId(long userId) {
		UserId = userId;
	}

	public long getConnectId() {
		return ConnectId;
	}

	public void setConnectId(long connectId) {
		ConnectId = connectId;
	}

	public byte[] getData() {
		return data;
	}

	public void setData(byte[] data) {
		this.data = data;
	}

	public String getMsgGuid() {
		return MsgGuid;
	}

	public void setMsgGuid(String msgGuid) {
		MsgGuid = msgGuid;
	}

	/*
	 * 返回消息字节数组
	 */
	public byte[] getBytes() {

		int ABodySize = 0;
		if (data != null) {
			ABodySize = data.length;
		}
		int capacity = 4 + 4 + 32 + 8 + 8 + 4 + ABodySize;
		byte[] AResBytes = new byte[capacity];

		int offSet = 0;

		byte[] src = TMsg.intToBytes(MsgHeadFlag);
		System.arraycopy(src, 0, AResBytes, offSet, src.length);
		offSet += 4;

		src = TMsg.intToBytes(CmdId);
		System.arraycopy(src, 0, AResBytes, offSet, src.length);
		offSet += 4;

		src = MsgGuid.getBytes();
		System.arraycopy(src, 0, AResBytes, offSet, src.length);
		offSet += 32;

		src = TMsg.longToByte(UserId);
		System.arraycopy(src, 0, AResBytes, offSet, src.length);
		offSet += 8;

		src = TMsg.longToByte(ConnectId);
		System.arraycopy(src, 0, AResBytes, offSet, src.length);
		offSet += 8;

		src = TMsg.intToBytes(ABodySize);
		System.arraycopy(src, 0, AResBytes, offSet, src.length);
		offSet += 4;

		if (data != null) {
			System.arraycopy(data, 0, AResBytes, offSet, data.length);
		}

		return AResBytes;
	}

	public static TMsg getMsg(byte[] ABytes) {

		TMsg AMsg = new TMsg();
		try {
			int offset = 0;

			int AHead = TMsg
					.bytesToInt(copyOfRange(ABytes, offset, offset + 4));
			offset += 4;

			System.out.println(AHead);

			if (AHead == TMsg.MsgHeadFlag) {

				AMsg.setCmdId(TMsg.bytesToInt(copyOfRange(ABytes, offset,
						offset + 4)));
				offset += 4;

				AMsg.setMsgGuid(new String(copyOfRange(ABytes, offset,
						offset + 32)));
				offset += 32;

				AMsg.setUserId(TMsg.bytesToLong(copyOfRange(ABytes, offset,
						offset + 8)));
				offset += 8;

				AMsg.setConnectId(TMsg.bytesToLong(copyOfRange(ABytes, offset,
						offset + 8)));
				offset += 8;

				int bodysize = TMsg.bytesToInt(copyOfRange(ABytes, offset,
						offset + 4));
				offset += 4;
				if (bodysize != 0) {
					AMsg.data = new byte[bodysize];
					System.arraycopy(ABytes, offset, AMsg.data, 0, bodysize);
				}
			}
		} catch (Exception e) {
			System.out.print(e.toString());
		}
		return AMsg;
	}

	public static byte[] intToBytes(int num) {
		byte[] b = new byte[4];
		for (int i = 0; i < 4; i++) {
			b[i] = (byte) (num >>> (24 - i * 8));
		}
		return b;
	}

	// long类型转成byte数组
	public static byte[] longToByte(long number) {
		long temp = number;
		byte[] b = new byte[8];
		for (int i = 0; i < b.length; i++) {
			b[i] = new Long(temp & 0xff).byteValue();
			temp = temp >> 8; // 向右移8位
		}
		return b;
	}

	// byte数组转成long
	public static long bytesToLong(byte[] b) {
		long s = 0;
		long s0 = b[0] & 0xff;// 最低位
		long s1 = b[1] & 0xff;
		long s2 = b[2] & 0xff;
		long s3 = b[3] & 0xff;
		long s4 = b[4] & 0xff;// 最低位
		long s5 = b[5] & 0xff;
		long s6 = b[6] & 0xff;
		long s7 = b[7] & 0xff;

		// s0不变
		s1 <<= 8;
		s2 <<= 16;
		s3 <<= 24;
		s4 <<= 8 * 4;
		s5 <<= 8 * 5;
		s6 <<= 8 * 6;
		s7 <<= 8 * 7;
		s = s0 | s1 | s2 | s3 | s4 | s5 | s6 | s7;
		return s;
	}

	public static int bytesToInt(byte[] b) {

		int mask = 0xff;
		int temp = 0;
		int res = 0;
		for (int i = 0; i < 4; i++) {
			res <<= 8;
			temp = b[i] & mask;
			res |= temp;
		}
		return res;
	}

	public static byte[] copyOfRange(byte[] origin, int offset, int length) {
		byte[] to = new byte[length];
		for (int i = offset; i < length; i++) {
			to[i - offset] = origin[i];
		}
		return to;
	}

	public static boolean checkTMsg(TMsg send, TMsg receive) {
		if (send.equals(receive) && send.getCmdId() + 1 == receive.getCmdId()) {
			return true;
		}
		return false;
	}

	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		TMsg msg = (TMsg) obj;
		if (msg.getUserId() == msg.getUserId()
				&& msg.getConnectId() == this.getConnectId()
				&& msg.getMsgGuid().equals(this.getMsgGuid())
				&& msg.getData().equals(this.getData())) {
			return true;
		}
		return false;
	}

}
