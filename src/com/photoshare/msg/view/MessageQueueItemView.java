/**
 * 
 */
package com.photoshare.msg.view;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.photoshare.common.IObserver;
import com.photoshare.msg.MessageItem;
import com.photoshare.service.photos.PhotoType;
import com.photoshare.service.photos.factory.BitmapDisplayConfig;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncImageLoader.ImageCallback;
import com.photoshare.utils.async.AsyncUtils;
import com.photoshare.view.State;
import com.photoshare.view.UserStateBtn;

/**
 * @author Aron
 * 
 */
public class MessageQueueItemView {
	private View baseView;
	private ImageView msgPhoto;
	private TextView msgName;
	private TextView msgDecription;
	private UserStateBtn msgBtn;
	private MessageItem item;
	private AsyncUtils async;

	/**
	 * @param baseView
	 * @param item
	 * @param async
	 */
	public MessageQueueItemView(View baseView, MessageItem item,
			AsyncUtils async) {
		super();
		this.baseView = baseView;
		this.item = item;
		this.async = async;
	}

	public void applyView() {
		msgBtn = new UserStateBtn(baseView, R.id.itemMessageBtn, true, item
				.getMsgType().getStartText(), item.getMsgType()
				.getPendingText(), item.getMsgType().getSuccessText(), item
				.getMsgType().getFailText());
		msgBtn.registerListener(listener);
		msgDecription = (TextView) baseView
				.findViewById(R.id.itemMessageDescription);
		msgName = (TextView) baseView.findViewById(R.id.itemMessageName);
		msgPhoto = (ImageView) baseView.findViewById(R.id.itemMessagePhoto);
		msgDecription.setText(item.getMsgDescription());
		msgName.setText(item.getMsgName());

		BitmapDisplayConfig config = new BitmapDisplayConfig();

		switch (item.getMsgType()) {
		case COMMENT:
		case FOLLOW:
			config.setPhotoType(PhotoType.SMALL);
			break;
		case LIKE:
		case PHOTO:
			config.setPhotoType(PhotoType.MIDDLE);
			break;
		case NULL:
			break;
		default:
			break;

		}

		async.loadDrawableFromFile(item.getMsgPhotoUrl(), new ImageCallback() {

			public void imageLoaded(Drawable imageDrawable, String imageUrl) {
				if (msgListener != null) {
					msgListener
							.OnImageLoaded(msgPhoto, imageDrawable, imageUrl);
				}
			}

			public void imageDefault() {
				if (msgListener != null) {
					msgListener.OnImageDefault(msgPhoto);
				}
			}
		}, config);

	}

	private UserStateBtn.OnObserverClickListener listener = new UserStateBtn.OnObserverClickListener() {

		public void OnClick(IObserver<State> observer) {
			if (msgListener != null) {
				msgListener.OnResend(item, msgBtn);
			}
		}
	};

	private MessageListener msgListener;

	public interface MessageListener {
		public void OnResend(MessageItem message, IObserver<State> observer);

		public void OnImageLoaded(ImageView imageView, Drawable drawable,
				String url);

		public void OnImageDefault(ImageView imageView);
	}

	public void registerListener(MessageListener listener) {
		this.msgListener = listener;
	}
}
