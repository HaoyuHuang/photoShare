/**
 * 
 */
package com.photoshare.service.photos.views;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncUtils;
import com.photoshare.view.listview.MyListView;
import com.photoshare.view.listview.MyListView.OnRefreshListener;

/**
 * @author Aron
 * 
 */
public class FeedsView {

	private Context context;
	private View baseView;
	private MyListView feedsView;
	private FeedsAdapter mFeedsAdapter;
	private List<PhotoBean> feeds;
	private AsyncUtils async;

	/**
	 * @param context
	 * @param baseView
	 * @param feeds
	 * @param async
	 */
	public FeedsView(Context context, View baseView, List<PhotoBean> feeds,
			AsyncUtils async) {
		super();
		this.context = context;
		this.baseView = baseView;
		this.feeds = feeds;
		this.async = async;
	}

	public void applyView() {
		feedsView = (MyListView) baseView.findViewById(R.id.feedsList);
		feedsView.setonRefreshListener(new OnRefreshListener() {

			public void onRefresh() {
				if (mFeedsActionListener != null) {
					mFeedsActionListener.OnRefresh();
				}
			}

		});
		mFeedsAdapter = new FeedsAdapter();
		feedsView.setAdapter(mFeedsAdapter);
	}

	private class FeedsAdapter extends ArrayAdapter<PhotoBean> {

		/*
		 * @param context
		 * 
		 * @param textViewResourceId
		 */
		public FeedsAdapter() {
			super(context, 0, feeds);
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see android.widget.Adapter#getView(int, android.view.View,
		 * android.view.ViewGroup)
		 */
		public View getView(int position, View convertView, ViewGroup parent) {

			View rowView = convertView;
			FeedItemView feeds;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (rowView == null) {
				rowView = inflater.inflate(R.layout.feeds_layout, null);
				PhotoBean feed = getItem(position);
				feeds = new FeedItemView(rowView, async, feed);
				rowView.setTag(feeds);
			} else {
				feeds = (FeedItemView) rowView.getTag();
			}
			feeds.registerCallback(mCallback);

			feeds.applyView();

			return rowView;
		}
	}

	public void addPhotoBean(PhotoBean photo) {
		if (photo != null) {
			feeds.add(photo);
			mFeedsAdapter.notifyDataSetChanged();
		}
	}

	private FeedItemView.ICallback mCallback = new FeedItemView.ICallback() {

		public void OnNameClick(UserInfo info) {
			if (mFeedsActionListener != null) {
				mFeedsActionListener.OnNameClick(info);
			}
		}

		public void OnCommentListClick(PhotoBean photo) {
			if (mFeedsActionListener != null) {
				mFeedsActionListener.OnCommentListClick(photo);
			}
		}

		public void OnLikeListClick(PhotoBean like) {
			if (mFeedsActionListener != null) {
				mFeedsActionListener.OnLikeListClick(like);
			}
		}

		public void OnLikeClick(PhotoBean photo) {
			if (mFeedsActionListener != null) {
				mFeedsActionListener.OnLikeClick(photo);
			}
		}

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url) {
			if (mFeedsActionListener != null) {
				mFeedsActionListener.OnUserHeadLoaded(image, drawable, url);
			}
		}

		public void OnFeedPhotoLoaded(ImageView image, Drawable drawable,
				String url) {
			if (mFeedsActionListener != null) {
				mFeedsActionListener.OnFeedPhotoLoaded(image, drawable, url);
			}
		}

		public void OnUserHeadDefault(ImageView image) {
			if (mFeedsActionListener != null) {
				mFeedsActionListener.OnUserHeadDefault(image);
			}
		}

		public void OnFeedPhotoDefault(ImageView image) {
			if (mFeedsActionListener != null) {
				mFeedsActionListener.OnFeedPhotoDefault(image);
			}
		}
	};

	public void registerCallback(OnFeedsActionListener actionListener) {
		this.mFeedsActionListener = actionListener;
	}

	private OnFeedsActionListener mFeedsActionListener;

	public interface OnFeedsActionListener {
		public void OnNameClick(UserInfo info);

		public void OnCommentListClick(PhotoBean photo);

		public void OnLikeListClick(PhotoBean photo);

		public void OnLikeClick(PhotoBean photo);

		public void OnRefresh();

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url);

		public void OnFeedPhotoLoaded(ImageView image, Drawable drawable,
				String url);

		public void OnUserHeadDefault(ImageView image);

		public void OnFeedPhotoDefault(ImageView image);
	}
}
