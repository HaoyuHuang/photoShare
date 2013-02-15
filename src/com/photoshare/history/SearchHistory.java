/**
 * 
 */
package com.photoshare.history;

import java.util.ArrayList;

import android.annotation.TargetApi;
import android.util.LruCache;

/**
 * @author Aron
 * 
 *         SearchHistory use the {@link LruCache} for saving user's search
 *         requests.
 * 
 */
@TargetApi(12)
public class SearchHistory {

	private static SearchHistory searchHistory;

	public static SearchHistory getInstance() {
		if (searchHistory == null) {
			searchHistory = new SearchHistory();
		}
		return searchHistory;
	}

	private SearchHistory() {

	}

	private LruCache<String, ArrayList<?>> cache = new LruCache<String, ArrayList<?>>(
			10);
	private int[] keys = new int[10];

	public ArrayList<?> put(String key, ArrayList<?> value) {
		return cache.put(key, value);
	}

	public void evictAll() {
		for (int i = 0; i < keys.length; i++) {
			keys[i] = 0;
		}
		cache.evictAll();
	}

	public ArrayList<?> get(String key) {
		return cache.get(key);
	}

	public boolean containsKey(String key) {
		for (int i = 0; i < keys.length; i++) {
			if (keys[i] == key.hashCode()) {
				return true;
			}
		}
		return false;
	}

}
