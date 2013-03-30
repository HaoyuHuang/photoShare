package com.photoshare.tabHost;

import android.app.Activity;
import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TabHost;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.common.TabActivityResultListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.fragments.stacktrace.TracePhase;
import com.photoshare.fragments.stacktrace.TraceStack;
import com.photoshare.service.users.UserInfo;
import com.photoshare.service.users.UserInfoReader;
import com.photoshare.tabHost.tab.TabCameraActivity;
import com.photoshare.tabHost.tab.TabHomeActivity;
import com.photoshare.tabHost.tab.TabHomePageActivity;
import com.photoshare.tabHost.tab.TabNewsActivity;
import com.photoshare.tabHost.tab.TabPopularActivity;
import com.photoshare.utils.User;
import com.photoshare.utils.UserReader;
import com.photoshare.utils.async.AsyncUtils;
import com.renren.api.connect.android.Util;

public class TabHostActivity extends TabActivity {

	private RadioGroup ratioGroup;
	private static TabHost tabHost;
	public static final String TAB_HOME = "tabHome";
	public static final String TAB_POPULAR = "tabPopular";
	public static final String TAB_CAMERA = "tabCamera";
	public static final String TAB_NEWS = "tabFollower";
	public static final String TAB_HOME_PAGE = "tabProfile";
	public static final int tabHome = R.id.tabHome;
	public static final int tabPopular = R.id.tabPopular;
	public static final int tabCamera = R.id.tabCamera;
	public static final int tabFollower = R.id.tabFollower;
	public static final int tabProfile = R.id.tabProfile;
	private TraceStack stack = TraceStack.getInstance();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 设置不显示标题栏
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		// 设置布局
		this.setContentView(R.layout.tabwidgets);

		ratioGroup = (RadioGroup) this.findViewById(R.id.main_radio);
		tabHost = getTabHost();
		init();
	}

	public static void setCurrentTab(String tag) {
		tabHost.setCurrentTabByTag(tag);
	}

	@Override
	protected void onDestroy() {
		AsyncUtils async = AsyncUtils.getInstance();
		async.writeUserInfo(new UserReader(), new UserInfoReader(),
				new AbstractRequestListener<UserInfo>() {

					@Override
					public void onNetworkError(NetworkError networkError) {

					}

					@Override
					public void onFault(Throwable fault) {

					}

					@Override
					public void onComplete(UserInfo bean) {

					}
				});
		super.onDestroy();
	}

	private void init() {
//		User.Instance();
//		AsyncUtils.getInstance();
		tabHost.addTab(tabHost.newTabSpec(TAB_HOME).setIndicator(TAB_HOME)
				.setContent(new Intent(this, TabHomeActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(TAB_POPULAR)
				.setIndicator(TAB_POPULAR)
				.setContent(new Intent(this, TabPopularActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(TAB_CAMERA).setIndicator(TAB_CAMERA)
				.setContent(new Intent(this, TabCameraActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(TAB_NEWS).setIndicator(TAB_NEWS)
				.setContent(new Intent(this, TabNewsActivity.class)));
		tabHost.addTab(tabHost.newTabSpec(TAB_HOME_PAGE)
				.setIndicator(TAB_HOME_PAGE)
				.setContent(new Intent(this, TabHomePageActivity.class)));
		ratioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(RadioGroup group, int checkedId) {

				switch (checkedId) {
				case tabHome:
					tabHost.setCurrentTabByTag(TAB_HOME);
					stack.setCurrentPhase(TracePhase.HOME);
					break;
				case tabPopular:
					tabHost.setCurrentTabByTag(TAB_POPULAR);
					stack.setCurrentPhase(TracePhase.POPULAR);
					break;
				case tabCamera:
					tabHost.setCurrentTabByTag(TAB_CAMERA);
					stack.setCurrentPhase(TracePhase.CAMERA);
					break;
				case tabFollower:
					tabHost.setCurrentTabByTag(TAB_NEWS);
					stack.setCurrentPhase(TracePhase.NEWS);
					break;
				case tabProfile:
					System.out.println("TabChangedToHomePage");
					tabHost.setCurrentTabByTag(TAB_HOME_PAGE);
					stack.setCurrentPhase(TracePhase.HOME_PAGE);
					break;
				default:
					break;
				}
			}

		});
	}

	@Override
	protected void onResume() {
		stack.setCurrentPhase(TracePhase.HOME);
		super.onResume();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Util.logger("TabHost Activity Result");
		Activity activity = getLocalActivityManager().getCurrentActivity();
		if (activity instanceof TabActivityResultListener) {
			TabActivityResultListener listener = (TabActivityResultListener) activity;
			listener.onTabActivityResult(requestCode, resultCode, data);
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}
