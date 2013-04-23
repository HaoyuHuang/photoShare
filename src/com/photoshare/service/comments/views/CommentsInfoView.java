/**
 * 
 */
package com.photoshare.service.comments.views;

import java.util.List;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.photoshare.service.comments.CommentInfo;
import com.photoshare.service.photos.PhotoBean;
import com.photoshare.service.users.UserInfo;
import com.photoshare.tabHost.R;
import com.photoshare.utils.Format;
import com.photoshare.utils.async.AsyncUtils;
import com.photoshare.view.listview.MyListView;

/**
 * @author czj_yy
 * 
 */
public class CommentsInfoView {

	private Context context;
	private View baseView;
	private MyListView mCommentsView = null;
	private EditText comments;
	private Button mCommentSubmit;
	private CommentAdapter mCommentAdapter;
	private PhotoBean photoBean;
	private AsyncUtils async;
	private int currentPage = 1;
	private int demandPage = 20;
	private int maxPage;
	private boolean initiated;

	/**
	 * @param context
	 * @param baseView
	 * @param photoBean
	 * @param async
	 */
	public CommentsInfoView(Context context, View baseView,
			PhotoBean photoBean, AsyncUtils async) {
		super();
		this.context = context;
		this.baseView = baseView;
		this.photoBean = photoBean;
		this.async = async;
		currentPage = photoBean.getComments().size();
		maxPage = photoBean.getCommentCount();
	}

	public void applyView() {
		if (initiated())
			return;
		initiated = true;
		mCommentAdapter = new CommentAdapter();
		mCommentsView = (MyListView) baseView.findViewById(R.id.commentlist);
		mCommentsView.setAdapter(mCommentAdapter);
		comments = (EditText) baseView.findViewById(R.id.putCommentEditText);
		mCommentSubmit = (Button) baseView
				.findViewById(R.id.putCommentSubmitBtn);

		mCommentSubmit.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				if (!Format.isNullorEmpty(comments.getText().toString())) {
					if (onCommentInfoClickListener != null) {
						onCommentInfoClickListener.OnPutComment(comments
								.getText().toString());
					}
				}
			}
		});

	}

	private boolean initiated() {
		if (initiated)
			return true;
		return false;
	}

	private class CommentAdapter extends ArrayAdapter<CommentInfo> {

		/**
		 * @param context
		 * @param textViewResourceId
		 * @param objects
		 */
		public CommentAdapter() {
			super(context, 0, photoBean.getComments());
			// TODO Auto-generated constructor stub
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			View rowView = convertView;
			CommentItemView commentView;
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (rowView == null) {
				rowView = inflater.inflate(R.layout.simple_list_item_comment,
						null);
				CommentInfo comment = getItem(position);
				commentView = new CommentItemView(comment, rowView, async);
				rowView.setTag(commentView);
			} else {
				commentView = (CommentItemView) rowView.getTag();
			}
			commentView.registerCallback(callback);
			commentView.applyView();

			return rowView;
		}

	}

	public void addComments(List<CommentInfo> comments) {
		photoBean.getComments().addAll(comments);
		currentPage += comments.size();
		notifyDataChanged();
	}

	public void addComment(CommentInfo comment) {
		photoBean.getComments().add(comment);
		currentPage += 1;
		notifyDataChanged();
	}

	private boolean isMaxPage() {
		if (currentPage == maxPage)
			return true;
		return false;
	}

	private void notifyDataChanged() {
		if (mCommentAdapter != null) {
			mCommentAdapter.notifyDataSetChanged();
		}
	}

	private CommentItemView.ICallback callback = new CommentItemView.ICallback() {

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url) {
			if (onCommentInfoClickListener != null) {
				onCommentInfoClickListener.OnUserHeadLoaded(image, drawable,
						url);
			}
		}

		public void OnNameClick(UserInfo info) {
			if (onCommentInfoClickListener != null) {
				onCommentInfoClickListener.OnNameClicked(info);
			}

		}

		public void OnImageDefaule(ImageView image) {
			if (onCommentInfoClickListener != null) {
				onCommentInfoClickListener.OnImageDefault(image);
			}

		}
	};

	public interface OnCommentInfoClickListener {
		public void OnPutComment(String comment);

		public void OnLoadMore(int currentPage, int demandPage);

		public void OnNameClicked(UserInfo info);

		public void OnLoadAll();

		public void OnUserHeadLoaded(ImageView image, Drawable drawable,
				String url);

		public void OnImageDefault(ImageView image);
	}

	private OnCommentInfoClickListener onCommentInfoClickListener;

	public void registerListener(
			OnCommentInfoClickListener onCommentInfoClickListener) {
		this.onCommentInfoClickListener = onCommentInfoClickListener;
	}
}
