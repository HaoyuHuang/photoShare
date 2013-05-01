/**
 * 
 */
package com.photoshare.service.follow;

import android.os.Bundle;

import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.users.UserInfo;

/**
 * @author czj_yy
 * 
 */
public class UserGetFollowInfoRequestParam extends RequestParam {

	private long uid;

	public static final String FIELDS_DEFAULT = UserInfo.KEY_TINY_HEAD_URL
			+ "," + UserInfo.KEY_NAME + "," + UserInfo.KEY_PSEUDO_NAME + ","
			+ UserInfo.KEY_UID;
	/**
	 * 所有字段
	 */
	public static final String FIELDS_ALL = UserInfo.KEY_UID + ","
			+ UserInfo.KEY_NAME + "," + UserInfo.KEY_GENDER + ","
			+ UserInfo.KEY_WEBSITE + "," + UserInfo.KEY_BIO + ","
			+ UserInfo.KEY_BIRTHDAY + "," + UserInfo.KEY_PHONE_NUMBER + ","
			+ UserInfo.KEY_PRIVACY + "," + UserInfo.KEY_TINY_HEAD_URL + ","
			+ UserInfo.KEY_MIDDLE_HEAD_URL + "," + UserInfo.KEY_LARGE_HEAD_URL;

	private String fields = FIELDS_DEFAULT;

	private FollowAction type;

	private int currentPage;

	private int demandPage;

	private int datediff;

	public static final String KEY_CURRENT_PAGE = "currentPage";

	public static final String KEY_DEMAND_PAGE = "demandPage";

	public static final String KEY_DATE_DIFF = "datediff";

	@Deprecated
	public String getMethod() {
		return "userFollowGetInfo.do" + "?method=" + type;
	}

	private static final String ACTION = "/FollowGetInfoAction_";

	public String getAction() {
		return ACTION + type.getTag();
	}

	/**
	 * @param ids
	 */
	public UserGetFollowInfoRequestParam(long ids) {
		super();
		this.uid = ids;
	}

	/**
	 * @param ids
	 * @param fields
	 */
	public UserGetFollowInfoRequestParam(long ids, String fields) {
		super();
		this.uid = ids;
		this.fields = fields;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.RequestParam#getParams()
	 */
	@Override
	public Bundle getParams() throws NetworkException {
		Bundle bundle = new Bundle();
		if (fields != null) {
			bundle.putString("fields", fields);
		}
		bundle.putString(UserInfo.KEY_USER_INFO + "." + UserInfo.KEY_UID,
				String.valueOf(uid));
		if (type != null) {
			bundle.putString("method", type.toString());
			switch (type) {
			case DATED_FOLLOWER:
			case DATED_FOLLOWING:
				bundle.putString(KEY_DATE_DIFF, String.valueOf(datediff));
				break;
			case FOLLOWER:
			case FOLLOWING:
				bundle.putString(UserInfo.KEY_USER_INFO + "."
						+ KEY_CURRENT_PAGE, String.valueOf(currentPage));
				bundle.putString(
						UserInfo.KEY_USER_INFO + "." + KEY_DEMAND_PAGE,
						String.valueOf(demandPage));
				break;
			default:
				break;
			}
		}
		return bundle;
	}

	public FollowAction getType() {
		return type;
	}

	public void setType(FollowAction type) {
		this.type = type;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getDemandPage() {
		return demandPage;
	}

	public void setDemandPage(int demandPage) {
		this.demandPage = demandPage;
	}

	public int getDatediff() {
		return datediff;
	}

	public void setDatediff(int datediff) {
		this.datediff = datediff;
	}

}
