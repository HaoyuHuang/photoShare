/**
 * 
 */
package com.photoshare.service.users.views;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.view.listview.CornerListView;

/**
 * @author czj_yy
 * 
 */
public class UserInfoView {

	private Context context;
	private View baseView;
	private CornerListView mPublicProfileView = null;
	private CornerListView mPrivateProfileView = null;
	private ArrayList<HashMap<String, String>> PublicProfileDataSource = null;
	private ArrayList<HashMap<String, String>> PrivateProfileDataSource = null;
	private UserInfo userInfo;

	/**
	 * @param context
	 * @param baseView
	 * @param userInfo
	 */
	public UserInfoView(Context context, View baseView, UserInfo userInfo) {
		super();
		this.context = context;
		this.baseView = baseView;
		this.userInfo = userInfo;
	}

	public void applyView() {

		mPublicProfileView = (CornerListView) baseView
				.findViewById(R.id.profilePublic);

		getPublicProfileDataSource();

		SimpleAdapter publicProfileAdapter = new SimpleAdapter(context,
				PublicProfileDataSource, R.layout.simple_list_item_arrow,
				new String[] { "publicItem" }, new int[] { R.id.item_title });

		mPublicProfileView.setAdapter(publicProfileAdapter);
		mPublicProfileView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
			}

		});

		if (!userInfo.isPrivacy()) {
			mPrivateProfileView = (CornerListView) baseView
					.findViewById(R.id.profilePrivate);
			getPrivateProfileDataSource();
			SimpleAdapter privateProfileAdapter = new SimpleAdapter(context,
					PublicProfileDataSource, R.layout.simple_list_item_arrow,
					new String[] { "privateItem" },
					new int[] { R.id.item_title });

			mPrivateProfileView.setAdapter(privateProfileAdapter);
			mPrivateProfileView
					.setOnItemClickListener(new OnItemClickListener() {

						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
						}

					});
		}
	}

	private ArrayList<HashMap<String, String>> getPublicProfileDataSource() {

		PublicProfileDataSource = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		HashMap<String, String> map3 = new HashMap<String, String>();
		HashMap<String, String> map4 = new HashMap<String, String>();

		map1.put("publicItem", userInfo.getName());// 在通讯录中寻找朋友
		map2.put("publicItem", userInfo.getName());// 在通讯录中邀请朋友
		map3.put("publicItem", userInfo.getWebsite());// 在本社交平台查找用户
		map4.put("publicItem", userInfo.getBio());

		PublicProfileDataSource.add(map1);
		PublicProfileDataSource.add(map2);
		PublicProfileDataSource.add(map3);
		PublicProfileDataSource.add(map4);

		return PublicProfileDataSource;
	}

	private ArrayList<HashMap<String, String>> getPrivateProfileDataSource() {

		PrivateProfileDataSource = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		HashMap<String, String> map3 = new HashMap<String, String>();

		map1.put("privateItem", userInfo.getMail());// 在通讯录中寻找朋友
		map2.put("privateItem", userInfo.getPhoneNumber());// 在通讯录中邀请朋友
		map3.put("privateItem", userInfo.getBirthday());// 在本社交平台查找用户

		PrivateProfileDataSource.add(map1);
		PrivateProfileDataSource.add(map2);
		PrivateProfileDataSource.add(map3);

		return PrivateProfileDataSource;
	}
}
