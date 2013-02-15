/**
 * 
 */
package com.photoshare.utils;

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

/**
 * @author Aron
 * 
 *         This class is used to write the key-value pairs contained in the User
 *         class {@link User} into XML File, and read the XML File into the User
 *         class {@link User}
 * 
 */
public class UserReader implements XMLParser<User> {

	public static final String USER_PATH = Utils.SDCARD_ABSOLUTE_PATH
			+ File.separator + Utils.APP_NAME + File.separator + Utils.DIR_USR;
	
	public static final String USER_FILE_NAME = "user.xml";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadFromXML(java.lang.String,
	 * java.lang.String)
	 */
	public User loadFromXML(String path, String file) throws Exception {

		User user = User.getInstance();
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
					if (parser.getName().equals(User.KEY_MAIL)) {
						user.setMail(parser.nextText());
					} else if (parser.getName().equals(User.KEY_PWD)) {
						user.setPwd(parser.nextText());
					} else if (parser.getName().equals(User.KEY_CONFIGURE)) {
						user.setConfigured(Boolean.parseBoolean(parser
								.nextText()));
					}
					break;
				case XmlPullParser.END_TAG:
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
		return user;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadListFromXML(java.lang.String,
	 * java.lang.String)
	 */
	public List<User> loadListFromXML(String path, String file)
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
	public void WriteXML(String path, String file, User user) throws Exception {
		// TODO Auto-generated method stub
		try {
			OutputStream outStream = FileTools.OpenFile(path, file);
			OutputStreamWriter outStreamWriter = new OutputStreamWriter(
					outStream, "UTF-8");
			BufferedWriter writer = new BufferedWriter(outStreamWriter);
			XmlSerializer serializer = Xml.newSerializer();
			serializer.setOutput(writer);
			// <?xml version=”1.0″ encoding=”UTF-8″ standalone=”yes”?>
			serializer.startDocument(Utils.ENCODE_UTF_8, true);

			serializer.startTag("", User.KEY_MAIL);
			serializer.text(user.getMail());
			serializer.endTag("", User.KEY_MAIL);

			serializer.startTag("", User.KEY_PWD);
			serializer.text(user.getPwd());
			serializer.endTag("", User.KEY_PWD);

			serializer.startTag("", User.KEY_CONFIGURE);
			serializer.text(user.isConfigured() + "");
			serializer.endTag("", User.KEY_CONFIGURE);

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
	public void WriteXML(String path, String file, List<User> types)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadFromXML(java.lang.Object,
	 * java.lang.String, java.lang.String)
	 */
	public void loadFromXML(User obj, String path, String file)
			throws Exception {
		obj = loadFromXML(path, file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadListFromXML(java.util.List,
	 * java.lang.String, java.lang.String)
	 */
	public void loadListFromXML(List<User> list, String path, String file)
			throws Exception {
		// TODO Auto-generated method stub

	}

}
