package com.codered.engine.texture;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL42;

public class Texture2DArray
{
	private int id;
	
	private int width;
	private int height;
	private int depth;
	
	public Texture2DArray(int id, int width, int height, int depth)
	{
		this.id = id;
		this.width = width;
		this.height = height;
		this.depth = depth;
	}
	
	public static Texture2DArray createTexture(int width, int height, int depth, boolean hdr)
	{
		int id = GL11.glGenTextures();
		
		GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, id);

		GL42.glTexStorage3D(GL30.GL_TEXTURE_2D_ARRAY, 1, hdr ? GL30.GL_RGBA16F : GL11.GL_RGBA8, width, height, depth);
			
		return new Texture2DArray(id, width, height, depth);
	}
	
	public void bufferSubImage(int offsetX, int offsetY, int offsetZ, int depth, ByteBuffer buffer)
	{
		GL11.glBindTexture(GL30.GL_TEXTURE_2D_ARRAY, id);
		GL12.glTexSubImage3D(GL30.GL_TEXTURE_2D_ARRAY, 0, offsetX, offsetY, offsetZ, this.width, this.height, depth, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, buffer);
	}
	
	public int getId() { return this.id; }
	
	public int getWidth() { return this.width; }
	public int getHeight() { return this.height; }
	public int getDepth() { return this.depth; }
	
	public void cleanup() { GL11.glDeleteTextures(this.id); }
}
