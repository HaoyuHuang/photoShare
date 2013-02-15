/**
 * 
 */
package com.photoshare.service.photos;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.photoshare.common.XMLParser;
import com.photoshare.service.comments.CommentInfo;
import com.photoshare.utils.FileTools;
import com.photoshare.utils.Utils;

/**
 * @author czj_yy
 * 
 */
public class PhotoBeanReader implements XMLParser<PhotoBean> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadFromXML(java.lang.String,
	 * java.lang.String)
	 */
	public PhotoBean loadFromXML(String path, String file) throws Exception {
		PhotoBean photo = new PhotoBean();
		ArrayList<CommentInfo> comments = new ArrayList<CommentInfo>();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			InputStream stream = FileTools.ReadFile(path, file);
			if (stream == null) {
				return null;
			}
			parser.setInput(stream, Utils.ENCODE_UTF_8);
			int eventType = parser.getEventType();
			long cid = 0;
			long uid = 0;
			String uname = "";
			String content = "";
			String time = "";
			CommentInfo comment = null;

			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {

				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if (parser.getName().equals(PhotoBean.KEY_CAPTION)) {
						photo.setCaption(parser.nextText());
					} else if (parser.getName().equals(
							PhotoBean.KEY_COMMENT_COUNT)) {
						photo.setCommentCount(Integer.parseInt(parser
								.nextText()));
					} else if (parser.getName().equals(
							PhotoBean.KEY_CREATE_TIME)) {
						photo.setCreateTime(parser.nextText());
					} else if (parser.getName().equals(PhotoBean.KEY_LARGE_URL)) {
						photo.setUrlLarge(parser.nextText());
					} else if (parser.getName().equals(
							PhotoBean.KEY_LIKES_COUNT)) {
						photo.setLikesCount(Integer.parseInt(parser.nextText()));
					} else if (parser.getName()
							.equals(PhotoBean.KEY_MIDDLE_URL)) {
						photo.setUrlHead(parser.nextText());
					} else if (parser.getName().equals(PhotoBean.KEY_PID)) {
						photo.setPid(Long.parseLong(parser.nextText()));
					} else if (parser.getName().equals(PhotoBean.KEY_TINY_URL)) {
						photo.setUrlTiny(parser.nextText());
					} else if (parser.getName().equals(PhotoBean.KEY_UID)) {
						photo.setUid(Long.parseLong(parser.nextText()));
					} else if (parser.getName().equals(PhotoBean.KEY_UNAME)) {
						photo.setUname(parser.nextText());
					} else if (parser.getName().equals(PhotoBean.KEY_UHEAD_URL)) {
						photo.setTinyHeadUrl(parser.nextText());
					} else if (parser.getName().equals(PhotoBean.KEY_COMMENTS)) {

					} else if (parser.getName().equals(
							PhotoBean.ABSOLUTE_PATH_TAG)) {
						photo.setAbsolutePath(parser.nextText());
					} else if (parser.getName().equals(CommentInfo.KEY_COMMENT)) {

					} else if (parser.getName().equals(CommentInfo.KEY_CID)) {
						cid = Long.parseLong(parser.nextText());
					} else if (parser.getName().equals(CommentInfo.KEY_CONTENT)) {
						content = parser.nextText();
					} else if (parser.getName().equals(
							CommentInfo.KEY_CREATE_TIME)) {
						time = parser.nextText();
					} else if (parser.getName().equals(CommentInfo.KEY_UID)) {
						uid = Long.parseLong(parser.nextText());
					} else if (parser.getName().equals(CommentInfo.KEY_UNAME)) {
						uname = parser.nextText();
					} else if (parser.getName().equals(PhotoBean.KEY_IS_LIKE)) {
						photo.setLike(Boolean.parseBoolean(parser.nextText()));
					}
					break;
				case XmlPullParser.END_TAG:
					if (parser.getName().equals(CommentInfo.KEY_COMMENT)) {
						comment = new CommentInfo.CommentBuilder().Cid(cid)
								.Comment(content).CreateTime(time)
								.Pid(photo.getPid()).UName(uname).Uid(uid)
								.build();
						comments.add(comment);
					} else if (parser.getName().equals(PhotoBean.KEY_PHOTO)) {
						photo.setComments(comments);
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (NumberFormatException e) {
			throw new Exception(e);
		} catch (XmlPullParserException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}
		return photo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadListFromXML(java.lang.String,
	 * java.lang.String)
	 */
	public List<PhotoBean> loadListFromXML(String path, String file)
			throws Exception {
		List<PhotoBean> photos = new ArrayList<PhotoBean>();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			InputStream stream = FileTools.ReadFile(path, file);
			if (stream == null) {
				return null;
			}
			PhotoBean photo = null;
			ArrayList<CommentInfo> comments = new ArrayList<CommentInfo>();
			parser.setInput(stream, Utils.ENCODE_UTF_8);
			int eventType = parser.getEventType();
			long cid = 0;
			long uid = 0;
			String uname = "";
			String content = "";
			String time = "";
			CommentInfo comment = null;

			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {

				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if (parser.getName().equals(PhotoBean.KEY_PHOTO)) {
						photo = new PhotoBean();
					} else if (parser.getName().equals(PhotoBean.KEY_CAPTION)) {
						photo.setCaption(parser.nextText());
					} else if (parser.getName().equals(
							PhotoBean.KEY_COMMENT_COUNT)) {
						photo.setCommentCount(Integer.parseInt(parser
								.nextText()));
					} else if (parser.getName().equals(
							PhotoBean.KEY_CREATE_TIME)) {
						photo.setCreateTime(parser.nextText());
					} else if (parser.getName().equals(PhotoBean.KEY_LARGE_URL)) {
						photo.setUrlLarge(parser.nextText());
					} else if (parser.getName().equals(
							PhotoBean.KEY_LIKES_COUNT)) {
						photo.setLikesCount(Integer.parseInt(parser.nextText()));
					} else if (parser.getName()
							.equals(PhotoBean.KEY_MIDDLE_URL)) {
						photo.setUrlHead(parser.nextText());
					} else if (parser.getName().equals(PhotoBean.KEY_PID)) {
						photo.setPid(Long.parseLong(parser.nextText()));
					} else if (parser.getName().equals(PhotoBean.KEY_TINY_URL)) {
						photo.setUrlTiny(parser.nextText());
					} else if (parser.getName().equals(PhotoBean.KEY_UID)) {
						photo.setUid(Long.parseLong(parser.nextText()));
					} else if (parser.getName().equals(PhotoBean.KEY_UNAME)) {
						photo.setUname(parser.nextText());
					} else if (parser.getName().equals(PhotoBean.KEY_UHEAD_URL)) {
						photo.setTinyHeadUrl(parser.nextText());
					} else if (parser.getName().equals(PhotoBean.KEY_COMMENTS)) {

					} else if (parser.getName().equals(
							PhotoBean.ABSOLUTE_PATH_TAG)) {
						photo.setAbsolutePath(parser.nextText());
					} else if (parser.getName().equals(CommentInfo.KEY_COMMENT)) {

					} else if (parser.getName().equals(CommentInfo.KEY_CID)) {
						cid = Long.parseLong(parser.nextText());
					} else if (parser.getName().equals(CommentInfo.KEY_CONTENT)) {
						content = parser.nextText();
					} else if (parser.getName().equals(
							CommentInfo.KEY_CREATE_TIME)) {
						time = parser.nextText();
					} else if (parser.getName().equals(CommentInfo.KEY_UID)) {
						uid = Long.parseLong(parser.nextText());
					} else if (parser.getName().equals(CommentInfo.KEY_UNAME)) {
						uname = parser.nextText();
					} else if (parser.getName().equals(PhotoBean.KEY_IS_LIKE)) {
						photo.setLike(Boolean.parseBoolean(parser.nextText()));
					}
					break;
				case XmlPullParser.END_TAG:
					if (parser.getName().equals(CommentInfo.KEY_COMMENT)) {
						comment = new CommentInfo.CommentBuilder().Cid(cid)
								.Comment(content).CreateTime(time)
								.Pid(photo.getPid()).UName(uname).Uid(uid)
								.build();
						comments.add(comment);
					} else if (parser.getName().equals(PhotoBean.KEY_PHOTO)) {
						photo.setComments(comments);
						photos.add(photo);
					}
					break;
				}
				eventType = parser.next();
			}
		} catch (NumberFormatException e) {
			throw new Exception(e);
		} catch (XmlPullParserException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}
		return photos;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#WriteXML(java.lang.String,
	 * java.lang.String, java.lang.Object)
	 */
	public void WriteXML(String path, String file, PhotoBean photo)
			throws Exception {
		try {
			OutputStream outStream = FileTools.OpenFile(path, file);
			OutputStreamWriter outStreamWriter = new OutputStreamWriter(
					outStream, "UTF-8");
			BufferedWriter writer = new BufferedWriter(outStreamWriter);
			XmlSerializer serializer = Xml.newSerializer();
			serializer.setOutput(writer);
			// <?xml version=”1.0″ encoding=”UTF-8″ standalone=”yes”?>
			serializer.startDocument(Utils.ENCODE_UTF_8, true);
			try {
				serializer.startTag("", PhotoBean.KEY_PHOTO);

				serializer.startTag("", PhotoBean.KEY_UID);
				serializer.text("" + photo.getUid());
				serializer.endTag("", PhotoBean.KEY_UID);

				serializer.startTag("", PhotoBean.KEY_UNAME);
				serializer.text(photo.getUname());
				serializer.endTag("", PhotoBean.KEY_UNAME);

				serializer.startTag("", PhotoBean.KEY_UHEAD_URL);
				serializer.text(photo.getTinyHeadUrl());
				serializer.endTag("", PhotoBean.KEY_UHEAD_URL);

				serializer.startTag("", PhotoBean.KEY_CAPTION);
				serializer.text(photo.getCaption());
				serializer.endTag("", PhotoBean.KEY_CAPTION);

				serializer.startTag("", PhotoBean.KEY_COMMENT_COUNT);
				serializer.text("" + photo.getCommentCount());
				serializer.endTag("", PhotoBean.KEY_COMMENT_COUNT);

				serializer.startTag("", PhotoBean.KEY_COMMENTS);

				for (CommentInfo info : photo.getComments()) {
					serializer.startTag("", CommentInfo.KEY_COMMENT);

					serializer.startTag("", CommentInfo.KEY_UNAME);
					serializer.text(info.getUname());
					serializer.endTag("", CommentInfo.KEY_UNAME);

					serializer.startTag("", CommentInfo.KEY_UID);
					serializer.text(info.getUid() + "");
					serializer.endTag("", CommentInfo.KEY_UID);

					serializer.startTag("", CommentInfo.KEY_CID);
					serializer.text(info.getCid() + "");
					serializer.endTag("", CommentInfo.KEY_CID);

					serializer.startTag("", CommentInfo.KEY_CONTENT);
					serializer.text(info.getComment());
					serializer.endTag("", CommentInfo.KEY_CONTENT);

					serializer.startTag("", CommentInfo.KEY_CREATE_TIME);
					serializer.text(info.getCreateTime());
					serializer.endTag("", CommentInfo.KEY_CREATE_TIME);

					serializer.endTag("", CommentInfo.KEY_COMMENT);
				}
				serializer.endTag("", PhotoBean.KEY_COMMENTS);

				serializer.startTag("", PhotoBean.KEY_CREATE_TIME);
				serializer.text("" + photo.getCreateTime());
				serializer.endTag("", PhotoBean.KEY_CREATE_TIME);

				serializer.startTag("", PhotoBean.ABSOLUTE_PATH_TAG);
				serializer.text(photo.getAbsolutePath());
				serializer.endTag("", PhotoBean.ABSOLUTE_PATH_TAG);

				serializer.startTag("", PhotoBean.KEY_LARGE_URL);
				serializer.text(photo.getUrlLarge());
				serializer.endTag("", PhotoBean.KEY_LARGE_URL);

				serializer.startTag("", PhotoBean.KEY_LIKES_COUNT);
				serializer.text("" + photo.getLikesCount());
				serializer.endTag("", PhotoBean.KEY_LIKES_COUNT);

				serializer.startTag("", PhotoBean.KEY_IS_LIKE);
				serializer.text("" + photo.isLike());
				serializer.endTag("", PhotoBean.KEY_IS_LIKE);

				serializer.startTag("", PhotoBean.KEY_MIDDLE_URL);
				serializer.text(photo.getUrlHead());
				serializer.endTag("", PhotoBean.KEY_MIDDLE_URL);

				serializer.startTag("", PhotoBean.KEY_PID);
				serializer.text("" + photo.getPid());
				serializer.endTag("", PhotoBean.KEY_PID);

				serializer.startTag("", PhotoBean.KEY_TINY_URL);
				serializer.text(photo.getUrlTiny());
				serializer.endTag("", PhotoBean.KEY_TINY_URL);

				serializer.endTag("", PhotoBean.KEY_PHOTO);
			} catch (IllegalArgumentException e) {
				throw new Exception(e);
			} catch (IllegalStateException e) {
				throw new Exception(e);
			} catch (IOException e) {
				throw new Exception(e);
			}
			serializer.endDocument();
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			throw new Exception(e);
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#WriteXML(java.lang.String,
	 * java.lang.String, java.util.List)
	 */
	public void WriteXML(String path, String file, List<PhotoBean> photos)
			throws Exception {
		try {
			OutputStream outStream = FileTools.OpenFile(path, file);
			OutputStreamWriter outStreamWriter = new OutputStreamWriter(
					outStream, "UTF-8");
			BufferedWriter writer = new BufferedWriter(outStreamWriter);
			XmlSerializer serializer = Xml.newSerializer();
			serializer.setOutput(writer);
			// <?xml version=”1.0″ encoding=”UTF-8″ standalone=”yes”?>
			serializer.startDocument(Utils.ENCODE_UTF_8, true);

			serializer.startTag("", PhotoBean.KEY_PHOTOS);

			try {
				for (PhotoBean photo : photos) {
					serializer.startTag("", PhotoBean.KEY_PHOTO);

					serializer.startTag("", PhotoBean.KEY_UID);
					serializer.text("" + photo.getUid());
					serializer.endTag("", PhotoBean.KEY_UID);

					serializer.startTag("", PhotoBean.KEY_UNAME);
					serializer.text(photo.getUname());
					serializer.endTag("", PhotoBean.KEY_UNAME);

					serializer.startTag("", PhotoBean.KEY_UHEAD_URL);
					serializer.text(photo.getTinyHeadUrl());
					serializer.endTag("", PhotoBean.KEY_UHEAD_URL);

					serializer.startTag("", PhotoBean.KEY_CAPTION);
					serializer.text(photo.getCaption());
					serializer.endTag("", PhotoBean.KEY_CAPTION);

					serializer.startTag("", PhotoBean.KEY_COMMENT_COUNT);
					serializer.text("" + photo.getCommentCount());
					serializer.endTag("", PhotoBean.KEY_COMMENT_COUNT);

					serializer.startTag("", PhotoBean.KEY_COMMENTS);

					for (CommentInfo info : photo.getComments()) {
						serializer.startTag("", CommentInfo.KEY_COMMENT);

						serializer.startTag("", CommentInfo.KEY_UNAME);
						serializer.text(info.getUname());
						serializer.endTag("", CommentInfo.KEY_UNAME);

						serializer.startTag("", CommentInfo.KEY_UID);
						serializer.text(info.getUid() + "");
						serializer.endTag("", CommentInfo.KEY_UID);

						serializer.startTag("", CommentInfo.KEY_CID);
						serializer.text(info.getCid() + "");
						serializer.endTag("", CommentInfo.KEY_CID);

						serializer.startTag("", CommentInfo.KEY_CONTENT);
						serializer.text(info.getComment());
						serializer.endTag("", CommentInfo.KEY_CONTENT);

						serializer.startTag("", CommentInfo.KEY_CREATE_TIME);
						serializer.text(info.getCreateTime());
						serializer.endTag("", CommentInfo.KEY_CREATE_TIME);

						serializer.endTag("", CommentInfo.KEY_COMMENT);
					}
					serializer.endTag("", PhotoBean.KEY_COMMENTS);

					serializer.startTag("", PhotoBean.KEY_CREATE_TIME);
					serializer.text("" + photo.getCreateTime());
					serializer.endTag("", PhotoBean.KEY_CREATE_TIME);

					serializer.startTag("", PhotoBean.ABSOLUTE_PATH_TAG);
					serializer.text(photo.getAbsolutePath());
					serializer.endTag("", PhotoBean.ABSOLUTE_PATH_TAG);

					serializer.startTag("", PhotoBean.KEY_LARGE_URL);
					serializer.text(photo.getUrlLarge());
					serializer.endTag("", PhotoBean.KEY_LARGE_URL);

					serializer.startTag("", PhotoBean.KEY_LIKES_COUNT);
					serializer.text("" + photo.getLikesCount());
					serializer.endTag("", PhotoBean.KEY_LIKES_COUNT);

					serializer.startTag("", PhotoBean.KEY_IS_LIKE);
					serializer.text("" + photo.isLike());
					serializer.endTag("", PhotoBean.KEY_IS_LIKE);

					serializer.startTag("", PhotoBean.KEY_MIDDLE_URL);
					serializer.text(photo.getUrlHead());
					serializer.endTag("", PhotoBean.KEY_MIDDLE_URL);

					serializer.startTag("", PhotoBean.KEY_PID);
					serializer.text("" + photo.getPid());
					serializer.endTag("", PhotoBean.KEY_PID);

					serializer.startTag("", PhotoBean.KEY_TINY_URL);
					serializer.text(photo.getUrlTiny());
					serializer.endTag("", PhotoBean.KEY_TINY_URL);

					serializer.endTag("", PhotoBean.KEY_PHOTO);

				}
				serializer.endTag("", PhotoBean.KEY_PHOTOS);
			} catch (IllegalArgumentException e) {
				throw new Exception(e);
			} catch (IllegalStateException e) {
				throw new Exception(e);
			} catch (IOException e) {
				throw new Exception(e);
			}

			serializer.endDocument();
			writer.flush();
			writer.close();
		} catch (FileNotFoundException e) {
			throw new Exception(e);
		} catch (UnsupportedEncodingException e) {
			throw new Exception(e);
		} catch (IOException e) {
			throw new Exception(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadFromXML(java.lang.Object,
	 * java.lang.String, java.lang.String)
	 */
	public void loadFromXML(PhotoBean obj, String path, String file)
			throws Exception {
		obj = loadFromXML(path, file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadListFromXML(java.util.List,
	 * java.lang.String, java.lang.String)
	 */
	public void loadListFromXML(List<PhotoBean> list, String path, String file)
			throws Exception {
		list.addAll(loadListFromXML(path, file));
	}
}
