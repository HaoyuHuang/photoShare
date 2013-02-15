/**
 * 
 */
package com.photoshare.cache;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.photos.PhotoBeanReader;
import com.photoshare.utils.Utils;
import com.photoshare.utils.async.AsyncUtils;

/**
 * @author czj_yy
 * 
 */
public class FeedsList {
	private ArrayList<PhotoBean> feeds = new ArrayList<PhotoBean>();

	private static FeedsList mFeedsList = new FeedsList();

	private AsyncUtils async = AsyncUtils.getInstance();

	private String path = Utils.SDCARD_ABSOLUTE_PATH + File.separator
			+ Utils.APP_NAME + File.separator + Utils.DIR_HOME + File.separator
			+ Utils.DIR_FEED;

	private String file = "feeds.xml";

	public static FeedsList getInstance() {
		return mFeedsList;
	}

	public void addFeed(PhotoBean photo) {
		feeds.add(photo);
		notifyDataChanged();
	}

	public ArrayList<PhotoBean> getFeeds() {
		return feeds;
	}

	public void setFeeds(ArrayList<PhotoBean> feeds) {
		this.feeds = feeds;
	}

	public void removeFeed(PhotoBean photo) {
		feeds.remove(photo);
		notifyDataChanged();
	}

	public void removeAll() {
		feeds.clear();
		notifyDataInvalid();
	}

	public interface DataSetChanged {
		public void DataChanged();

		public void DataInvalid();
	}

	public void writeXML() {
		PhotoBeanReader reader = new PhotoBeanReader();
		AbstractRequestListener<List<PhotoBean>> listener = new AbstractRequestListener<List<PhotoBean>>() {

			@Override
			public void onComplete(List<PhotoBean> bean) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onNetworkError(NetworkError networkError) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFault(Throwable fault) {
				// TODO Auto-generated method stub
				
			}

		};
		async.writeXMLList(reader, path, file, feeds, listener);
	}

	private ArrayList<DataSetChanged> listeners = new ArrayList<FeedsList.DataSetChanged>();

	private void notifyDataChanged() {
		for (DataSetChanged listener : listeners) {
			listener.DataChanged();
		}
	}

	private void notifyDataInvalid() {
		for (DataSetChanged listener : listeners) {
			listener.DataInvalid();
		}
	}

	public void registerListener(DataSetChanged listener) {
		listeners.add(listener);
	}
}
