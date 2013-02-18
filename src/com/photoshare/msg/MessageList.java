/**
 * 
 */
package com.photoshare.msg;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.common.RequestParam;
import com.photoshare.exception.NetworkError;
import com.photoshare.service.comments.PutCommentRequestParam;
import com.photoshare.service.follow.UserFollowRequestParam;
import com.photoshare.service.likes.PhotoLikeRequestParam;
import com.photoshare.service.photos.PhotoUploadRequestParam;
import com.photoshare.utils.Utils;
import com.photoshare.utils.async.AsyncUtils;

/**
 * @author Aron
 * 
 *         The Message List is a singleton class. It saves all failed requests.
 * 
 */
public class MessageList {

	private static final MessageList msgList = new MessageList();

	private AsyncUtils async = AsyncUtils.getInstance();

	public static MessageList getInstance() {
		return msgList;
	}

	private MessageList() {

	}

	private String path = Utils.SDCARD_ABSOLUTE_PATH + File.separator
			+ Utils.APP_NAME + File.separator + Utils.DIR_HOME + File.separator
			+ Utils.DIR_MSG;
	private String fileName = "message.xml";

	public void initList() throws Exception {
		tryConfigure();
		reader = new MessageReader();
		AbstractRequestListener<List<MessageItem>> listener = new AbstractRequestListener<List<MessageItem>>() {

			@Override
			public void onNetworkError(NetworkError networkError) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFault(Throwable fault) {
				
			}

			@Override
			public void onComplete(List<MessageItem> bean) {
				if (bean != null) {
					list = bean;
				}
			}
		};
		async.readXMLList(reader, path, fileName, listener);
		freeze();
	}

	private List<MessageItem> list = new ArrayList<MessageItem>();
	private MessageReader reader;
	private boolean configured;

	private void freeze() {
		this.configured = true;
	}

	private void tryConfigure() {
		if (configured) {
			return;
		}
	}

	/**
	 * Add a Request Message to the Message List. (User could implement the
	 * interface {@link OnModifyListener} to do the subsequent operations after
	 * adding.)
	 * 
	 * @param request
	 *            which has been failed to send to the server in 3 times and
	 *            will be automatically add to the message list.
	 */
	public void add(RequestMsg<? extends RequestParam> request) {

		MessageItem item = new MessageItem();
		RequestParam param = request.getAMsg();
		if (param == null) {
			return;
		}

		item.setMsgType(request.getType());
		switch (request.getType()) {
		case COMMENT:
			PutCommentRequestParam cparam = (PutCommentRequestParam) param;
			item.setEventId(cparam.getmPhotoId());
			item.setMsgDescription(cparam.getComment());
			item.setMsgPhotoUrl(cparam.getTinyUrl());
			break;
		case FOLLOW:
			UserFollowRequestParam fparam = (UserFollowRequestParam) param;
			item.setBtnStatus(fparam.isFollowing());
			item.setEventId(fparam.getFollowId());
			item.setMsgPhotoUrl(fparam.getTinyUrl());
			break;
		case LIKE:
			PhotoLikeRequestParam lparam = (PhotoLikeRequestParam) param;
			item.setBtnStatus(lparam.isLike());
			item.setEventId(lparam.getPhotoId());
			item.setMsgPhotoUrl(lparam.getTinyUrl());
			break;
		case NULL:
			break;
		case PHOTO:
			PhotoUploadRequestParam uparam = (PhotoUploadRequestParam) param;
			item.setEventId(uparam.getUid());
			item.setMsgDescription(uparam.getCaption());
			item.setMsgPhotoUrl(uparam.getFile().getAbsolutePath());
			break;
		default:
			break;
		}
		list.add(item);
		notifyOnAdd(item);
	}

	public void remove(MessageItem item) {
		list.remove(item);
		notifyOnRemove(item);
	}

	public void setList(List<MessageItem> list) {
		this.list = list;
	}

	/**
	 * The method may risk the "escape" problem, thus why it is used as a
	 * temporary method, in which will be deprecated in future release.
	 * 
	 * @return Message List
	 */
	public List<MessageItem> getList() {
		return list;
	}

	/**
	 * The method may risk the "escape" problem, thus why it is used as a
	 * temporary method, in which will be deprecated in future release.
	 * 
	 * @return Message List
	 */
	public ArrayList<MessageItem> getArrayList() {
		return new ArrayList<MessageItem>(list);
	}

	/**
	 * @author Aron
	 * 
	 *         The interface OnModifyListener designed to notify the user any
	 *         specific operations posed on the Message List
	 * 
	 */
	public interface OnModifyListener {
		public void OnAdd(MessageItem item);

		public void OnRemove(MessageItem item);
	}

	private ArrayList<OnModifyListener> listeners = new ArrayList<MessageList.OnModifyListener>();

	public void registerListener(OnModifyListener listener) {
		this.listeners.add(listener);
	}

	private void notifyOnAdd(MessageItem item) {
		for (OnModifyListener listener : listeners) {
			listener.OnAdd(item);
		}
	}

	private void notifyOnRemove(MessageItem item) {
		for (OnModifyListener listener : listeners) {
			listener.OnRemove(item);
		}
	}

}
