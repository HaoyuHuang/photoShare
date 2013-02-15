/**
 * 
 */
package com.photoshare.msg;

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
 *         MessageReader read failed messages from xml file.
 * 
 */
public class MessageReader implements XMLParser<MessageItem> {

	private static final String MSGS_TAG = "messages";

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#WriteXML(java.lang.String,
	 * java.lang.String)
	 */
	public void WriteXML(String path, String file) throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadListFromXML(java.lang.String,
	 * java.lang.String)
	 */
	public List<MessageItem> loadListFromXML(String path, String file)
			throws Exception {

		List<MessageItem> list = new ArrayList<MessageItem>();
		try {
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			XmlPullParser parser = factory.newPullParser();
			InputStream stream = FileTools.ReadFile(path, file);

			if (stream == null) {
				return null;
			}
			parser.setInput(stream, Utils.ENCODE_UTF_8);
			int eventType = parser.getEventType();
			String name = null;
			String url = null;
			String descp = null;
			String type = null;
			long id = 0;
			boolean status = false;

			while (eventType != XmlPullParser.END_DOCUMENT) {
				switch (eventType) {

				case XmlPullParser.START_DOCUMENT:
					break;
				case XmlPullParser.START_TAG:
					if (parser.getName().equals(MessageItem.MSG_DESCRIPTION)) {
						descp = parser.nextText();
					} else if (parser.getName().equals(
							MessageItem.MSG_IMAGE_URL)) {
						url = parser.nextText();
					} else if (parser.getName().equals(MessageItem.MSG_NAME)) {
						name = parser.nextText();
					} else if (parser.getName().equals(MessageItem.MSG_TYPE)) {
						type = parser.nextText();
					} else if (parser.getName()
							.equals(MessageItem.MSG_EVENT_ID)) {
						id = Long.parseLong(parser.nextText());
					} else if (parser.getName().equals(
							MessageItem.MSG_BTN_STATUS)) {
						status = Boolean.parseBoolean(parser.nextText());
					}
				case XmlPullParser.END_TAG:
					if (parser.getName().equals(MessageItem.MSG_TAG)) {
						MessageItem item = new MessageItem(name, descp, url,
								MsgType.SWITCH(type), id, status);
						list.add(item);
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
	 * @see com.photoshare.common.XMLParser#WriteXML(java.lang.String,
	 * java.lang.String, java.lang.Object)
	 */
	public void WriteXML(String path, String file, MessageItem type)
			throws Exception {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadFromXML(java.lang.String,
	 * java.lang.String)
	 */
	public MessageItem loadFromXML(String path, String file) throws Exception {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#WriteXML(java.lang.String,
	 * java.lang.String, java.util.List)
	 */
	public void WriteXML(String path, String file, List<MessageItem> list)
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
			serializer.startTag("", MSGS_TAG);
			for (MessageItem item : list) {

				serializer.startTag("", MessageItem.MSG_TAG);

				serializer.startTag("", MessageItem.MSG_NAME);
				serializer.text(item.getMsgName());
				serializer.endTag("", MessageItem.MSG_NAME);

				serializer.startTag("", MessageItem.MSG_DESCRIPTION);
				serializer.text(item.getMsgDescription());
				serializer.endTag("", MessageItem.MSG_DESCRIPTION);

				serializer.startTag("", MessageItem.MSG_IMAGE_URL);
				serializer.text(item.getMsgPhotoUrl());
				serializer.endTag("", MessageItem.MSG_IMAGE_URL);

				serializer.startTag("", MessageItem.MSG_TYPE);
				serializer.text(item.getMsgType().toString());
				serializer.endTag("", MessageItem.MSG_TYPE);

				serializer.startTag("", MessageItem.MSG_EVENT_ID);
				serializer.text(item.getEventId() + "");
				serializer.endTag("", MessageItem.MSG_EVENT_ID);

				serializer.startTag("", MessageItem.MSG_BTN_STATUS);
				serializer.text(item.isBtnStatus() + "");
				serializer.endTag("", MessageItem.MSG_BTN_STATUS);

				serializer.endTag("", MessageItem.MSG_TAG);
			}
			serializer.endTag("", MSGS_TAG);
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
	public void loadFromXML(MessageItem obj, String path, String file)
			throws Exception {
		obj = loadFromXML(path, file);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadListFromXML(java.util.List,
	 * java.lang.String, java.lang.String)
	 */
	public void loadListFromXML(List<MessageItem> list, String path, String file)
			throws Exception {
		list = loadListFromXML(path, file);
	}

}
