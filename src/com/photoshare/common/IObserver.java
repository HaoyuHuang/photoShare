/**
 * 
 */
package com.photoshare.common;

/**
 * @author czj_yy
 * 
 */
public interface IObserver<SubjectType> {
	public void update(SubjectType subject);
}
