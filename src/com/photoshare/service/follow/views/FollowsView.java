/**
 * 
 */
package com.photoshare.service.follow.views;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.photoshare.common.IObserver;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncUtils;
import com.photoshare.view.State;
import com.photoshare.view.listview.MyListView;

/**
 * @author czj_yy
 * 
 */
public class FollowsView {

	private MyListView followList;
	private View baseView;
	private ArrayList<UserInfo> followsInfo;
	private Context context;
	private AsyncUtils async;

	/**
	 * @param baseView
	 * @param followsInfo
	 * @param context
	 * @param async
	 */
	public FollowsView(View baseView, ArrayList<UserInfo> followsInfo,
			Context context, AsyncUtils async) {
		super();
		this.baseView = baseView;
		this.followsInfo = followsInfo;
		this.context = context;
		this.async = async;
	}

	public void applyView() {
		followList = (MyListView) baseView
				.findViewById(R.id.userFollowListView);
		FollowAdapter adapter = new FollowAdapter();
		followList.setAdapter(adapter);
	}

	private class FollowAdapter extends ArrayAdapter<UserInfo> {

		public FollowAdapter() {
			super(context, 0, followsInfo);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			FollowItemView follows;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (rowView == null) {
				rowView = inflater.inflate(R.layout.simple_list_item_follows, null);
				UserInfo userInfo = getItem(position);
				follows = new FollowItemView(userInfo, rowView, async);
				rowView.setTag(follows);
			} else {
				follows = (FollowItemView) rowView.getTag();
			}
			follows.registerCallback(mCallback);
			follows.applyView();
			return rowView;
		}

	}

	private FollowItemView.ICallback mCallback = new FollowItemView.ICallback() {

		public void OnNameClick(UserInfo info) {
			if (onFollowActionListener != null) {
				onFollowActionListener.OnUserNameClick(info);
			}
		}

		public void OnFollowClick(UserInfo info, IObserver<State> observer) {
			// TODO Auto-generated method stub
			if (onFollowActionListener != null) {
				onFollowActionListener.OnFollowClick(info, observer);
			}
		}

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url) {
			if (onFollowActionListener != null) {
				onFollowActionListener.OnUserHeadLoaded(image, drawable, url);
			}

		}

		public void OnImageDefaule(ImageView image) {
			if (onFollowActionListener != null) {
				onFollowActionListener.OnImageDefault(image);
			}
		}

	};

	public void registerCallback(OnFollowActionListener actionListener) {
		this.onFollowActionListener = actionListener;
	}

	public interface OnFollowActionListener {
		public void OnUserNameClick(UserInfo info);
		
		public void OnFollowClick(UserInfo info, IObserver<State> observer);

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url);

		public void OnImageDefault(ImageView image);
	}

	private OnFollowActionListener onFollowActionListener;
}
