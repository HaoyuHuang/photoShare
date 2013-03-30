package com.photoshare.tabHost.tab;

import java.util.ArrayList;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.photoshare.command.Command;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.fragments.stacktrace.TraceElement;
import com.photoshare.fragments.stacktrace.TracePhase;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.RequestPhotoType;
import com.photoshare.service.photos.views.PopularPhotosFragment;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.BaseActivity;
import com.photoshare.tabHost.R;

public class TabPopularActivity extends BaseActivity {

	private ArrayList<PhotoBean> mPopularPhotos;
	private PopularPhotosFragment ppf;

	private boolean onStop = false;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_popular_layout_holder);
		initFragments();
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
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
			mPopularPhotos = savedInstanceState
					.getParcelableArrayList(PhotoBean.KEY_PHOTOS);
		}
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(outState);
		if (outState != null) {
			outState.putParcelableArrayList(PhotoBean.KEY_PHOTOS,
					mPopularPhotos);
		}
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (onStop) {
			stack.setCurrentPhase(TracePhase.POPULAR);
			Bundle args = new Bundle();
			args.putParcelableArrayList(PhotoBean.KEY_PHOTOS, mPopularPhotos);
			args.putParcelable(UserInfo.KEY_USER_INFO, user.getUserInfo());
			args.putString(PhotoBean.KEY_PHOTO_TYPE,
					RequestPhotoType.PopularPhotos.toString());
			Command.forwardTab((BaseFragment) getFragmentManager()
					.findFragmentById(R.id.TabPopularLayoutHolderId),
					getPopularFragment(), args);
		}
	}

	private String getPopularFragment() {
		return getString(R.string.fpopularPhotosFragment);
	}

	private void initFragments() {
		ppf = (PopularPhotosFragment) getFragmentManager().findFragmentById(
				R.id.TabPopularLayoutHolderId);

		ppf = PopularPhotosFragment.newInstance(R.id.TabPopularLayoutHolderId);
		ppf.setUserInfo(user.getUserInfo());
		ppf.setType(RequestPhotoType.PopularPhotos);
		ppf.setPhotos(mPopularPhotos);
		TraceElement element = new TraceElement(getPopularFragment(), null);
		stack.setCurrentPhase(TracePhase.POPULAR);
		stack.forward(element);
		// Execute a transaction, replacing any existing fragment
		// with this one inside the frame.
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.TabPopularLayoutHolderId, ppf);

		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}

}
