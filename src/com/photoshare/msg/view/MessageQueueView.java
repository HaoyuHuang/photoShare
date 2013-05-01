/**
 * 
 */
package com.photoshare.msg.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.photoshare.common.IObserver;
import com.photoshare.msg.MessageItem;
import com.photoshare.msg.MessageList;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncUtils;
import com.photoshare.view.State;

/**
 * @author Aron
 * 
 */
public class MessageQueueView {

	private Context context;
	private View baseView;
	private AsyncUtils async;
	private ListView msgQueue;
	private MessageList messages;
	private MsgQueueAdapter adapter;

	/**
	 * @param context
	 * @param baseView
	 * @param async
	 * @param messages
	 */
	public MessageQueueView(Context context, View baseView, AsyncUtils async,
			MessageList messages) {
		super();
		this.context = context;
		this.baseView = baseView;
		this.async = async;
		this.messages = messages;
	}

	public void applyView() {
		messages.registerListener(onModifyListener);
		msgQueue = (ListView) baseView.findViewById(R.id.messageQueue);
		adapter = new MsgQueueAdapter();
		msgQueue.setAdapter(adapter);

	}

	private class MsgQueueAdapter extends ArrayAdapter<MessageItem> {

		/**
		 * @param context
		 * @param textViewResourceId
		 * @param objects
		 */
		public MsgQueueAdapter() {
			super(context, 0, messages.getList());
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View rowView = convertView;
			MessageItem item;
			MessageQueueItemView itemView;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (rowView == null) {
				rowView = inflater.inflate(R.layout.simple_list_item_message,
						null);
				item = getItem(position);
				itemView = new MessageQueueItemView(rowView, item, async);
				itemView.registerListener(messageListener);
				rowView.setTag(itemView);
			} else {
				itemView = (MessageQueueItemView) rowView.getTag();
			}

			itemView.applyView();

			return rowView;
		}

		public void notifyDataChanged() {
			notifyDataSetChanged();
		}

	}

	private MessageQueueItemView.MessageListener messageListener = new MessageQueueItemView.MessageListener() {

		public void OnResend(MessageItem message, IObserver<State> observer) {
			// TODO Auto-generated method stub
			if (listener != null) {
				listener.OnMsgClicked(message, observer);
			}
		}

		public void OnImageLoaded(ImageView imageView, Drawable drawable,
				String url) {
			if (listener != null) {
				listener.OnImageLoaded(imageView, drawable, url);
			}
		}

		public void OnImageDefault(ImageView imageView) {
			if (listener != null) {
				listener.OnImageDefault(imageView);
			}
		}
	};

	private MessageList.OnModifyListener onModifyListener = new MessageList.OnModifyListener() {

		public void OnRemove(MessageItem item) {
			adapter.notifyDataChanged();
		}

		public void OnAdd(MessageItem item) {
			adapter.notifyDataChanged();
		}
	};

	private OnMsgListener listener;

	public interface OnMsgListener {
		public void OnMsgClicked(MessageItem message, IObserver<State> observer);

		public void OnImageLoaded(ImageView image, Drawable drawable, String url);

		public void OnImageDefault(ImageView image);
	}

	public void registerListener(OnMsgListener listener) {
		this.listener = listener;
	}

}
