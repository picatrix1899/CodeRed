package com.codered.fbo;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import com.codered.CodeRed;
import com.codered.utils.GLCommon;

public abstract class FBOAttachment
{	
	protected int id;
	
	protected int samples;
	
	protected int width;
	protected int height;
	
	public FBOAttachment(int id, int width, int height, int samples)
	{
		this.id = id;
		
		this.width = width;
		this.height = height;
		this.samples = samples;
	}
	public int getId() { return this.id; }
	
	public int getSamples() { return this.samples; }
	
	public int getWidth() { return this.width; }
	public int getHeight() { return this.height; }
	
	public abstract void resize(int width, int height);
	
	public abstract boolean isBuffer();
	public abstract boolean isHDR();
	public abstract boolean isDepth();
	public abstract boolean isMultisampled();
	public abstract boolean isExternal();
	public abstract boolean isStencil();
	
	private static int create(int width, int height, int samples, boolean isBuffer, boolean isHDR, boolean isDepth, boolean isStencil)
	{
		int id = isBuffer ? GL30.glGenRenderbuffers() : GLCommon.genTextures();
		
		edit(id, width, height, samples, isBuffer, isHDR, isDepth, isStencil);
		
		return id;
	}
	
	private static void edit(int id, int width, int height, int samples, boolean isBuffer,boolean isHDR, boolean isDepth, boolean isStencil)
	{
		int internalformat = isDepth ? isStencil ? GL30.GL_DEPTH24_STENCIL8 : GL14.GL_DEPTH_COMPONENT24 : isHDR ? GL11.GL_RGBA16 : GL11.GL_RGBA8;
		
		if(isBuffer)
		{
			GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, id);
			
			if(samples > 1)
				GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, samples, internalformat, width, height);
			else
				GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, internalformat, width, height);
		}
		else
		{
			GL11.glBindTexture(samples > 1 ? GL32.GL_TEXTURE_2D_MULTISAMPLE : GL11.GL_TEXTURE_2D, id);	
			
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_LINEAR);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_S, GL12.GL_CLAMP_TO_EDGE);
			GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_WRAP_T, GL12.GL_CLAMP_TO_EDGE);
			
			if(samples > 1)
				GL32.glTexImage2DMultisample(GL32.GL_TEXTURE_2D_MULTISAMPLE, samples, internalformat, width, height, false);	
			else
				GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, internalformat, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
		}
	}
	
	public static FBOAttachment createNewWithValidation(int width, int height, int samples, boolean isBuffer, boolean isHDR, boolean isDepth, boolean isStencil, boolean isExternal)
	{
		if(isDepth && isHDR) throw new RuntimeException();
	
		int id = create(width, height, samples, isBuffer, isHDR, isDepth, isStencil);
		
		return new FBOAttachment(id, width, height, samples) {
			
			public void resize(int width, int height)
			{
				this.width = width;
				this.height = height;
				
				if(CodeRed.RECREATE_FBOS_ON_RESIZE)
				{
					if(isBuffer)
						GL30.glDeleteRenderbuffers(this.id);
					else
						GLCommon.deleteTextures(this.id);
			
					this.id = create(this.width, this.height, this.samples, isBuffer, isHDR, isDepth, isStencil);
				}
				else
				{
					edit(this.id, this.width, this.height, this.samples, isBuffer, isHDR, isDepth, isStencil);
				}

			}

			public boolean isBuffer() { return isBuffer; }
			public boolean isHDR() { return isHDR; }
			public boolean isDepth() { return isDepth; }
			public boolean isMultisampled() { return samples > 1; }
			public boolean isExternal() { return isExternal; }
			public boolean isStencil() { return isStencil; }
		};
	}
	
	public void release()
	{
		if(isBuffer())
			GL30.glDeleteRenderbuffers(this.id);
		else
			GLCommon.deleteTextures(this.id);
	}
}
