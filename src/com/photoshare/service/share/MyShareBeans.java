/**
 * 
 */
package com.photoshare.service.share;

import java.util.ArrayList;
import java.util.List;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.exception.NetworkError;
import com.photoshare.utils.async.AsyncUtils;

/**
 * @author Aron
 * 
 */
public class MyShareBeans {

	private MyShareBeans() {

	}

	private static MyShareBeans beans = new MyShareBeans();

	public static MyShareBeans getInstance() {
		return beans;
	}

	public void initShareBeans() {
		AsyncUtils async = AsyncUtils.getInstance();
		async.readXMLList(new ShareBeanReader(), ShareBeanReader.PATH,
				ShareBeanReader.FILE,
				new AbstractRequestListener<List<ShareBean>>() {

					@Override
					public void onComplete(List<ShareBean> bean) {
						if (bean != null) {
							bean = mShareBeans;
						}
					}

					@Override
					public void onNetworkError(NetworkError networkError) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onFault(Throwable fault) {
						// TODO Auto-generated method stub

					}

				});
	}

	public void writeShareBeans() {
		AsyncUtils async = AsyncUtils.getInstance();
		async.writeXMLList(new ShareBeanReader(), ShareBeanReader.PATH,
				ShareBeanReader.FILE, mShareBeans, null);
	}

	private List<ShareBean> mShareBeans = new ArrayList<ShareBean>();

	public void putShareBean(ShareBean bean) {
		boolean exist = false;
		for (int i = 0; i < mShareBeans.size(); i++) {
			ShareBean share = mShareBeans.get(i);
			if (bean.getmShareType().equals(share.getmShareType())) {
				share.setmShareAccount(bean.getmShareAccount());
				share.setmSharePwd(bean.getmSharePwd());
				exist = true;
			}
		}
		if (!exist) {
			mShareBeans.add(bean);
		}
	}

	public boolean containsBean(ShareBean bean) {
		for (int i = 0; i < mShareBeans.size(); i++) {
			if (bean.getmShareType().equals(mShareBeans.get(i).getmShareType())) {
				return true;
			}
		}
		return false;
	}

}
