/**
 * 
 */
package com.photoshare.service.likes.view;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.photoshare.service.likes.LikeBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncUtils;
import com.photoshare.view.listview.CornerListView;

/**
 * @author czj_yy
 * 
 */
public class LikesView {
	private CornerListView mLikesListView;
	private Context context;
	private View baseView;
	private ArrayList<LikeBean> beans;
	private AsyncUtils async;

	/**
	 * @param context
	 * @param baseView
	 * @param beans
	 * @param async
	 */
	public LikesView(Context context, View baseView, ArrayList<LikeBean> beans,
			AsyncUtils async) {
		super();
		this.context = context;
		this.baseView = baseView;
		this.beans = beans;
		this.async = async;
	}

	public void applyView() {
		mLikesListView = (CornerListView) baseView
				.findViewById(R.id.likesListView);
		LikesAdapter adapter = new LikesAdapter();
		mLikesListView.setAdapter(adapter);

	}

	private class LikesAdapter extends ArrayAdapter<LikeBean> {

		/**
		 * @param context
		 * @param textViewResourceId
		 * @param objects
		 */
		public LikesAdapter() {
			super(context, 0, beans);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View rowView = convertView;
			LikeItemView likes;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (rowView == null) {
				rowView = inflater.inflate(R.layout.feeds_layout, null);
				LikeBean feed = getItem(position);
				likes = new LikeItemView(rowView, async, feed);
				rowView.setTag(likes);
			} else {
				likes = (LikeItemView) rowView.getTag();
			}
			likes.registerListener(listener);

			likes.applyView();

			return rowView;
		}
	}

	private LikeItemView.OnLikeClickListener listener = new LikeItemView.OnLikeClickListener() {

		public void OnNameClick(UserInfo info) {
			if (mCallback != null) {
				mCallback.OnLikeNameClick(info);
			}
		}

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url) {
			if (mCallback != null) {
				mCallback.OnUserHeadLoaded(image, drawable, url);
			}
		}

		public void OnImageDefaule(ImageView image) {
			if (mCallback != null) {
				mCallback.OnImageDefault(image);
			}
		}
	};

	private ICallback mCallback;

	public void registerCallback(ICallback mCallback) {
		this.mCallback = mCallback;
	}

	public interface ICallback {
		public void OnLikeNameClick(UserInfo info);

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url);

		public void OnImageDefault(ImageView image);
	}
}
