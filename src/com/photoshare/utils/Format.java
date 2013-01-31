/**
 * 
 */
package com.photoshare.utils;

/**
 * @author czj_yy
 * 
 */
public class Format {
	public static boolean isNullorEmpty(String str) {
		if (str == null || "".equals(str)) {
			return true;
		}
		return false;
	}
}
