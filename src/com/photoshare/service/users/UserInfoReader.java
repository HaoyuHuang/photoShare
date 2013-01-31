/**
 * 
 */
package com.photoshare.service.users;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import android.util.Xml;

import com.photoshare.common.XMLParser;
import com.photoshare.utils.FileTools;
import com.photoshare.utils.Utils;

/**
 * @author Aron
 * 
 *         This class is used to write the key-value pairs from the JavaBean
 *         {@link UserInfo} into XML File, and read the XML File into JavaBean
 *         {@link UserInfo}
 * 
 */
public class UserInfoReader implements XMLParser<UserInfo> {

	public static final String PATH = Utils.APP_NAME + File.separator
			+ Utils.DIR_HOME + File.separator + Utils.DIR_USER_INFO;

	public static final String FILE_NAME = "userinfo.xml";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadFromXML(java.lang.String,
	 * java.lang.String)
	 */
	public UserInfo loadFromXML(String path, String file) throws Exception {
		UserInfo info = new UserInfo();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			InputStream stream = FileTools.ReadFile(path, file);
			parser.setInput(stream, Utils.ENCODE_UTF_8);
			int eventType = parser.getEventType();
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if (parser.getName().equals(UserInfo.KEY_BIO)) {
						info.setBio(parser.nextText());
					} else if (parser.getName().equals(UserInfo.KEY_BIRTHDAY)) {
						info.setBirthday(parser.nextText());
					} else if (parser.getName().equals(UserInfo.KEY_GENDER)) {
						info.setGender(parser.nextText());
					} else if (parser.getName().equals(
							UserInfo.KEY_LARGE_HEAD_URL)) {
						info.setLargeurl(parser.nextText());
					} else if (parser.getName().equals(
							UserInfo.KEY_MIDDLE_HEAD_URL)) {
						info.setHeadurl(parser.nextText());
					} else if (parser.getName().equals(UserInfo.KEY_NAME)) {
						info.setName(parser.nextText());
					} else if (parser.getName().equals(
							UserInfo.KEY_PHONE_NUMBER)) {
						info.setPhoneNumber(parser.nextText());
					} else if (parser.getName().equals(UserInfo.KEY_PHOTOS_CNT)) {
						info.setPhotosCnt(Integer.parseInt(parser.nextText()));
					} else if (parser.getName().equals(UserInfo.KEY_PRIVACY)) {
						info.setPrivacy(Boolean.parseBoolean(parser.nextText()));
					} else if (parser.getName().equals(
							UserInfo.KEY_TINY_HEAD_URL)) {
						info.setTinyurl(parser.nextText());
					} else if (parser.getName().equals(UserInfo.KEY_UID)) {
						info.setUid(Integer.parseInt(parser.nextText()));
					} else if (parser.getName().equals(UserInfo.KEY_WEBSITE)) {
						info.setWebsite(parser.nextText());
					} else if (parser.getName().equals(UserInfo.KEY_MAIL)) {
						info.setMail(parser.nextText());
					} else if (parser.getName().equals(UserInfo.KEY_LIKES_CNT)) {
						info.setLikesCnt(Long.parseLong(parser.nextText()));
					} else if (parser.getName()
							.equals(UserInfo.KEY_PSEUDO_NAME)) {
						info.setPseudoName(parser.nextText());
					}
					break;
				case XmlPullParser.END_TAG:
					if ("person".equals(parser.getName())) {
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
		return info;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadListFromXML(java.lang.String,
	 * java.lang.String)
	 */
	public List<UserInfo> loadListFromXML(String path, String file)
			throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#WriteXML(java.lang.String,
	 * java.lang.String, java.lang.Object)
	 */
	public void WriteXML(String path, String file, UserInfo info)
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
			serializer.startTag("", UserInfo.KEY_UID);
			serializer.text("" + info.getUid());
			serializer.endTag("", UserInfo.KEY_UID);

			serializer.startTag("", UserInfo.KEY_NAME);
			serializer.text(info.getName());
			serializer.endTag("", UserInfo.KEY_NAME);

			serializer.startTag("", UserInfo.KEY_PSEUDO_NAME);
			serializer.text(info.getPseudoName());
			serializer.endTag("", UserInfo.KEY_PSEUDO_NAME);

			serializer.startTag("", UserInfo.KEY_MAIL);
			serializer.text(info.getMail());
			serializer.endTag("", UserInfo.KEY_MAIL);

			serializer.startTag("", UserInfo.KEY_BIRTHDAY);
			serializer.text(info.getBirthday());
			serializer.endTag("", UserInfo.KEY_BIRTHDAY);

			serializer.startTag("", UserInfo.KEY_BIO);
			serializer.text(info.getBio());
			serializer.endTag("", UserInfo.KEY_BIO);

			serializer.startTag("", UserInfo.KEY_GENDER);
			serializer.text(info.getGender());
			serializer.endTag("", UserInfo.KEY_GENDER);

			serializer.startTag("", UserInfo.KEY_WEBSITE);
			serializer.text(info.getWebsite());
			serializer.endTag("", UserInfo.KEY_WEBSITE);

			serializer.startTag("", UserInfo.KEY_IS_FOLLOWING);
			serializer.text(info.isFollowing() + "");
			serializer.endTag("", UserInfo.KEY_IS_FOLLOWING);

			serializer.startTag("", UserInfo.KEY_FOLLOWER_CNT);
			serializer.text(info.getFollowersCnt() + "");
			serializer.endTag("", UserInfo.KEY_FOLLOWER_CNT);

			serializer.startTag("", UserInfo.KEY_FOLLOWING_CNT);
			serializer.text(info.getFollowingCnt() + "");
			serializer.endTag("", UserInfo.KEY_FOLLOWING_CNT);

			serializer.startTag("", UserInfo.KEY_LARGE_HEAD_URL);
			serializer.text(info.getLargeurl());
			serializer.endTag("", UserInfo.KEY_LARGE_HEAD_URL);

			serializer.startTag("", UserInfo.KEY_MIDDLE_HEAD_URL);
			serializer.text(info.getHeadurl());
			serializer.endTag("", UserInfo.KEY_MIDDLE_HEAD_URL);

			serializer.startTag("", UserInfo.KEY_PRIVACY);
			serializer.text("" + info.isPrivacy());
			serializer.endTag("", UserInfo.KEY_PRIVACY);

			serializer.startTag("", UserInfo.KEY_TINY_HEAD_URL);
			serializer.text(info.getTinyurl());
			serializer.endTag("", UserInfo.KEY_TINY_HEAD_URL);

			serializer.startTag("", UserInfo.KEY_LIKES_CNT);
			serializer.text("" + info.getLikesCnt());
			serializer.endTag("", UserInfo.KEY_LIKES_CNT);

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
	public void WriteXML(String path, String file, List<UserInfo> types)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadFromXML(java.lang.Object,
	 * java.lang.String, java.lang.String)
	 */
	public void loadFromXML(UserInfo obj, String path, String file)
			throws Exception {
		obj = loadFromXML(path, file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadListFromXML(java.util.List,
	 * java.lang.String, java.lang.String)
	 */
	public void loadListFromXML(List<UserInfo> list, String path, String file)
			throws Exception {
		if (list != null) {
			list.addAll(loadListFromXML(path, file));
		}
	}

}
