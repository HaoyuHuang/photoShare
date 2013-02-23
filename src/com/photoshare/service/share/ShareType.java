/**
 * 
 */
package com.photoshare.service.share;

/**
 * @author czj_yy
 * 
 */
public enum ShareType {
	NULL(0, "", "", "") {
		@Override
		public void setAppId(String appId) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setApiKey(String apiKey) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setSecretKey(String secretKey) {
			// TODO Auto-generated method stub

		}
	},
	RenRen(1, "105381", "6b1016db20c540e78bd1b20be4c707a3",
			"4723a695c09e4ddebbe8d87393d95fb4") {
		@Override
		public void setAppId(String appId) {
			// TODO Auto-generated method stub
		}

		@Override
		public void setApiKey(String apiKey) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setSecretKey(String secretKey) {
			// TODO Auto-generated method stub

		}
	},
	
	// 3508959734
	// demo: 966056985
	SinaWeibo(2, "3508959734", "966056985",
			"639f56698089d08847ed4754d0ebfc19") {
		@Override
		public void setAppId(String appId) {
			// TODO Auto-generated method stub
			this.appId = "188602";
		}

		@Override
		public void setApiKey(String apiKey) {
			// TODO Auto-generated method stub
			this.apiKey = "966056985";
		}

		@Override
		public void setSecretKey(String secretKey) {
			// TODO Auto-generated method stub
			this.secretKey = "639f56698089d08847ed4754d0ebfc19";
		}
	},
	TxWeibo(3, "188602", "989d893cd06e4c9da30b0f28161361fd",
			"f270c6d5e0b54c4aab379574c5247f26") {
		@Override
		public void setAppId(String appId) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void setApiKey(String apiKey) {
			// TODO Auto-generated method stub

		}

		@Override
		public void setSecretKey(String secretKey) {
			// TODO Auto-generated method stub

		}
	};

	private int type;

	public String appId;
	public String apiKey;
	public String secretKey;

	/**
	 * @param type
	 */
	private ShareType(int type, String appId, String apiKey, String secretKey) {
		this.type = type;
		this.apiKey = apiKey;
		this.appId = appId;
		this.secretKey = secretKey;
	}

	public int getType() {
		return type;
	}

	public String getAppId() {
		return appId;
	}

	public String getApiKey() {
		return apiKey;
	}

	public String getSecretKey() {
		return secretKey;
	}

	public abstract void setAppId(String appId);

	public abstract void setApiKey(String apiKey);

	public abstract void setSecretKey(String secretKey);

	public static ShareType Switch(int type) {
		switch (type) {
		case 1:
			return RenRen;
		case 2:
			return SinaWeibo;
		case 3:
			return TxWeibo;
		default:
			return NULL;
		}
	}

}
