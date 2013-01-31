/**
 * 
 */
package com.photoshare.service.share;

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
import com.photoshare.utils.FileTools;
import com.photoshare.utils.Utils;

/**
 * @author Aron
 * 
 */
public class ShareBeanReader implements XMLParser<ShareBean> {

	public static final String PATH = "";

	public static final String FILE = "";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadFromXML(java.lang.String,
	 * java.lang.String)
	 */
	public ShareBean loadFromXML(String path, String file) throws Exception {
		ShareBean share = new ShareBean();
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
					if (parser.getName().equals(ShareBean.KEY_SHARE_ACCOUNT)) {
						share.setmShareAccount(parser.nextText());
					} else if (parser.getName().equals(ShareBean.KEY_SHARE_PWD)) {
						share.setmSharePwd(parser.nextText());
					} else if (parser.getName()
							.equals(ShareBean.KEY_SHARE_TYPE)) {
						share.setmShareType(ShareType.Switch(Integer
								.parseInt(parser.nextText())));
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
		return share;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadFromXML(java.lang.Object,
	 * java.lang.String, java.lang.String)
	 */
	public void loadFromXML(ShareBean obj, String path, String file)
			throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadListFromXML(java.lang.String,
	 * java.lang.String)
	 */
	public List<ShareBean> loadListFromXML(String path, String file)
			throws Exception {
		List<ShareBean> list = new ArrayList<ShareBean>();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			InputStream stream = FileTools.ReadFile(path, file);
			parser.setInput(stream, Utils.ENCODE_UTF_8);
			int eventType = parser.getEventType();
			ShareBean share = null;
			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {
				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if (parser.getName().equals(ShareBean.KEY_SHARE_BEAN)) {
						share = new ShareBean();
					} else if (parser.getName().equals(
							ShareBean.KEY_SHARE_ACCOUNT)) {
						share.setmShareAccount(parser.nextText());
					} else if (parser.getName().equals(ShareBean.KEY_SHARE_PWD)) {
						share.setmSharePwd(parser.nextText());
					} else if (parser.getName()
							.equals(ShareBean.KEY_SHARE_TYPE)) {
						share.setmShareType(ShareType.Switch(Integer
								.parseInt(parser.nextText())));
					}
					break;
				case XmlPullParser.END_TAG:
					if (parser.getName().equals(ShareBean.KEY_SHARE_BEAN)) {
						list.add(share);
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
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadListFromXML(java.util.List,
	 * java.lang.String, java.lang.String)
	 */
	public void loadListFromXML(List<ShareBean> list, String path, String file)
			throws Exception {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#WriteXML(java.lang.String,
	 * java.lang.String, java.lang.Object)
	 */
	public void WriteXML(String path, String file, ShareBean share)
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

			serializer.startTag("", ShareBean.KEY_SHARE_BEAN);

			serializer.startTag("", ShareBean.KEY_SHARE_ACCOUNT);
			serializer.text(share.getmShareAccount());
			serializer.endTag("", ShareBean.KEY_SHARE_ACCOUNT);

			serializer.startTag("", ShareBean.KEY_SHARE_PWD);
			serializer.text(share.getmSharePwd() + "");
			serializer.endTag("", ShareBean.KEY_SHARE_PWD);

			serializer.startTag("", ShareBean.KEY_SHARE_TYPE);
			serializer.text(share.getmShareType().getType() + "");
			serializer.endTag("", ShareBean.KEY_SHARE_TYPE);

			serializer.endTag("", ShareBean.KEY_SHARE_BEAN);

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
	public void WriteXML(String path, String file, List<ShareBean> shares)
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
			serializer.startTag("", ShareBean.KEY_SHARE_BEANS);

			for (ShareBean share : shares) {
				serializer.startTag("", ShareBean.KEY_SHARE_BEAN);

				serializer.startTag("", ShareBean.KEY_SHARE_ACCOUNT);
				serializer.text(share.getmShareAccount());
				serializer.endTag("", ShareBean.KEY_SHARE_ACCOUNT);

				serializer.startTag("", ShareBean.KEY_SHARE_PWD);
				serializer.text(share.getmSharePwd() + "");
				serializer.endTag("", ShareBean.KEY_SHARE_PWD);

				serializer.startTag("", ShareBean.KEY_SHARE_TYPE);
				serializer.text(share.getmShareType().getType() + "");
				serializer.endTag("", ShareBean.KEY_SHARE_TYPE);

				serializer.endTag("", ShareBean.KEY_SHARE_BEAN);
			}

			serializer.endTag("", ShareBean.KEY_SHARE_BEANS);
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

}
