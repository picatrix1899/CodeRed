package com.codered.engine.managing.loader.data;

import java.util.ArrayList;
import java.util.List;

public class BMFontData
{
	public String title;
	public int size;
	public int lineHeight;
	public int base;
	public int width;
	public int height;
	
	public TextureData file;
	
	public List<CharData> data = new ArrayList<CharData>();
	
	public static class CharData
	{
		public char c;
		public int x;
		public int y;
		public int width;
		public int height;
		public int xOff;
		public int yOff;
		public int xAdvance;
	}
}