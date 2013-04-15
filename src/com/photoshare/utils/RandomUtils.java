package com.photoshare.utils;

import java.util.Random;

public class RandomUtils {

	private static Random random;

	public static void setSeed(long seed) {
		random = new Random(seed);
	}

	public static int getNextInt(int n) {
		return random.nextInt(n);
	}

	public static void main(String[] args) {
		RandomUtils.setSeed(1000);
		for (int i = 0; i < 100; i++) {
			RandomUtils.getNextInt(8);
		}
	}

}
