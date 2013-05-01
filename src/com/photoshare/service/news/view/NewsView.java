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
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.photoshare.service.news.NewsBean;
import com.photoshare.service.news.NewsViewBean;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncUtils;

/**
 * @author czj_yy
 * 
 */
public class NewsView {

	private ArrayList<NewsViewBean> newsList;
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
	public NewsView(ArrayList<NewsViewBean> newsList, View baseView,
			Context context, AsyncUtils async) {
		super();
		this.newsList = newsList;
		this.baseView = baseView;
		this.context = context;
		this.async = async;
	}

	public void applyView() {
		mNewsListView = (ListView) baseView.findViewById(R.id.newsListView);
		if (mNewsListView == null) {
			System.out.println("nulll List View -----------------------------");
			return;
		}
		NewsAdapter adapter = new NewsAdapter();
		if (newsList == null) {
			System.out.println("List null ------------------------");
			return;
		}
		mNewsListView.setAdapter(adapter);
		mNewsListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				System.out.println("onNewsClickListener");
				if (onNewsClickListener != null) {
					System.out.println("onNewsClickListener");
					onNewsClickListener.onNewsItemClick(newsList.get(arg2));
				}
			}

		});
	}

	private class NewsAdapter extends ArrayAdapter<NewsViewBean> {

		/**
		 * @param context
		 * @param textViewResourceId
		 */
		public NewsAdapter() {
			super(context, R.layout.simple_list_item_news, newsList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View rowView = convertView;
			NewsItemView item = null;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (rowView == null) {

				rowView = inflater
						.inflate(R.layout.simple_list_item_news, null);
				NewsViewBean news = getItem(position);

				item = new NewsItemView(news, rowView, async);
				rowView.setTag(item);
			} else {
				item = (NewsItemView) rowView.getTag();
			}
			item.registerCallback(mCallback);
			item.apply();
			return rowView;
		}
	}

	public ArrayList<NewsViewBean> getNewsList() {
		return newsList;
	}

	public void setNewsList(ArrayList<NewsViewBean> newsList) {
		this.newsList = newsList;
	}

	private NewsItemView.ICallback mCallback = new NewsItemView.ICallback() {

		public void OnNewsImageClick(PhotoBean photo) {
			// TODO Auto-generated method stub
			if (onNewsClickListener != null) {
				onNewsClickListener.OnNewsImageClick(photo);
			}
		}

		public void OnNewsUserNameClick(UserInfo info) {
			// TODO Auto-generated method stub
			if (onNewsClickListener != null) {
				onNewsClickListener.OnNewsUserNameClick(info);
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

		public void OnNewsUserNameClick(UserInfo info);

		public void onNewsItemClick(NewsViewBean bean);

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url);

		public void OnImageDefault(ImageView image);

	}
}
