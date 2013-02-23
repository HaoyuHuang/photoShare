package com.photoshare.tabHost.tab;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.photoshare.camera.CameraFragment;
import com.photoshare.common.TabActivityResultListener;
import com.photoshare.fragments.stacktrace.TraceElement;
import com.photoshare.fragments.stacktrace.TracePhase;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.share.views.DecoratedSharingPreferencesFragment;
import com.photoshare.tabHost.BaseActivity;
import com.photoshare.tabHost.R;
import com.renren.api.connect.android.Util;

public class TabCameraActivity extends BaseActivity implements
		TabActivityResultListener {
	private CameraFragment uhf;

	private boolean onStop;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_camera_layout_holder);
		initFragmentss();
	}

	private void initFragments() {
		uhf = (CameraFragment) getFragmentManager().findFragmentById(
				R.id.TabCameraLayoutHolderId);
		uhf = CameraFragment.newInstance(R.id.TabCameraLayoutHolderId);
		uhf.setCanonicalTag(getCameraFragment());
		TraceElement element = new TraceElement(getCameraFragment(), null);
		stack.setCurrentPhase(TracePhase.CAMERA);
		stack.forward(element);
		// Execute a transaction, replacing any existing fragment
		// with this one inside the frame.
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.TabCameraLayoutHolderId, uhf);
		// ft.show(details);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
	}

	private void initFragmentss() {
		DecoratedSharingPreferencesFragment uhf = (DecoratedSharingPreferencesFragment) getFragmentManager()
				.findFragmentById(R.id.TabCameraLayoutHolderId);
		uhf = DecoratedSharingPreferencesFragment
				.newInstance(R.id.TabCameraLayoutHolderId);
		uhf.setCanonicalTag(getCameraFragment());
		TraceElement element = new TraceElement(getCameraFragment(), null);
		Drawable d = getResources()
				.getDrawable(R.drawable.titlebar_left_button);
		Bitmap b = ((BitmapDrawable) d).getBitmap();
		Bundle args = new Bundle();
		args.putParcelable(PhotoBean.KEY_PHOTO, b);
		args.putString(PhotoBean.KEY_CAPTION, "test");
		stack.setCurrentPhase(TracePhase.CAMERA);
		stack.forward(element);
		// Execute a transaction, replacing any existing fragment
		// with this one inside the frame.
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.TabCameraLayoutHolderId, uhf);
		// ft.show(details);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
		ft.commit();
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
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if (onStop) {
			stack.setCurrentPhase(TracePhase.CAMERA);
			// Command.forwardTab((BaseFragment) getFragmentManager()
			// .findFragmentById(R.id.TabCameraLayoutHolderId),
			// getCameraFragment(), null);
		}
	}

	private String getCameraFragment() {
		return getString(R.string.fcameraPhotoFragment);
	}

	public void onTabActivityResult(int requestCode, int resultCode, Intent data) {
		Util.logger("onTabActivityResult");
		if (uhf.isVisible()) {
			uhf.onTakePhotoResult(requestCode, resultCode, data);
		} else {
			Fragment f = getFragmentManager().findFragmentById(
					R.id.TabCameraLayoutHolderId);
			if (f instanceof DecoratedSharingPreferencesFragment) {
				Util.logger("decorated");
				DecoratedSharingPreferencesFragment dspf = (DecoratedSharingPreferencesFragment) f;
				dspf.onAuthorizeCallback(requestCode, resultCode, data);
			}
		}
	}

}
