package com.photoshare.viewgroup;

import android.app.ActivityGroup;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import com.photoshare.ui.tab.ForumsActivity;
import com.photoshare.ui.tab.PopularActivity;

public class PopularViewGroup extends ActivityGroup
{
	public static ActivityGroup _PopularGroup;

	public static LocalActivityManager _manager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		_PopularGroup = this;
		_manager = this.getLocalActivityManager();
		Intent intent = new Intent(this, PopularActivity.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		Window w = PopularViewGroup._manager.startActivity(
				PopularActivity.class.getName(), intent);
		View view = w.getDecorView();
		PopularViewGroup._PopularGroup.setContentView(view);
		
	}

}
