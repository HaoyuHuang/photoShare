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
public class UserGetNewsResponseBean extends ResponseBean {

	/**
	 * 构造UsersGetInfoResponseBean对象
	 * 
	 * @param response
	 *            返回的数据 json格式
	 * @throws Exception
	 */
	public UserGetNewsResponseBean(String response) {
		super(response);
		if (response == null) {
			return;
		}

		try {
			JSONObject obj = new JSONObject(response);
			JSONArray array = obj.optJSONArray(NewsBean.KEY_NEWS);
			if (array != null) {
				for (int i = 0; i < array.length(); i++) {
					NewsBean feed = new NewsBean();
					try {
						feed.parse(array.optJSONObject(i));
						System.out.println(feed);
						news.add(feed);
					} catch (NetworkException e) {
						// TODO Auto-generated catch block
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
