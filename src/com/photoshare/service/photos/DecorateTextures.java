package com.photoshare.service.photos;

import java.util.Iterator;
import java.util.LinkedList;

public class DecorateTextures {
	private static LinkedList<Texture> textures = new LinkedList<Texture>();

	public static Texture getTexture(int index) {
		return textures.get(index);
	}

	public static Texture getTextureByName(String name) {
		Iterator<Texture> iterator = textures.iterator();
		while (iterator.hasNext()) {
			Texture texture = iterator.next();
			if (name.equals(texture.getName())) {
				return texture;
			}
		}
		return null;
	}

	public static boolean offer(Texture texture) {
		return textures.offer(texture);
	}

}
