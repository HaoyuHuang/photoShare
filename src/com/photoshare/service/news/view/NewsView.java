/**
 * 
 */
package com.photoshare.service.news.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.photoshare.service.news.NewsBean;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncUtils;

/**
 * @author czj_yy
 * 
 */
public class NewsView {

	private ArrayList<NewsBean> newsList;
	private ListView mNewsListView;
	private View baseView;
	private Context context;
	private AsyncUtils async;

	/**
	 * @param newsList
	 * @param baseView
	 * @param context
	 * @param async
	 */
	public NewsView(ArrayList<NewsBean> newsList, View baseView,
			Context context, AsyncUtils async) {
		super();
		this.newsList = newsList;
		this.baseView = baseView;
		this.context = context;
		this.async = async;
	}

	public void applyView() {
		mNewsListView = (ListView) baseView.findViewById(R.id.newsListView);
		NewsAdapter adapter = new NewsAdapter();
		mNewsListView.setAdapter(adapter);
	}

	private class NewsAdapter extends ArrayAdapter<NewsBean> {

		/**
		 * @param context
		 * @param textViewResourceId
		 */
		public NewsAdapter() {
			super(context, 0, newsList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View rowView = convertView;

			if (rowView == null) {
				LayoutInflater inflater = (LayoutInflater) context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				rowView = inflater.inflate(R.layout.simple_grid_item, null);
			} else {

			}

			NewsBean news = getItem(position);

			NewsItemView item = new NewsItemView(news, rowView, async, context);
			item.registerCallback(mCallback);
			item.applyView();

			return rowView;
		}
	}

	private NewsItemView.ICallback mCallback = new NewsItemView.ICallback() {

		public void OnNewsImageClick(PhotoBean photo) {
			// TODO Auto-generated method stub
			if (onNewsClickListener != null) {
				onNewsClickListener.OnNewsImageClick(photo);
			}
		}

		public void OnNameClick(UserInfo info) {
			// TODO Auto-generated method stub
			if (onNewsClickListener != null) {
				onNewsClickListener.OnNameClick(info);
			}
		}

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url) {
			if (onNewsClickListener != null) {
				onNewsClickListener.OnUserHeadLoaded(image, drawable, url);
			}
		}

		public void OnImageDefaule(ImageView image) {
			if (onNewsClickListener != null) {
				onNewsClickListener.OnImageDefault(image);
			}
		}
	};

	private OnNewsClickListener onNewsClickListener;

	public void registerNewsClickListener(OnNewsClickListener clickListener) {
		this.onNewsClickListener = clickListener;
	}

	public interface OnNewsClickListener {
		public void OnNewsImageClick(PhotoBean photo);

		public void OnNameClick(UserInfo info);

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url);

		public void OnImageDefault(ImageView image);

	}
}
