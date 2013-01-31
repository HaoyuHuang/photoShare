package com.photoshare.tabHost.tab;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.photoshare.camera.CameraFragment;
import com.photoshare.command.Command;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.fragments.stacktrace.TraceElement;
import com.photoshare.fragments.stacktrace.TracePhase;
import com.photoshare.tabHost.BaseActivity;
import com.photoshare.tabHost.R;

public class TabCameraActivity extends BaseActivity {
	private CameraFragment uhf;

	private boolean onStop;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tab_camera_layout_holder);
		initFragments();
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
			Command.forwardTab((BaseFragment) getFragmentManager()
					.findFragmentById(R.id.TabCameraLayoutHolderId),
					getCameraFragment(), null);
		}
	}

	private String getCameraFragment() {
		return getString(R.string.fcameraPhotoFragment);
	}

}
