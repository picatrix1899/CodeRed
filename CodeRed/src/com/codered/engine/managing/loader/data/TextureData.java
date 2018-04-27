package com.codered.engine.managing.loader.data;

import java.nio.ByteBuffer;

public class TextureData
{
	private ByteBuffer data;
	private int width;
	private int height;
	
	public TextureData(ByteBuffer data, int width, int height)
	{
		this.data = data;
		this.width = width;
		this.height = height;
	}
	
	public ByteBuffer data()
	{
		return this.data;
	}
	
	public int width()
	{
		return this.width;
	}
	
	public int height()
	{
		return this.height;
	}
}
