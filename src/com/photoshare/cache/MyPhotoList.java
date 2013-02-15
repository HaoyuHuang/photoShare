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
 * @author Aron
 * 
 */
public class MyPhotoList {
	private ArrayList<PhotoBean> mPhotos = new ArrayList<PhotoBean>();

	private static MyPhotoList mPhotosList = new MyPhotoList();

	private AsyncUtils async = AsyncUtils.getInstance();

	private String path = Utils.SDCARD_ABSOLUTE_PATH + File.separator
			+ Utils.APP_NAME + File.separator + Utils.DIR_HOME + File.separator
			+ Utils.DIR_MY_PHOTOS;

	private String file = "photo.xml";

	public static MyPhotoList getInstance() {
		return mPhotosList;
	}

	public void addPhoto(PhotoBean photo) {
		mPhotos.add(photo);
		notifyDataChanged();
	}

	public ArrayList<PhotoBean> getPhotos() {
		return mPhotos;
	}

	public void setPhotos(ArrayList<PhotoBean> feeds) {
		this.mPhotos = feeds;
	}

	public void removePhoto(PhotoBean photo) {
		mPhotos.remove(photo);
		notifyDataChanged();
	}

	public void removeAll() {
		mPhotos.clear();
		notifyDataInvalid();
	}

	public void writeXML() {
		PhotoBeanReader reader = new PhotoBeanReader();
		AbstractRequestListener<List<PhotoBean>> listener = new AbstractRequestListener<List<PhotoBean>>() {

			@Override
			public void onNetworkError(NetworkError networkError) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onFault(Throwable fault) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onComplete(List<PhotoBean> bean) {
				// TODO Auto-generated method stub

			}
		};
		async.writeXMLList(reader, path, file, mPhotos, listener);
	}

	public interface DataSetChanged {
		public void DataChanged();

		public void DataInvalid();
	}

	private ArrayList<DataSetChanged> listeners = new ArrayList<DataSetChanged>();

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
