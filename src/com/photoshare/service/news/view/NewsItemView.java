/**
 * 
 */
package com.photoshare.service.news.view;

import java.util.List;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.photoshare.service.news.EventBean;
import com.photoshare.service.news.EventType;
import com.photoshare.service.news.NewsViewBean;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.PhotoType;
import com.photoshare.service.photos.factory.BitmapDisplayConfig;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncImageLoader.ImageCallback;
import com.photoshare.utils.async.AsyncUtils;
import com.photoshare.view.UserTextView;

/**
 * @author czj_yy
 * 
 */
public class NewsItemView {

	private NewsViewBean news;
	private TextView mNewsOwner;
	private TextView mNewsUserNames;
	private ImageView mNewsHead;
	private TextView mNewsPrefix;
	private TextView mNewsSuffix;
	private View baseView;
	private AsyncUtils asyncUtils;
	private int displayNameSize = 5;

	/**
	 * @param news
	 * @param baseView
	 * @param asyncUtils
	 */
	public NewsItemView(NewsViewBean news, View baseView, AsyncUtils asyncUtils) {
		super();
		this.news = news;
		this.baseView = baseView;
		this.asyncUtils = asyncUtils;
	}

	public void apply() {
		// mNewsOwner = new UserTextView(
		// (TextView) baseView.findViewById(R.id.newsOwnerId),
		// news.getUserInfo(), news.getUserInfo().getName());
		mNewsOwner = (TextView) baseView.findViewById(R.id.newsOwnerId);
		mNewsOwner.setText(news.getUserInfo().getName());
		mNewsHead = (ImageView) baseView.findViewById(R.id.newsHead);
		mNewsUserNames = (TextView) baseView.findViewById(R.id.newsUserNames);
		mNewsPrefix = (TextView) baseView.findViewById(R.id.newsPrefix);
		mNewsSuffix = (TextView) baseView.findViewById(R.id.newsSuffix);
		// mNewsOwner.disableListener();
		List<EventBean> eventBeans = news.getEventBean();
		EventType eventType = news.getEventType();

		StringBuilder builder = new StringBuilder();
		StringBuilder prefix = new StringBuilder();
		StringBuilder suffix = new StringBuilder();
		int currentSize = 0;
		String eventDescription = "";
		switch (eventType) {
		case COMMENT:
			if (news.isMerge()) {
				for (EventBean eventBean : eventBeans) {
					if (currentSize < displayNameSize) {
						builder.append(eventBean.getEventUserName());
						builder.append(",");
						currentSize++;
						eventDescription = eventBean.getEventDescription();
					} else
						break;
				}
				builder.deleteCharAt(builder.length() - 1);
				if (eventBeans.size() > displayNameSize) {
					builder.append("...");
					prefix.append("等");
					prefix.append(eventBeans.size() - displayNameSize);
					prefix.append("人");
				}
				mNewsUserNames.setText(builder.toString());
				prefix.append(eventType.getCnTag());
				prefix.append("了");
				mNewsPrefix.setText(prefix.toString());
				suffix.append("的照片 ");
				suffix.append(eventDescription);
				mNewsSuffix.setText(suffix.toString());
//				mNewsOwner.apply();
			}
			break;
		case FOLLOW:
			if (news.isMerge()) {
				for (EventBean eventBean : eventBeans) {
					if (currentSize < displayNameSize) {
						builder.append(eventBean.getEventUserName());
						builder.append(",");
						currentSize++;
					} else
						break;
				}
				builder.deleteCharAt(builder.length() - 1);
				if (eventBeans.size() > displayNameSize) {
					builder.append("...");
					prefix.append("等");
					prefix.append(eventBeans.size() - displayNameSize);
					prefix.append("人");
				}
				mNewsUserNames.setText(builder.toString());
				prefix.append(eventType.getCnTag());
				prefix.append("了");
				mNewsPrefix.setText(prefix.toString());
				mNewsSuffix.setText(suffix.toString());
//				mNewsOwner.apply();
			}
			break;
		case LIKE:
			if (news.isMerge()) {
				for (EventBean eventBean : eventBeans) {
					if (currentSize < displayNameSize) {
						builder.append(eventBean.getEventUserName());
						builder.append(",");
						currentSize++;
						eventDescription = eventBean.getEventDescription();
					} else
						break;
				}
				builder.deleteCharAt(builder.length() - 1);
				if (eventBeans.size() > displayNameSize) {
					builder.append("...");
					prefix.append("等");
					prefix.append(eventBeans.size() - displayNameSize);
					prefix.append("人");
				}
				mNewsUserNames.setText(builder.toString());
				prefix.append(eventType.getCnTag());
				prefix.append("了");
				mNewsPrefix.setText(prefix.toString());
				suffix.append("的照片 ");
				suffix.append(eventDescription);
				mNewsSuffix.setText(suffix.toString());
//				mNewsOwner.apply();
			}
			break;
		case NULL:
			break;
		case PHOTO:
			// mNewsUserNames.setText(news.getUserInfo().getName());
			// prefix.append("发布了");
			// prefix.append(eventBeans.size());
			// prefix.append("张照片");
			// mNewsOwner.invisible();
			break;
		default:
			break;
		}

		asyncUtils.loadDrawableFromWeb(news.getUserInfo().getTinyurl(),
				new ImageCallback() {

					public void imageLoaded(Drawable imageDrawable,
							String imageUrl) {
						if (mCallback != null) {
							mCallback.OnUserHeadLoaded(mNewsHead,
									imageDrawable, imageUrl);
						}
					}

					public void imageDefault() {
						if (mCallback != null) {
							mCallback.OnImageDefaule(mNewsHead);
						}
					}
				}, new BitmapDisplayConfig(PhotoType.SMALL));

	}

	private ICallback mCallback;

	public void registerCallback(ICallback mCallback) {
		this.mCallback = mCallback;
	}

	public interface ICallback {
		public void OnNewsUserNameClick(UserInfo info);

		public void OnNewsImageClick(PhotoBean photo);

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url);

		public void OnImageDefaule(ImageView image);
	}
}
