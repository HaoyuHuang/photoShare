/**
 * 
 */
package com.photoshare.view;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;

import com.photoshare.common.Builder;

/**
 * @author Aron
 * 
 *         The NotificationDisplayer aims at displaying notification at the
 *         notification title bar while the method {@Method
 *         displayNotification} has been called
 * 
 */
public class NotificationDisplayer {

	private Context context;
	private String mContentTitle;
	private String mContentText;
	private int mSmallIcon;
	private String mContentInfo;
	private String ticker;
	private String tag;
	private int id;

	public static class NotificationBuilder implements
			Builder<NotificationDisplayer> {
		private Context context;
		private String mContentTitle;
		private String mContentText;
		private int mSmallIcon;
		private String mContentInfo;
		private String ticker;
		private String tag;
		private int id;

		public NotificationBuilder Context(Context context) {
			this.context = context;
			return this;
		}

		public NotificationBuilder ContentTitle(String contentTitle) {
			this.mContentTitle = contentTitle;
			return this;
		}

		public NotificationBuilder ContentText(String contentText) {
			this.mContentText = contentText;
			return this;
		}

		public NotificationBuilder SmallIcon(int smallIcon) {
			this.mSmallIcon = smallIcon;
			return this;
		}

		public NotificationBuilder ContentInfo(String contentInfo) {
			this.mContentInfo = contentInfo;
			return this;
		}

		public NotificationBuilder Ticker(String ticker) {
			this.ticker = ticker;
			return this;
		}

		public NotificationBuilder Tag(String tag) {
			this.tag = tag;
			return this;
		}

		public NotificationBuilder Id(int id) {
			this.id = id;
			return this;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see com.photoshare.common.Builder#build()
		 */
		public NotificationDisplayer build() {
			// TODO Auto-generated method stub
			return new NotificationDisplayer(this);
		}

	}

	private NotificationManager mNotifyManager;
	private final Notification.Builder mBuilder = new Notification.Builder(
			context);

	/**
	 * @param notificationBuilder
	 */
	public NotificationDisplayer(NotificationBuilder notificationBuilder) {
		this.context = notificationBuilder.context;
		this.mContentInfo = notificationBuilder.mContentInfo;
		this.mContentText = notificationBuilder.mContentText;
		this.mContentTitle = notificationBuilder.mContentTitle;
		this.mSmallIcon = notificationBuilder.mSmallIcon;
		this.ticker = notificationBuilder.ticker;
		this.id = notificationBuilder.id;
		this.tag = notificationBuilder.tag;
		NotifyAsyncMsgProcess();
	}

	private void NotifyAsyncMsgProcess() {
		mNotifyManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);

		// Intent resultIntent = new Intent(context, MessageFragment.class);
		// TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
		// // stackBuilder.addParentStack(MessageFragment.class);
		// stackBuilder.addNextIntent(resultIntent);
		// PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(0,
		// PendingIntent.FLAG_UPDATE_CURRENT);

		mBuilder.setContentTitle(mContentTitle).setContentText(mContentText)
				.setSmallIcon(mSmallIcon).setContentInfo(mContentInfo)
				.setTicker(ticker);
	}

	public void displayNotification() {
		// mNotifyManager.notify(tag, id, mBuilder.build());
	}

	public void cancleNotification() {
		mNotifyManager.cancel(tag, id);
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

}
