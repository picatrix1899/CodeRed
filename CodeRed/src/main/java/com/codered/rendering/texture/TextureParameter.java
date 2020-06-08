package com.codered.rendering.texture;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL44;

public class TextureParameter
{
	private MinFunc textureMinFilter = null;
	private MagFunc textureMagFilter = null;
	private WrapMode textureWrapS = null;
	private WrapMode textureWrapT = null;
	private WrapMode textureWrapR = null;
	
	public TextureParameter setTextureMinFilter(MinFunc func)
	{
		this.textureMinFilter = func;
		return this;
	}
	
	public TextureParameter setTextureMagFilter(MagFunc func)
	{
		this.textureMagFilter = func;
		return this;
	}
	
	public TextureParameter setTextureWrapS(WrapMode mode)
	{
		this.textureWrapS = mode;
		return this;
	}
	
	public TextureParameter setTextureWrapT(WrapMode mode)
	{
		this.textureWrapT = mode;
		return this;
	}
	
	public TextureParameter setTextureWrapR(WrapMode mode)
	{
		this.textureWrapR = mode;
		return this;
	}
	
	public void apply(int target)
	{
		if(this.textureMinFilter != null) GL11.glTexParameteri(target, GL11.GL_TEXTURE_MIN_FILTER, this.textureMinFilter.getValue());
		if(this.textureMagFilter != null) GL11.glTexParameteri(target, GL11.GL_TEXTURE_MAG_FILTER, this.textureMagFilter.getValue());
		if(this.textureWrapS != null) GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_S, this.textureWrapS.getValue());
		if(this.textureWrapT != null) GL11.glTexParameteri(target, GL11.GL_TEXTURE_WRAP_T, this.textureWrapT.getValue());
		if(this.textureWrapR != null) GL11.glTexParameteri(target, GL12.GL_TEXTURE_WRAP_R, this.textureWrapR.getValue());
	}
	
	public static enum MinFunc
	{
		NEAREST_MIPMAP_LINEAR(GL11.GL_NEAREST_MIPMAP_LINEAR),
		LINEAR(GL11.GL_LINEAR),
		NEAREST(GL11.GL_NEAREST),
		NEAREST_MIPMAP_NEAREST(GL11.GL_NEAREST_MIPMAP_NEAREST),
		LINEAR_MIPMAP_NEAREST(GL11.GL_LINEAR_MIPMAP_NEAREST),
		LINEAR_MIPMAP_LINEAR(GL11.GL_LINEAR_MIPMAP_LINEAR);
		
		private int value;
		
		private MinFunc(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return this.value;
		}
	}
	
	public static enum MagFunc
	{
		LINEAR(GL11.GL_LINEAR),
		NEAREST(GL11.GL_NEAREST);
		
		private int value;
		
		private MagFunc(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return this.value;
		}
	}
	
	public static enum WrapMode
	{
		REPEAT(GL11.GL_REPEAT),
		CLAMP_TO_EDGE(GL12.GL_CLAMP_TO_EDGE),
		CLAMP_TO_BORDER(GL13.GL_CLAMP_TO_BORDER),
		MIRRORED_REPEAT(GL14.GL_MIRRORED_REPEAT),
		MIRROR_CLAMP_TO_EDGE(GL44.GL_MIRROR_CLAMP_TO_EDGE);
		
		private int value;
		
		private WrapMode(int value)
		{
			this.value = value;
		}
		
		public int getValue()
		{
			return this.value;
		}
	}
}
