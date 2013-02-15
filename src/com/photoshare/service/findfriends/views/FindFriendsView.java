/**
 * 
 */
package com.photoshare.service.findfriends.views;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncUtils;

/**
 * @author czj_yy
 * 
 */
public class FindFriendsView {

	private Context context;
	private View baseView;
	private EditText mSearchNameView;
	private Button mSearchBtn;
	private ListView mFriendsView;
	private FindFriendsAdapter mAdapter;
	private List<UserInfo> mFriendsList;
	private AsyncUtils async;

	/**
	 * @param context
	 * @param baseView
	 * @param mFriendsList
	 * @param async
	 */
	public FindFriendsView(Context context, View baseView,
			List<UserInfo> mFriendsList, AsyncUtils async) {
		super();
		this.context = context;
		this.baseView = baseView;
		this.mFriendsList = mFriendsList;
		this.async = async;
	}

	public void applyView() {
		mSearchBtn = (Button) baseView.findViewById(R.id.findFriendsBtn);
		mSearchNameView = (EditText) baseView
				.findViewById(R.id.findFriendsEditText);
		mSearchBtn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (onSearchClickListener != null) {
					onSearchClickListener.OnSearch(mSearchBtn, getSearchStr());
				}
			}
		});
		mFriendsView = (ListView) baseView
				.findViewById(R.id.findFriendsListView);
		mAdapter = new FindFriendsAdapter();
		mFriendsView.setAdapter(mAdapter);
		mFriendsView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> arg0, View arg1,
					int position, long arg3) {
				UserInfo info = mFriendsList.get(position);
				if (info != null) {
					if (onSearchClickListener != null) {
						onSearchClickListener.OnItemClicked(info);
					}
				}
			}
		});
	}
	
	public void setmFriendsList(List<UserInfo> mFriendsList) {
		this.mFriendsList = mFriendsList;
		if (mAdapter != null) {
			mAdapter.notifyDataSetChanged();
		}
	}
	
	private String getSearchStr() {
		return mSearchNameView.getText().toString();
	}
	
	private class FindFriendsAdapter extends ArrayAdapter<UserInfo> {

		/**
		 * @param context
		 * @param textViewResourceId
		 * @param objects
		 */
		public FindFriendsAdapter() {
			super(context, 0, mFriendsList);
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			FindFriendsItemView ffView;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (rowView == null) {
				rowView = inflater.inflate(R.layout.find_friends_layout, null);
				UserInfo info = getItem(position);
				ffView = new FindFriendsItemView(rowView, async, info);
				rowView.setTag(ffView);
			} else {
				ffView = (FindFriendsItemView) rowView.getTag();
			}
			ffView.registerListener(viewDisplay);
			ffView.applyView();
			return rowView;
		}

	}

	private FindFriendsItemView.OnFriendsViewDisplay viewDisplay = new FindFriendsItemView.OnFriendsViewDisplay() {

		public void OnDisplay(ImageView view, Drawable drawable, String url) {
			// TODO Auto-generated method stub
			if (onSearchClickListener != null) {
				onSearchClickListener.OnDisplay(view, drawable, url);
			}
		}

		public void OnDefault(ImageView view) {
			// TODO Auto-generated method stub
			if (onSearchClickListener != null) {
				onSearchClickListener.OnDefault(view);
			}
		}
	};

	public void clear() {
		mFriendsList.clear();
		if (mAdapter != null) {
			mAdapter.notifyDataSetChanged();
		}
	}

	private OnSearchClick onSearchClickListener;

	public void registerListener(OnSearchClick onSearchClick) {
		this.onSearchClickListener = onSearchClick;
	}

	public interface OnSearchClick {
		public void OnSearch(View view, String str);

		public void OnItemClicked(UserInfo info);

		public void OnDisplay(ImageView view, Drawable drawable, String url);

		public void OnDefault(ImageView view);
	}

}
