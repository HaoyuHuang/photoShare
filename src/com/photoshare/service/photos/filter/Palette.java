package com.photoshare.service.photos.filter;

/**
 * @author Aron
 *
 */
public class Palette {

	public int[] Blue;
	public int[] Green;
	public int Length;
	public int[] Red;

	public Palette(int length) {
		this.Length = length;
		this.Red = new int[length];
		this.Green = new int[length];
		this.Blue = new int[length];
	}
}
