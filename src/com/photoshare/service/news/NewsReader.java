/**
 * 
 */
package com.photoshare.service.news;

import java.util.List;

import com.photoshare.common.XMLParser;

/**
 * @author czj_yy
 * 
 */
public class NewsReader implements XMLParser<NewsBean> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadFromXML(java.lang.String,
	 * java.lang.String)
	 */
	public NewsBean loadFromXML(String path, String file) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadListFromXML(java.lang.String,
	 * java.lang.String)
	 */
	public List<NewsBean> loadListFromXML(String path, String file) {
		// TODO Auto-generated method stub
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#WriteXML(java.lang.String,
	 * java.lang.String, java.lang.Object)
	 */
	public void WriteXML(String path, String file, NewsBean type) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#WriteXML(java.lang.String,
	 * java.lang.String, java.util.List)
	 */
	public void WriteXML(String path, String file, List<NewsBean> types) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadFromXML(java.lang.Object,
	 * java.lang.String, java.lang.String)
	 */
	public void loadFromXML(NewsBean obj, String path, String file) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.photoshare.common.XMLParser#loadListFromXML(java.util.List,
	 * java.lang.String, java.lang.String)
	 */
	public void loadListFromXML(List<NewsBean> list, String path, String file) {
		// TODO Auto-generated method stub

	}

}
