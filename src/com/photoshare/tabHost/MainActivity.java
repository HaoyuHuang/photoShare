/**
 * 
 */
package com.photoshare.tabHost;

import android.app.FragmentTransaction;
import android.os.Bundle;

import com.photoshare.fragments.MainFragment;
import com.photoshare.fragments.stacktrace.TraceElement;
import com.photoshare.fragments.stacktrace.TracePhase;

/**
 * @author Aron
 * 
 */
public class MainActivity extends BaseActivity {

	private MainFragment mf;

	private void initFragments() {
		mf = (MainFragment) getFragmentManager().findFragmentById(
				R.id.MainLayoutHolderId);
		mf = MainFragment.newInstance(R.id.MainLayoutHolderId);
		// Execute a transaction, replacing any existing fragment
		// with this one inside the frame.
		mf.setCanonicalTag(getMainFragment());
		stack.setCurrentPhase(TracePhase.MAIN);
		TraceElement element = new TraceElement(getMainFragment(), null);
		stack.forward(element);
		FragmentTransaction ft = getFragmentManager().beginTransaction();
		ft.replace(R.id.MainLayoutHolderId, mf);
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
		setContentView(R.layout.main_layout_holder);
		initFragments();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onBackPressed() {
		// do nothing
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		stack.setCurrentPhase(TracePhase.MAIN);
		super.onResume();
	}

	private String getMainFragment() {
		return getString(R.string.fmainFragment);
	}

}
