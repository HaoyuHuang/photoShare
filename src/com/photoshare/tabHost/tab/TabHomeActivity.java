package com.photoshare.tabHost.tab;

import java.io.File;
import java.util.ArrayList;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.Window;

import com.photoshare.cache.FeedsList;
import com.photoshare.command.Command;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.fragments.stacktrace.TraceElement;
import com.photoshare.fragments.stacktrace.TracePhase;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.PhotoBeanReader;
import com.photoshare.service.photos.RequestPhotoType;
import com.photoshare.service.photos.views.FeedsFragment;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.BaseActivity;
import com.photoshare.tabHost.R;
import com.photoshare.utils.Utils;

public class TabHomeActivity extends BaseActivity {

	private PhotoBeanReader reader;

	private FeedsList feeds = FeedsList.getInstance();

	private final String KEY_FEEDS = "FEEDS";

	private String path = Utils.APP_NAME + File.separator + Utils.DIR_HOME
			+ File.separator + Utils.DIR_FEED;

	private String file = "feeds.xml";

	private FeedsFragment ff;

	private boolean onStop = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_home_layout_holder);
		initFeeds();
		initFragments();

	}

	@Override
	protected void onDestroy() {
		feeds.writeXML();
		super.onDestroy();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		onStop = true;
	}

	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onRestoreInstanceState(savedInstanceState);
		if (savedInstanceState != null) {
			if (savedInstanceState.containsKey(KEY_FEEDS)) {
				ArrayList<PhotoBean> photos = savedInstanceState
						.getParcelableArrayList(KEY_FEEDS);
				feeds.setFeeds(photos);
			}
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (onStop) {
			Utils.logger("onTabHomeActivityResumed");
			stack.setCurrentPhase(TracePhase.HOME);
			// Bundle args = new Bundle();
			// args.putParcelableArrayList(PhotoBean.KEY_PHOTOS,
			// feeds.getFeeds());
			// for (PhotoBean bean : feeds.getFeeds()) {
			// System.out.println(bean);
			// }
			// args.putParcelable(UserInfo.KEY_USER_INFO, user.getUserInfo());
			// args.putBoolean(BaseFragment.KEY_IGNORE_TITLE_VIEW, true);
			// Command.forwardTab((BaseFragment) getFragmentManager()
			// .findFragmentById(R.id.TabHomeLayoutHolderId),
			// getFeedsFragment(), args);
		}

	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		outState.putParcelableArrayList(KEY_FEEDS, feeds.getFeeds());
	}

	private String getFeedsFragment() {
		return getString(R.string.ffeedsFragment);
	}

	private void initFragments() {
		ff = (FeedsFragment) getFragmentManager().findFragmentById(
				R.id.TabHomeLayoutHolderId);
		ff = FeedsFragment.newInstance(R.id.TabHomeLayoutHolderId);
		ff.setCanonicalTag(getFeedsFragment());
		ff.setPhotos(feeds.getFeeds());
		ff.setUserInfo(user.getUserInfo());
		ff.setType(RequestPhotoType.MyFeeds);

		TraceElement element = new TraceElement(getFeedsFragment(), null);
		Bundle args = new Bundle();
		args.putBoolean(BaseFragment.KEY_IGNORE_TITLE_BAR_ELEMENT, true);
		element.setParams(args);
		stack.setCurrentPhase(TracePhase.HOME);
		stack.forward(element);

		ff.setArguments(args);
		// Execute a transaction, replacing any existing fragment
		// with this one inside the frame.
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.TabHomeLayoutHolderId, ff);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}

	private void initFeeds() {
		try {
			reader = new PhotoBeanReader();
			reader.loadListFromXML(feeds.getFeeds(), path, file);
		} catch (Exception e) {

		}
	}

}