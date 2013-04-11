/**
 * 
 */
package com.photoshare.command;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.photoshare.camera.CameraFragment;
import com.photoshare.camera.DecoratedPhotoFragment;
import com.photoshare.camera.DecoratedPhotoShareFragment;
import com.photoshare.camera.DecoratedPhotoUploadFragment;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.fragments.MainFragment;
import com.photoshare.fragments.PhotoBarFragment;
import com.photoshare.fragments.stacktrace.TraceConfig;
import com.photoshare.fragments.stacktrace.TraceElement;
import com.photoshare.fragments.stacktrace.TraceStack;
import com.photoshare.service.comments.views.CommentsFragment;
import com.photoshare.service.findfriends.views.FindFriendsFragment;
import com.photoshare.service.follow.views.FollowsInfoFragment;
import com.photoshare.service.likes.view.LikesFragment;
import com.photoshare.service.news.view.NewsFragment;
import com.photoshare.service.photos.views.FeedsFragment;
import com.photoshare.service.photos.views.FeedsItemFragment;
import com.photoshare.service.photos.views.PopularPhotosFragment;
import com.photoshare.service.share.views.DecoratedSharingPreferencesFragment;
import com.photoshare.service.share.views.PreferenceSettingsFragment;
import com.photoshare.service.share.views.SharePreferencesFragment;
import com.photoshare.service.share.views.ShareSettingsFragment;
import com.photoshare.service.signin.view.SignInFragment;
import com.photoshare.service.signup.view.SignUpFragment;
import com.photoshare.service.users.views.OtherHomeTitleBarFragment;
import com.photoshare.service.users.views.PersonalProfileFragment;
import com.photoshare.service.users.views.UserHomeFragment;
import com.photoshare.service.users.views.UserHomeTitleBarFragment;
import com.photoshare.service.users.views.UserProfileFragment;
import com.photoshare.tabHost.MainActivity;
import com.photoshare.tabHost.R;
import com.photoshare.tabHost.TabHostActivity;
import com.photoshare.tabHost.UserHomeActivity;
import com.photoshare.utils.Utils;

/**
 * @author Aron
 * 
 *         The Command Class used to pass the activity and fragment from the
 *         origin to the destination, respectively.
 * 
 */
public final class Command {

	private Command() {

	}

	public static void UserHome(Context orig, Bundle args) {
		Intent intent = new Intent(orig, UserHomeActivity.class);
		intent.putExtra(UserHomeActivity.KEY_USER_HOME_BUNDLES, args);
		orig.startActivity(intent);
	}

	public static void MsgList(Context orig) {

	}

	public static void TabHost(Context orig) {
		orig.startActivity(new Intent(orig, TabHostActivity.class));
	}

	public static void Main(Context orig) {
		orig.startActivity(new Intent(orig, MainActivity.class));
	}

	public static void invoke(Activity activity, String invokeName) {

	}

	/**
	 * This method used in the tab host
	 * 
	 * @param base
	 * @param invokeName
	 * @param args
	 */
	public static void forwardTab(BaseFragment base, String invokeName,
			Bundle args) {
		int fragmentViewId = base.getFragmentViewId();
		BaseFragment target = invoke(base, invokeName, args);
		TraceStack stack = TraceStack.getInstance();
		if (target != null) {
			target.setCanonicalTag(invokeName);
			FragmentTransaction ft = base.getFragmentManager()
					.beginTransaction();
			if (args == null) {
				args = new Bundle();
			}
			TraceElement element = new TraceElement(base.getCanonicalTag(),
					args, invokeName);
			stack.forward(element);
			args.putBoolean(TraceConfig.getTrackBackward(), true);
			target.setArguments(args);
			ft.replace(fragmentViewId, target);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}
		Utils.logger(invokeName);
	}

	/**
	 * 
	 * This method used in the fragments
	 * 
	 * @param base
	 * @param invokeName
	 * @param args
	 */
	public static void forward(BaseFragment base, String invokeName, Bundle args) {
		int fragmentViewId = base.getFragmentViewId();
		BaseFragment target = invoke(base, invokeName, args);
		TraceStack stack = TraceStack.getInstance();
		if (target != null) {
			target.setCanonicalTag(invokeName);
			FragmentTransaction ft = base.getFragmentManager()
					.beginTransaction();
			if (args == null) {
				args = new Bundle();
			}
			TraceElement element = new TraceElement(base.getCanonicalTag(),
					args, invokeName);
			stack.forward(element);
			target.setArguments(args);
			ft.replace(fragmentViewId, target);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}
		Utils.logger(invokeName);
	}

	public static void backward(BaseFragment base, Bundle args) {
		int fragmentViewId = base.getFragmentViewId();
		TraceStack stack = TraceStack.getInstance();
		TraceElement element = stack.backward();
		String originFragment = element.getOriginFragment();
		System.out.println(originFragment);
		BaseFragment target = invoke(base, originFragment, args);
		if (target != null) {
			FragmentTransaction ft = base.getFragmentManager()
					.beginTransaction();
			target.setCanonicalTag(element.getOriginFragment());
			target.setArguments(element.getParams());
			ft.replace(fragmentViewId, target);
			ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
			ft.commit();
		}
		Utils.logger(element.getOriginFragment());
	}

	private static BaseFragment invoke(BaseFragment base, String invokeName,
			Bundle args) {
		int fragmentViewId = base.getFragmentViewId();

		BaseFragment target = null;

		if (invokeName.equals(base.getString(R.string.fcameraPhotoFragment))) {
			target = CameraFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fcommentsFragment)
				.equals(invokeName)) {
			target = CommentsFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fdecoratedPhotoFragment).equals(
				invokeName)) {
			target = DecoratedPhotoFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fdecoratedPhotoUploadFragment)
				.equals(invokeName)) {
			target = DecoratedPhotoUploadFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fdecoratedPhotoShareFragment)
				.equals(invokeName)) {
			target = DecoratedPhotoShareFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fdecoratedSharingPreferenceFragment)
				.equals(invokeName)) {
			target = DecoratedSharingPreferencesFragment
					.newInstance(fragmentViewId);
		} else if (base.getString(R.string.ffeedsFragment).equals(invokeName)) {
			target = FeedsFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.ffeedsItemFragment).equals(
				invokeName)) {
			target = FeedsItemFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.ffindFriendsFragment).equals(
				invokeName)) {
			target = FindFriendsFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.ffollowInfoFragment).equals(
				invokeName)) {
			target = FollowsInfoFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.flikeFragment).equals(invokeName)) {
			target = LikesFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fmainFragment).equals(invokeName)) {
			target = MainFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fnewsFragment).equals(invokeName)) {
			target = NewsFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fotherHomeTitleBarFragment).equals(
				invokeName)) {
			target = OtherHomeTitleBarFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fpersonalProfileFragment).equals(
				invokeName)) {
			target = PersonalProfileFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fphotoBarFragment)
				.equals(invokeName)) {
			target = PhotoBarFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fpopularPhotosFragment).equals(
				invokeName)) {
			target = PopularPhotosFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fpreferenceSettingsFragment).equals(
				invokeName)) {
			target = PreferenceSettingsFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fsharePreferenceFragment).equals(
				invokeName)) {
			target = SharePreferencesFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fshareSettingsFragment).equals(
				invokeName)) {
			target = ShareSettingsFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fsignInFragment).equals(invokeName)) {
			target = SignInFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fsignUpFragment).equals(invokeName)) {
			target = SignUpFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fuserHomeFragment)
				.equals(invokeName)) {
			target = UserHomeFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fuserHomeTitleBarFragment).equals(
				invokeName)) {
			target = UserHomeTitleBarFragment.newInstance(fragmentViewId);
		} else if (base.getString(R.string.fuserProfileFragment).equals(
				invokeName)) {
			target = UserProfileFragment.newInstance(fragmentViewId);
		}
		return target;
	}

}
