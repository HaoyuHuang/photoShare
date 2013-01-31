/**
 * 
 */
package com.photoshare.camera;

import java.io.ByteArrayOutputStream;
import java.io.File;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.photoshare.cache.FeedsList;
import com.photoshare.common.AbstractRequestListener;
import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.fragments.BaseFragment;
import com.photoshare.msg.MessageList;
import com.photoshare.msg.MsgType;
import com.photoshare.msg.RequestMsg;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.PhotoUploadRequestParam;
import com.photoshare.service.photos.PhotoUploadResponseBean;
import com.photoshare.tabHost.R;
import com.photoshare.tabHost.TabHostActivity;
import com.photoshare.utils.Utils;
import com.photoshare.view.NotificationDisplayer;

/**
 * @author Aron
 * 
 *         DecoratedPhotoShareFragment displays views for uploading photos.
 * 
 */
public class DecoratedPhotoShareFragment extends BaseFragment {

	private MessageList mMsgList = MessageList.getInstance();
	private DecoratedPhotoShareView shareView;

	private String leftBtnText = "";
	private String rightBtnText = "";
	private String titlebarText = "";

	public static DecoratedPhotoShareFragment newInstance(int fragmentViewId) {
		DecoratedPhotoShareFragment dpsf = new DecoratedPhotoShareFragment();
		dpsf.setFragmentViewId(fragmentViewId);
		return dpsf;
	}

	private String getDecoratedPhotoShareFragment() {
		return getString(R.string.fdecoratedPhotoShareFragment);
	}

	private NotificationDisplayer displayer;

	private Bitmap photo;

	public Bitmap getPhoto() {
		return photo;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.decorating_share_photo_layout,
				container, false);
	}

	@Override
	protected void OnRightBtnClicked() {
		try {
			upload();
		} catch (NetworkException e) {
			mExceptionHandler.obtainMessage(NetworkError.ERROR_SIGN_IN_NULL)
					.sendToTarget();
		}
	}

	@Override
	protected void OnLeftBtnClicked() {
		backward(null);
	}

	@Override
	public void onSaveInstanceState(Bundle outState) {
		if (outState != null) {
			outState.putParcelable(PhotoBean.KEY_PHOTO, photo);
		}
		super.onSaveInstanceState(outState);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		if (savedInstanceState != null) {
			photo = savedInstanceState.getParcelable(PhotoBean.KEY_PHOTO);
		}
		super.onActivityCreated(savedInstanceState);
		Bundle args = getArguments();
		if (args != null) {
			if (args.containsKey(PhotoBean.KEY_PHOTO)) {
				photo = args.getParcelable(PhotoBean.KEY_PHOTO);
			}
		}
		initViews();
	}

	private void initViews() {
		leftBtnText = getCameraText();
		titlebarText = getPhotoText();
		rightBtnText = getSubmitText();
		initTitleBar(leftBtnText, rightBtnText, titlebarText);
		shareView = new DecoratedPhotoShareView(getActivity().findViewById(
				R.id.decoratingSharePhotoLayoutId));
		shareView.applyView();

		displayer = new NotificationDisplayer.NotificationBuilder()
				.Context(getActivity()).Tag(MsgType.PHOTO.toString())
				.ContentTitle(getContentTitle()).ContentText(getContent())
				.Ticker(getContentTitle()).build();
	}

	private String getContentTitle() {
		return getString(R.string.uploadPhotoCaption);
	}

	private String getContent() {
		return getString(R.string.uploadPhotoContent);
	}

	private String getCameraText() {
		return getString(R.string.camera);
	}

	private String getSubmitText() {
		return getString(R.string.submit);
	}

	private String getPhotoText() {
		return getString(R.string.photos);
	}

	private String getDecoratedPhotoFragment() {
		return getString(R.string.fdecoratedPhotoFragment);
	}

	private void upload() throws NetworkException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		photo.compress(CompressFormat.PNG, 100, baos);
		File file = Utils.getFileFromBytes(baos.toByteArray(), "bitmap");

		final PhotoUploadRequestParam photoParam = new PhotoUploadRequestParam();

		photoParam.setFile(file);
		photoParam.setCaption(shareView.getCaption());
		photoParam.setUid(user.getUserInfo().getUid());

		displayer.displayNotification();

		async.publishPhoto(photoParam,
				new AbstractRequestListener<PhotoUploadResponseBean>() {
					@Override
					public void onNetworkError(NetworkError networkError) {
						if (networkError != null) {
							RequestMsg<? extends RequestParam> msg = new RequestMsg<PhotoUploadRequestParam>(
									photoParam, MsgType.PHOTO);
							mMsgList.add(msg);
							getActivity().runOnUiThread(new Runnable() {

								public void run() {
									mExceptionHandler.obtainMessage(
											NetworkError.ERROR_PHOTO)
											.sendToTarget();
								}

							});
						}
					}

					@Override
					public void onFault(Throwable fault) {
						if (fault != null) {
							RequestMsg<? extends RequestParam> msg = new RequestMsg<PhotoUploadRequestParam>(
									photoParam, MsgType.PHOTO);
							mMsgList.add(msg);
							getActivity().runOnUiThread(new Runnable() {

								public void run() {
									mExceptionHandler.obtainMessage(
											NetworkError.ERROR_NETWORK)
											.sendToTarget();
								}

							});
						}
					}

					@Override
					public void onComplete(final PhotoUploadResponseBean bean) {
						if (bean != null) {
							getActivity().runOnUiThread(new Runnable() {

								public void run() {
									FeedsList feeds = FeedsList.getInstance();
									PhotoBean photo = bean.get();
									feeds.addFeed(photo);
									TabHostActivity
											.setCurrentTab(TabHostActivity.TAB_HOME);

								}

							});
						}
					}
				});
		displayer.cancleNotification();
	}
}
