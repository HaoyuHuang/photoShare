package com.photoshare.viewgroup;

import com.photoshare.ui.tab.ForumsActivity;
import com.photoshare.ui.tab.FriendsActivity;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

public class ForumsViewGroup extends ActivityGroup
{

	public static ActivityGroup _FroumsGroup;

	public static LocalActivityManager _manager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		_FroumsGroup = this;
		_manager = this.getLocalActivityManager();

		Intent intent = new Intent(this, ForumsActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Window w = ForumsViewGroup._manager.startActivity(
				ForumsActivity.class.getName(), intent);
		View view = w.getDecorView();
		ForumsViewGroup._FroumsGroup.setContentView(view);
		Log.d("WCHH", "----------->finash2");
	}

}
