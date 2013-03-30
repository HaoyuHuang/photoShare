/**
 * 
 */
package com.photoshare.tabHost.tab;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.photoshare.cache.MyPhotoList;
import com.photoshare.command.Command;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.fragments.stacktrace.TraceElement;
import com.photoshare.fragments.stacktrace.TracePhase;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.RequestPhotoType;
import com.photoshare.service.share.views.PreferenceSettingsFragment;
import com.photoshare.service.users.UserInfo;
import com.photoshare.service.users.views.UserHomeFragment;
import com.photoshare.tabHost.BaseActivity;
import com.photoshare.tabHost.R;

/**
 * @author czj_yy
 * 
 */
public class TabHomePageActivity extends BaseActivity {

	private PreferenceSettingsFragment uhf;
	private MyPhotoList photos = MyPhotoList.getInstance();
	private boolean onPause;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_home_page_layout_holder);
		initFragments();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		onPause = true;
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (onPause) {
			stack.setCurrentPhase(TracePhase.HOME_PAGE);
			Bundle args = new Bundle();
			args.putParcelableArrayList(PhotoBean.KEY_PHOTOS,
					photos.getPhotos());
			args.putString(PhotoBean.KEY_PHOTO_TYPE,
					RequestPhotoType.MyPhotos.toString());
			Command.forwardTab((BaseFragment) getFragmentManager()
					.findFragmentById(R.id.TabHomePageLayoutHolderId2),
					getPreferenceSettingsFragment(), args);
		}
	}

	@Override
	protected void onDestroy() {
		photos.writeXML();
		super.onDestroy();
	}

	private void initFragments() {

		uhf = (PreferenceSettingsFragment) getFragmentManager()
				.findFragmentById(R.id.TabHomePageLayoutHolderId2);
		uhf = PreferenceSettingsFragment
				.newInstance(R.id.TabHomePageLayoutHolderId2);
		uhf.setCanonicalTag(getPreferenceSettingsFragment());
		Bundle args = new Bundle();
		args.putParcelable(UserInfo.KEY_USER_INFO, user.getUserInfo());
		args.putParcelableArrayList(PhotoBean.KEY_PHOTOS, photos.getPhotos());
		args.putString(PhotoBean.KEY_PHOTO_TYPE, RequestPhotoType.MyPhotos.toString());
		uhf.setArguments(args);
		TraceElement element = new TraceElement(
				getPreferenceSettingsFragment(), args);
		stack.setCurrentPhase(TracePhase.HOME_PAGE);
		stack.forward(element);
		// Execute a transaction, replacing any existing fragment
		// with this one inside the frame.
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.TabHomePageLayoutHolderId2, uhf);
		// ft.show(details);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}

	private String getPreferenceSettingsFragment() {
		return getString(R.string.fpreferenceSettingsFragment);
	}
}
