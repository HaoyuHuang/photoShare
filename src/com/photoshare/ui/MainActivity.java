package com.photoshare.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

import com.photoshare.R;
import com.photoshare.ui.tab.CameraActivity;
import com.photoshare.ui.tab.ProfileActivity;
import com.photoshare.viewgroup.ForumsViewGroup;
import com.photoshare.viewgroup.FriendsViewGroup;
import com.photoshare.viewgroup.PopularViewGroup;

public class MainActivity extends TabActivity
{

	public static final String HOME = "home";
	public static final String FORUMS = "forums";
	public static final String CAMERA = "camera";
	public static final String FRIENDS = "friends";
	public static final String PROFILE = "profile";

	private TabHost mTabHost;

	private RadioGroup mRadioGroup;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.main);

		this.mTabHost = this.getTabHost();

		TabSpec homeSpec = mTabHost.newTabSpec(HOME).setIndicator(HOME)
				.setContent(new Intent(this, PopularViewGroup.class));
		TabSpec forumsSpec = mTabHost.newTabSpec(FORUMS).setIndicator(FORUMS)
				.setContent(new Intent(this, ForumsViewGroup.class));
		TabSpec cameraSpec = mTabHost.newTabSpec(CAMERA).setIndicator(CAMERA)
				.setContent(new Intent(this, CameraActivity.class));
		TabSpec friendsSpec = mTabHost.newTabSpec(FRIENDS)
				.setIndicator(FRIENDS)
				.setContent(new Intent(this, FriendsViewGroup.class));
		TabSpec profileSpec = mTabHost.newTabSpec(PROFILE)
				.setIndicator(PROFILE)
				.setContent(new Intent(this, ProfileActivity.class));
		mTabHost.addTab(forumsSpec);
		mTabHost.addTab(homeSpec);
		mTabHost.addTab(cameraSpec);
		mTabHost.addTab(friendsSpec);
		mTabHost.addTab(profileSpec);

		
		
		this.mRadioGroup = (RadioGroup) this.findViewById(R.id.main_radio);

		this.mRadioGroup
				.setOnCheckedChangeListener(new OnCheckedChangeListener()
				{

					public void onCheckedChanged(RadioGroup group, int checkedId)
					{

						switch (checkedId)
						{
						case R.id.tabhome:
							mTabHost.setCurrentTabByTag(MainActivity.HOME);
							break;
						case R.id.tabcamera:
							mTabHost.setCurrentTabByTag(MainActivity.CAMERA);
							break;
						case R.id.tabforums:
							mTabHost.setCurrentTabByTag(MainActivity.FORUMS);
							break;
						case R.id.tabfriends:
							mTabHost.setCurrentTabByTag(MainActivity.FRIENDS);
							break;
						case R.id.tabprofile:
							mTabHost.setCurrentTabByTag(MainActivity.PROFILE);
							Log.d("WCH", "-------------->");
							break;
						}
					}
				});
	}

}
