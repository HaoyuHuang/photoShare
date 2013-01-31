/**
 * 
 */
package com.photoshare.service.share.views;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SimpleAdapter;

import com.photoshare.service.share.ShareBean;
import com.photoshare.service.share.ShareType;
import com.photoshare.tabHost.R;
import com.photoshare.view.listview.CornerListView;

/**
 * @author czj_yy
 * 
 */
public class SharePreferencesView {
	private View baseView;
	private Context context;
	private CornerListView SharingListView = null;
	private ArrayList<HashMap<String, String>> SharingList = null;

	/**
	 * @param baseView
	 * @param context
	 */
	public SharePreferencesView(View baseView, Context context) {
		super();
		this.baseView = baseView;
		this.context = context;
	}

	public void applyView() {
		getAccountDataSource();
		SharingListView = (CornerListView) baseView
				.findViewById(R.id.sharingPreferencelist);
		SimpleAdapter FriendsAdapter = new SimpleAdapter(context, SharingList,
				R.layout.simple_list_item_arrow, new String[] { "friendItem" },
				new int[] { R.id.item_title });
		SharingListView.setAdapter(FriendsAdapter);
		SharingListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				ShareBean bean = new ShareBean();
				ShareType type;
				switch (position) {
				case 0:
					type = ShareType.RenRen;
					break;
				case 1:
					type = ShareType.SinaWeibo;
					break;
				case 2:
					type = ShareType.TxWeibo;
					break;
				default:
					type = ShareType.NULL;
				}
				Log.i("SharingType", type.toString());
				bean.setmShareType(type);
				if (listener != null) {
					listener.AsyncShareSettings(view, bean);
				}
			}

		});
	}

	private ArrayList<HashMap<String, String>> getAccountDataSource() {

		SharingList = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		HashMap<String, String> map3 = new HashMap<String, String>();
		map1.put("friendItem", "人人网");// 在通讯录中寻找朋友
		map2.put("friendItem", "新浪微博");// 在通讯录中邀请朋友
		map3.put("friendItem", "腾讯微博");// 在本社交平台查找用户
		SharingList.add(map1);
		SharingList.add(map2);
		SharingList.add(map3);

		return SharingList;
	}

	private OnAsyncItemClickListener listener;

	public void registerListener(OnAsyncItemClickListener listener) {
		this.listener = listener;
	}

	public interface OnAsyncItemClickListener {
		public void AsyncShareSettings(View view, ShareBean info);
	}

}
