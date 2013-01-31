/**
 * 
 */
package com.photoshare.service.comments.views;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.photoshare.service.comments.CommentInfo;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.async.AsyncImageLoader.ImageCallback;
import com.photoshare.utils.async.AsyncUtils;
import com.photoshare.view.UserTextView;

/**
 * @author czj_yy
 * 
 */
public class CommentItemView {

	private UserTextView userNameView;
	private TextView userComment;
	private TextView userDate;
	private ImageView userHead;
	private CommentInfo comment;
	private View baseView;
	private AsyncUtils async;

	/**
	 * @param comment
	 * @param baseView
	 * @param async
	 * @param context
	 */
	public CommentItemView(CommentInfo comment, View baseView, AsyncUtils async) {
		super();
		this.comment = comment;
		this.baseView = baseView;
		this.async = async;
	}

	public void applyView() {

		userComment = (TextView) baseView.findViewById(R.id.comment);
		userDate = (TextView) baseView.findViewById(R.id.commentDate);
		userHead = (ImageView) baseView.findViewById(R.id.commentHead);
		userComment.setText(comment.getComment());
		userDate.setText(comment.getCreateTime());

		try {
			userNameView = new UserTextView(
					(TextView) baseView.findViewById(R.id.newsPopularName),
					new UserInfo.UserInfoBuilder().ID(comment.getUid())
							.Name(comment.getUname()).build(),
					comment.getUname());
			userNameView.registerListener(listener);
			userNameView.apply();
			async.loadDrawableFromWeb(comment.getTinyHead(),
					new ImageCallback() {

						public void imageLoaded(Drawable imageDrawable,
								String imageUrl) {
							if (mCallback != null) {
								mCallback.OnUserHeadLoaded(userHead,
										imageDrawable, imageUrl);
							}
						}

						public void imageDefault() {
							if (mCallback != null) {
								mCallback.OnImageDefaule(userHead);
							}
						}

					});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			userHead.setImageResource(R.drawable.icon);
		}
	}

	private UserTextView.UserTextOnClickListener listener = new UserTextView.UserTextOnClickListener() {

		public void OnClick(UserInfo info) {
			// TODO Auto-generated method stub
			if (mCallback != null) {
				mCallback.OnNameClick(info);
			}
		}
	};

	private ICallback mCallback;

	public void registerCallback(ICallback mCallback) {
		this.mCallback = mCallback;
	}

	public interface ICallback {
		public void OnNameClick(UserInfo info);

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url);

		public void OnImageDefaule(ImageView image);
	}

}
