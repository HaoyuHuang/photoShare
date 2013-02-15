/**
 * 
 */
package com.photoshare.service.news;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.photoshare.common.ResponseBean;
import com.photoshare.exception.NetworkException;

/**
 * @author czj_yy
 * 
 */
@Deprecated
public class FollowGetNewsResponseBean extends ResponseBean {

	/**
	 * 构造UsersGetInfoResponseBean对象
	 * 
	 * @param response
	 *            返回的数据 json格式
	 * @throws Exception
	 */
	public FollowGetNewsResponseBean(String response) {
		super(response);
		if (response == null) {
			return;
		}

		try {
			JSONObject obj = new JSONObject(response);
			JSONArray array = obj.optJSONArray(NewsBean.KEY_USER_ID);
			if (array != null) {
				NewsBean follower = new NewsBean();
				for (int i = 0; i < array.length(); i++) {
					try {
						news.add(follower.parse(array.optJSONObject(i)));
					} catch (NetworkException e) {
						e.printStackTrace();
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * follower数组
	 */
	private ArrayList<NewsBean> news = new ArrayList<NewsBean>();

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuffer buffer = new StringBuffer();
		for (NewsBean bean : news) {
			buffer.append(bean.toString()).append("\r\n");
		}
		return buffer.toString();
	}

	public ArrayList<NewsBean> getNews() {
		return news;
	}

}
