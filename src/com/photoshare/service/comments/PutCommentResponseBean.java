/**
 * 
 */
package com.photoshare.service.comments;

import org.json.JSONException;
import org.json.JSONObject;

import com.photoshare.common.ResponseBean;
import com.photoshare.exception.NetworkException;

/**
 * @author czj_yy
 * 
 */
public class PutCommentResponseBean extends ResponseBean {

	private CommentInfo comment;

	/**
	 * @param response
	 */
	public PutCommentResponseBean(String response) {
		super(response);
		// TODO Auto-generated constructor stub
		try {
			JSONObject json = new JSONObject(response);
			comment = new CommentInfo();
			comment.parse(json);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NetworkException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public String toString() {
		return comment.toString();
	}

	public CommentInfo getComment() {
		return comment;
	}

}
