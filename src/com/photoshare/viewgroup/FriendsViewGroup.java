package com.photoshare.viewgroup;

import com.photoshare.ui.tab.FriendsActivity;
import com.photoshare.ui.tab.ProfileActivity;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class FriendsViewGroup extends ActivityGroup
{

	public static ActivityGroup _frindsGroup;

	public static LocalActivityManager _manager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		_frindsGroup = this;
		_manager = this.getLocalActivityManager();
		Log.d("WCHH", "----------->finash1");
		Intent intent = new Intent(this, FriendsActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Window w = FriendsViewGroup._manager.startActivity(FriendsActivity.class.getName(),
				intent);
		View view = w.getDecorView();
		FriendsViewGroup._frindsGroup.setContentView(view);
		Log.d("WCHH", "----------->finash2");
	}

}
