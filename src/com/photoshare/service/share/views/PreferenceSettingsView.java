/**
 * 
 */
package com.photoshare.service.share.views;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

import com.photoshare.tabHost.R;
import com.photoshare.view.listview.CornerListView;

/**
 * @author czj_yy
 * 
 */
public class PreferenceSettingsView {

	private View baseView;
	private Context context;
	private CornerListView friendsListView = null;
	private CornerListView actionListView = null;
	private CornerListView profileListView = null;
	private ArrayList<HashMap<String, String>> ProfileList = null;
	private ArrayList<HashMap<String, String>> FriendsList = null;
	private ArrayList<HashMap<String, String>> ActionList = null;

	/**
	 * @param baseView
	 * @param context
	 */
	public PreferenceSettingsView(View baseView, Context context) {
		super();
		this.baseView = baseView;
		this.context = context;
	}

	public void applyView() {
		getFriendsDataSource();
		getActionDataSource();
		getProfileDataSource();
		friendsListView = (CornerListView) baseView
				.findViewById(R.id.personalSettingFriend);
		SimpleAdapter FriendsAdapter = new SimpleAdapter(context, FriendsList,
				R.layout.simple_list_item_arrow, new String[] { "friendItem" },
				new int[] { R.id.item_title });
		friendsListView.setAdapter(FriendsAdapter);
		friendsListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
				case 0:
					if (mCallback != null) {
						mCallback.OnFindFriendClicked();
					}
					break;
				case 1:
					if (mCallback != null) {
						mCallback.OnInviteFriendClicked();
					}
					break;
				case 2:
					if (mCallback != null) {
						mCallback.OnMyFollowersClicked();
					}
					break;
				case 3:
					if (mCallback != null) {
						mCallback.OnMyFollowingClicked();
					}
					break;
				}
			}
		});

		profileListView = (CornerListView) baseView
				.findViewById(R.id.personalSettingPhotos);
		SimpleAdapter ProfileAdapter = new SimpleAdapter(context, ProfileList,
				R.layout.simple_list_item_arrow,
				new String[] { "profileItem" }, new int[] { R.id.item_title });
		profileListView.setAdapter(ProfileAdapter);
		profileListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				switch (position) {
				case 0:
					if (mCallback != null) {
						mCallback.OnLikedPhotosClicked();
					}
					break;
				case 1:
					if (mCallback != null) {
						mCallback.OnMyPhotosClicked();
					}
					break;
				}
			}
		});

		actionListView = (CornerListView) baseView
				.findViewById(R.id.personalSettingAccounts);
		SimpleAdapter ActionAdapter = new SimpleAdapter(context, ActionList,
				R.layout.simple_list_item_arrow, new String[] { "actionItem" },
				new int[] { R.id.item_title });
		actionListView.setAdapter(ActionAdapter);
		actionListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				Log.e("select", "OK!");
				switch (position) {
				case 0:
					if (mCallback != null) {
						mCallback.OnEditProfileClicked();
					}
					break;
				case 1:
					if (mCallback != null) {
						mCallback.OnSharePreferenceClicked();
					}
					break;
				case 2:
					if (mCallback != null) {
						mCallback.OnClearHistoryClicked();
					}
					break;
				case 3:
					if (mCallback != null) {
						mCallback.OnUserPrivacyClicked();
					}
					break;
				case 4:
					if (mCallback != null) {
						mCallback.OnLogoutClicked();
					}
					break;
				}
			}
		});

	}

	private ArrayList<HashMap<String, String>> getFriendsDataSource() {

		FriendsList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		HashMap<String, String> map3 = new HashMap<String, String>();
		HashMap<String, String> map4 = new HashMap<String, String>();
		
		map1.put("friendItem", "寻找朋友");
		map2.put("friendItem", "邀请朋友");
		map3.put("friendItem", "跟随的人");
		map4.put("friendItem", "跟随我的人");

		FriendsList.add(map1);
		FriendsList.add(map2);
		FriendsList.add(map3);
		FriendsList.add(map4);

		return FriendsList;
	}

	private ArrayList<HashMap<String, String>> getActionDataSource() {

		ActionList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		HashMap<String, String> map3 = new HashMap<String, String>();
		HashMap<String, String> map4 = new HashMap<String, String>();
		HashMap<String, String> map5 = new HashMap<String, String>();

		map1.put("actionItem", "修改资料");
		map2.put("actionItem", "分享设置");
		map3.put("actionItem", "清空歷史");
		map4.put("actionItem", "私有设置");
		map5.put("actionItem", "下线");

		ActionList.add(map1);
		ActionList.add(map2);
		ActionList.add(map3);
		ActionList.add(map4);
		ActionList.add(map5);

		return ActionList;
	}

	private ArrayList<HashMap<String, String>> getProfileDataSource() {

		ProfileList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		map1.put("profileItem", "我喜欢的照片");
		map2.put("profileItem", "我的照片");
		ProfileList.add(map1);
		ProfileList.add(map2);
		return ProfileList;
	}

	private ICallback mCallback;

	public void registerCallback(ICallback mCallback) {
		this.mCallback = mCallback;
	}

	public interface ICallback {
		public void OnFindFriendClicked();

		public void OnInviteFriendClicked();

		public void OnMyFollowersClicked();

		public void OnMyFollowingClicked();

		public void OnLikedPhotosClicked();

		public void OnMyPhotosClicked();

		public void OnEditProfileClicked();

		public void OnSharePreferenceClicked();

		public void OnClearHistoryClicked();

		public void OnUserPrivacyClicked();

		public void OnLogoutClicked();
	}
}
