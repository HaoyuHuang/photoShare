package com.photoshare.service.comments;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.photoshare.common.ResponseBean;
import com.photoshare.exception.NetworkException;

public class CommentsGetInfoResponseBean extends ResponseBean {

	private ArrayList<CommentInfo> comments = null;

	public ArrayList<CommentInfo> getComments() {
		return this.comments;
	}

	public CommentsGetInfoResponseBean(String response) {
		super(response);
		// TODO Auto-generated constructor stub
		if (response == null) {
			return;
		}

		try {
			JSONObject obj = new JSONObject(response);
			JSONArray array = obj.optJSONArray(CommentInfo.KEY_COMMENTS);
			if (array != null) {
				comments = new ArrayList<CommentInfo>();
				int size = array.length();
				for (int i = 0; i < size; i++) {
					CommentInfo info = new CommentInfo();
					info.parse(array.optJSONObject(i));
					if (info != null) {
						System.out.println(info);
						comments.add(info);
					}
				}
			}
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (NetworkException e) {
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (comments != null) {
			for (CommentInfo info : comments) {
				sb.append(info.toString()).append("\r\n");
			}
		}
		return sb.toString();
	}
}
