package com.photoshare.tabHost;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

import com.photoshare.command.Command;
import com.photoshare.fragments.stacktrace.TraceElement;
import com.photoshare.fragments.stacktrace.TracePhase;
import com.photoshare.service.users.UserInfo;
import com.photoshare.service.users.views.UserHomeFragment;

public class UserHomeActivity extends BaseActivity {

	public static final String KEY_USER_HOME_BUNDLES = "userHomeBundles";

	private UserHomeFragment mf;

	private void initFragments() {
		mf = (UserHomeFragment) getFragmentManager().findFragmentById(
				R.id.userHomeHolderId);
		mf = UserHomeFragment.newInstance(R.id.userHomeHolderId);
		mf.setCanonicalTag(getUserHomeFragment());

		Bundle extra = getIntent().getExtras().getBundle(KEY_USER_HOME_BUNDLES);

		UserInfo info = extra.getParcelable(UserInfo.KEY_USER_INFO);
		Bundle args = new Bundle();

		args.putParcelable(UserInfo.KEY_USER_INFO, info);
		mf.setArguments(args);

		TraceElement element = new TraceElement(getUserHomeFragment(), args);
		stack.setCurrentPhase(TracePhase.USER_HOME);
		stack.forward(element);

		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.userHomeHolderId, mf);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.user_home_layout_holder);
		initFragments();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		Command.TabHost(getBaseContext());
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		stack.setCurrentPhase(TracePhase.USER_HOME);
		super.onResume();
	}

	private String getUserHomeFragment() {
		return getString(R.string.fuserHomeFragment);
	}
}
