/**
 * 
 */
package com.photoshare.common;

import java.util.List;

/**
 * @author czj_yy
 * 
 */
public interface XMLParser<Type> {
	public Type loadFromXML(String path, String file) throws Exception;

	public void loadFromXML(Type obj, String path, String file)
			throws Exception;

	public List<Type> loadListFromXML(String path, String file)
			throws Exception;

	public void loadListFromXML(List<Type> list, String path, String file)
			throws Exception;

	public void WriteXML(String path, String file, Type type) throws Exception;

	public void WriteXML(String path, String file, List<Type> types)
			throws Exception;
}
