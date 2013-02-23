package com.photoshare.utils.async;

import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import android.graphics.Bitmap;
import android.os.Bundle;

import com.photoshare.common.AbstractRequestListener;
import com.photoshare.common.XMLParser;
import com.photoshare.exception.NetworkError;
import com.photoshare.exception.NetworkException;
import com.photoshare.service.CommentHelper;
import com.photoshare.service.FindFriendHelper;
import com.photoshare.service.FollowHelper;
import com.photoshare.service.LikeHelper;
import com.photoshare.service.NewsHelper;
import com.photoshare.service.PhotoHelper;
import com.photoshare.service.SignInHelper;
import com.photoshare.service.SignUpHelper;
import com.photoshare.service.UserInfoHelper;
import com.photoshare.service.comments.CommentsGetInfoRequestParam;
import com.photoshare.service.comments.CommentsGetInfoResponseBean;
import com.photoshare.service.comments.PutCommentRequestParam;
import com.photoshare.service.findfriends.FindFriendsRequestParam;
import com.photoshare.service.findfriends.FindFriendsResponseBean;
import com.photoshare.service.follow.UserFollowRequestParam;
import com.photoshare.service.follow.UserGetFollowInfoRequestParam;
import com.photoshare.service.follow.UserGetFollowInfoResponseBean;
import com.photoshare.service.likes.LikeGetInfoRequestParam;
import com.photoshare.service.likes.LikeGetInfoResponseBean;
import com.photoshare.service.likes.PhotoLikeRequestParam;
import com.photoshare.service.news.FollowGetNewsRequestParam;
import com.photoshare.service.news.FollowGetNewsResponseBean;
import com.photoshare.service.news.UserGetNewsRequestParam;
import com.photoshare.service.news.UserGetNewsResponseBean;
import com.photoshare.service.photos.EditPhotoType;
import com.photoshare.service.photos.PhotoGetInfoRequestParam;
import com.photoshare.service.photos.PhotoGetInfoResponseBean;
import com.photoshare.service.photos.PhotoUploadRequestParam;
import com.photoshare.service.photos.PhotoUploadResponseBean;
import com.photoshare.service.photos.PhotosGetInfoRequestParam;
import com.photoshare.service.photos.PhotosGetInfoResponseBean;
import com.photoshare.service.signin.UserSignInRequestParam;
import com.photoshare.service.signin.UserSignInResponseBean;
import com.photoshare.service.signup.UserSignUpRequestParam;
import com.photoshare.service.signup.UserSignUpResponseBean;
import com.photoshare.service.users.UserEditInfoRequestParam;
import com.photoshare.service.users.UserGetInfoRequestParam;
import com.photoshare.service.users.UserGetInfoResponseBean;
import com.photoshare.service.users.UserGetOtherInfoRequestParam;
import com.photoshare.service.users.UserInfo;
import com.photoshare.service.users.UserInfoReader;
import com.photoshare.service.users.UserPrivacyRequestParam;
import com.photoshare.service.users.UserPrivacyResponseBean;
import com.photoshare.utils.RequestListener;
import com.photoshare.utils.User;
import com.photoshare.utils.UserReader;
import com.photoshare.utils.Utils;
import com.photoshare.utils.async.AsyncImageLoader.ImageCallback;

public class AsyncUtils {
	/** 线程池 */
	private Executor pool;

	private User user = User.getInstance();

	private AsyncImageLoader imageLoader = new AsyncImageLoader();

	private static AsyncUtils async = new AsyncUtils();

	public static AsyncUtils getInstance() {
		return async;
	}

	private AsyncUtils() {
		this.pool = Executors.newFixedThreadPool(2);
	}

	public void setUser(User user) {
		this.user = user;
	}

	/**
	 * 异步上传照片
	 * */
	public void publishPhoto(final String method, final byte[] photo,
			final String fileName, final String description,
			final String format, final RequestListener listener) {
		pool.execute(new Runnable() {

			public void run() {

				try {
					String resp = user.publishPhoto(method, photo, fileName,
							description, format);
					NetworkError Error = Utils.parseNetworkError(resp);
					if (Error != null) {
						listener.onNetworkError(Error);
					} else {
						listener.onComplete(resp);
					}
				} catch (Throwable e) {
					listener.onFault(e);
				}
			}
		});
	}

	/**
	 * 异步获取用户信息接口<br>
	 * 
	 * @param param
	 */
	public void getUsersInfo(UserGetInfoRequestParam param,
			AbstractRequestListener<UserGetInfoResponseBean> listener)
			throws NetworkException {
		checkLoggingStatus();
		new UserInfoHelper(user).asyncGetUsersInfo(pool, param, listener);
	}

	public void getOthersInfo(UserGetOtherInfoRequestParam param,
			AbstractRequestListener<UserGetInfoResponseBean> listener)
			throws NetworkException {
		checkLoggingStatus();
		new UserInfoHelper(user).asyncGetOthersInfo(pool, param, listener);
	}

	public void getFollowsInfo(UserGetFollowInfoRequestParam param,
			AbstractRequestListener<UserGetFollowInfoResponseBean> listener)
			throws NetworkException {
		checkLoggingStatus();
		new FollowHelper(user).asyncGetFollowInfo(pool, param, listener);
	}

	public void getEditUserInfo(UserEditInfoRequestParam param,
			AbstractRequestListener<UserGetInfoResponseBean> listener)
			throws NetworkException {
		checkLoggingStatus();
		new UserInfoHelper(user).asyncEditUsersInfo(pool, param, listener);
	}

	/**
	 * 异步获取图片集接口
	 * 
	 * @param param
	 * @throws NetworkException
	 * */
	public void getPhotoInfo(PhotoGetInfoRequestParam param,
			AbstractRequestListener<PhotoGetInfoResponseBean> listener)
			throws NetworkException {
		// checkLoggingStatus();
		new PhotoHelper(user).asyncGetPhotoInfo(pool, param, listener);
	}

	/**
	 * 异步获取图片集接口
	 * 
	 * @param param
	 * @throws NetworkException
	 * */
	public void getPhotosInfo(PhotosGetInfoRequestParam param,
			AbstractRequestListener<PhotosGetInfoResponseBean> listener)
			throws NetworkException {
		// checkLoggingStatus();
		new PhotoHelper(user).asyncGetPhotosInfo(pool, param, listener);
	}

	public void getFollowNews(FollowGetNewsRequestParam param,
			AbstractRequestListener<FollowGetNewsResponseBean> listener)
			throws NetworkException {
		checkLoggingStatus();
		new NewsHelper(user).asyncGetFollowNews(pool, param, listener);
	}

	public void getUserNews(UserGetNewsRequestParam param,
			AbstractRequestListener<UserGetNewsResponseBean> listener)
			throws NetworkException {
		checkLoggingStatus();
		new NewsHelper(user).asyncGetUserNews(pool, param, listener);
	}

	/**
	 * 异步获取评论集接口
	 * 
	 * @param param
	 * @throws NetworkException
	 * */
	public void getCommentsInfo(CommentsGetInfoRequestParam param,
			AbstractRequestListener<CommentsGetInfoResponseBean> listener)
			throws NetworkException {
		checkLoggingStatus();
		new CommentHelper(user).asyncGetCommentsInfo(pool, param, listener);
	}

	public void getLikesInfo(LikeGetInfoRequestParam param,
			AbstractRequestListener<LikeGetInfoResponseBean> listener)
			throws NetworkException {
		checkLoggingStatus();
		new LikeHelper(user).asyncGetLikesInfo(pool, param, listener);
	}

	public void getFriendsInfo(FindFriendsRequestParam param,
			AbstractRequestListener<FindFriendsResponseBean> listener)
			throws NetworkException {
		checkLoggingStatus();
		new FindFriendHelper(user).asyncGetFriendsInfo(pool, param, listener);
	}

	public void setPrivacy(UserPrivacyRequestParam param,
			AbstractRequestListener<UserPrivacyResponseBean> listener)
			throws NetworkException {
		checkLoggingStatus();
		new UserInfoHelper(user).asyncSetPrivacy(pool, param, listener);
	}

	/**
	 * 异步上传照片
	 * 
	 * @throws NetworkException
	 * */
	public void publishPhoto(PhotoUploadRequestParam photo,
			AbstractRequestListener<PhotoUploadResponseBean> listener)
			throws NetworkException {
//		checkLoggingStatus();
		new PhotoHelper(user).asyncUploadPhoto(pool, photo, listener);
	}

	public void publishLikePhoto(PhotoLikeRequestParam like,
			LikeHelper.ICallback mCallback) throws NetworkException {
//		checkLoggingStatus();
		new LikeHelper(user).publishLikePhoto(like, mCallback);
	}

	public void publishFollow(UserFollowRequestParam follow,
			FollowHelper.ICallback mCallback) throws NetworkException {
//		checkLoggingStatus();
		new FollowHelper(user).publishFollow(follow, mCallback);
	}

	public void publishComments(PutCommentRequestParam param,
			CommentHelper.ICallback mCallback) throws NetworkException {
//		checkLoggingStatus();
		new CommentHelper(user).publishComment(param, mCallback);
	}

	public void SignIn(UserSignInRequestParam param,
			AbstractRequestListener<UserSignInResponseBean> listener) {
		new SignInHelper(user).asyncSignIn(pool, param, listener);
	}

	public void SignUp(UserSignUpRequestParam param,
			AbstractRequestListener<UserSignUpResponseBean> listener) {
		new SignUpHelper(user).asyncSignUp(pool, param, listener);
	}

	public void loadDrawableFromFile(final String imageUrl,
			final AsyncImageLoader.ImageCallback mCallback) {
		imageLoader.loadImageFromFileUrl(pool, imageUrl, mCallback);
	}

	public void loadDrawableFromWeb(final String imageUrl,
			final AsyncImageLoader.ImageCallback mCallback) {
		imageLoader.loadImageFromWebUrl(pool, imageUrl, mCallback);
	}

	public void decorateImage(final EditPhotoType type, final Bitmap raw,
			ImageCallback mCallback) {
		imageLoader.docorateImage(pool, type, raw, mCallback);
	}

	public void request(final String action, final Bundle parameters,
			final AbstractRequestListener<String> listener) {
		AsyncRequest request = new AsyncRequest(parameters, action, listener);
		pool.execute(request);
	}

	public void readUserInfo(final UserReader reader,
			final UserInfoReader infoReader,
			final AbstractRequestListener<UserInfo> listener) {
		pool.execute(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				try {
					user = reader.loadFromXML(UserReader.USER_PATH,
							UserReader.USER_FILE_NAME);
					UserInfo info = infoReader.loadFromXML(UserInfoReader.PATH,
							UserInfoReader.FILE_NAME);
					user.setUserInfo(info);
					if (listener != null) {
						listener.onComplete(info);
					}
				} catch (Exception e) {
					if (listener != null) {
						listener.onFault(e);
					}
				}
			}

		});
	}

	public void writeUserInfo(final UserReader writer,
			final UserInfoReader infoWriter,
			final AbstractRequestListener<UserInfo> listener) {
		pool.execute(new Runnable() {

			public void run() {
				// TODO Auto-generated method stub
				try {
					writer.WriteXML(UserReader.USER_PATH,
							UserReader.USER_FILE_NAME, user);
					infoWriter.WriteXML(UserInfoReader.PATH,
							UserInfoReader.FILE_NAME, user.getUserInfo());
					if (listener != null) {
						listener.onComplete(null);
					}
				} catch (Exception e) {
					if (listener != null) {
						listener.onFault(e);
					}
				}
			}

		});
	}

	public <Type> void readXML(final XMLParser<Type> reader, final String path,
			final String file, final AbstractRequestListener<Type> listener) {

		pool.execute(new Runnable() {
			Type type;

			public void run() {
				try {
					type = reader.loadFromXML(path, file);
					if (listener != null) {
						listener.onComplete(type);
					}
				} catch (Exception e) {
					if (listener != null) {
						listener.onFault(e);
					}
				}

			}
		});
	}

	public <Type> void readXMLList(final XMLParser<Type> reader,
			final String path, final String file,
			final AbstractRequestListener<List<Type>> listener) {
		pool.execute(new Runnable() {
			List<Type> list;

			public void run() {
				try {
					list = reader.loadListFromXML(path, file);
					if (listener != null) {
						listener.onComplete(list);
					}
				} catch (Exception e) {
					if (listener != null) {
						listener.onFault(e);
					}
				}
			}
		});
	}

	public <Type> void writeXML(final XMLParser<Type> writer,
			final String path, final String file, final Type obj,
			final AbstractRequestListener<Type> listener) {
		pool.execute(new Runnable() {

			public void run() {
				try {
					writer.WriteXML(path, file, obj);
					if (listener != null) {
						listener.onComplete(obj);
					}
				} catch (Exception e) {
					if (listener != null) {
						listener.onFault(e);
					}
				}
			}
		});
	}

	public <Type> void writeXMLList(final XMLParser<Type> writer,
			final String path, final String file, final List<Type> obj,
			final AbstractRequestListener<List<Type>> listener) {
		pool.execute(new Runnable() {

			public void run() {
				try {
					writer.WriteXML(path, file, obj);
					if (listener != null) {
						listener.onComplete(obj);
					}
				} catch (Exception e) {
					if (listener != null) {
						listener.onFault(e);
					}
				}
			}
		});
	}

	private void checkLoggingStatus() throws NetworkException {
		if (!user.isLogging()) {
			throw new NetworkException(NetworkException.LOGGING_EXCEPTION);
		}
	}

	private final class AsyncRequest implements Runnable {

		private String resp;

		private Bundle parameters;

		private String action;

		private AbstractRequestListener<String> listener;

		public AsyncRequest(Bundle parameters, String action,
				AbstractRequestListener<String> listener) {
			super();
			this.parameters = parameters;
			this.action = action;
			this.listener = listener;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see java.lang.Runnable#run()
		 */
		public void run() {

			try {
				resp = user.request(action, parameters);
				if (resp != null) {
					Utils.checkResponse(resp);

				} else {
					Utils.logger("null response");
					if (listener != null) {
						listener.onFault(new NetworkException(
								NetworkError.ERROR_CODE_UNKNOWN_ERROR,
								"null response", "null response"));
					}
				}
				if (listener != null) {
					listener.onComplete(resp);
				}
			} catch (RuntimeException e) {
				if (listener != null) {
					listener.onFault(e);
				}
			} catch (NetworkException e) {
				listener.onNetworkError(new NetworkError(e.getMessage()));
			}
		}

	}
}
