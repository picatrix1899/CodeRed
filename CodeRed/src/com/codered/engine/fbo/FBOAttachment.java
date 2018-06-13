package com.codered.engine.fbo;

import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

public abstract class FBOAttachment
{
	protected int id;
	
	protected int samples;
	
	protected int width;
	protected int height;
	
	public FBOAttachment(int id, int width, int height)
	{
		this.id = id;
		
		this.width = width;
		this.height = height;
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
	
	public static FBOAttachment createNewWithValidation(int width, int height, int samples, boolean isBuffer, boolean isHDR,
			boolean isDepth, boolean isExternal)
	{
		if(isDepth && isHDR) throw new RuntimeException();
	
		if(isBuffer)
		{
			int buffer = GL30.glGenRenderbuffers();
			GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, buffer);

			if(samples > 1)
			{
				if(isDepth)
				{
					GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, samples, GL14.GL_DEPTH_COMPONENT24, width, height);
					
					return new FBOAttachment(buffer, width, height)
					{
						public void resize(int width, int height)
						{
							this.width = width;
							this.height = height;
							
							GL30.glDeleteRenderbuffers(this.id);
							
							this.id = GL30.glGenRenderbuffers();
							
							GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, this.id);
							GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, samples, GL14.GL_DEPTH_COMPONENT24, width, height);
						}

						public boolean isBuffer() { return isBuffer; }
						public boolean isHDR() { return isHDR; }
						public boolean isDepth() { return isDepth; }
						public boolean isMultisampled() { return samples > 1; }
						public boolean isExternal() { return isExternal; }
					};
				}
				else
				{
					GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, samples, isHDR ? GL11.GL_RGBA16 : GL11.GL_RGBA8, width, height);
					
					return new FBOAttachment(buffer, width, height)
					{
						public void resize(int width, int height)
						{
							this.width = width;
							this.height = height;
							
							GL30.glDeleteRenderbuffers(this.id);
							
							this.id = GL30.glGenRenderbuffers();
							
							GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, this.id);
							GL30.glRenderbufferStorageMultisample(GL30.GL_RENDERBUFFER, samples, isHDR() ? GL11.GL_RGBA16 : GL11.GL_RGBA8, width, height);	
						}

						public boolean isBuffer() { return isBuffer; }
						public boolean isHDR() { return isHDR; }
						public boolean isDepth() { return isDepth; }
						public boolean isMultisampled() { return samples > 1; }
						public boolean isExternal() { return isExternal; }
					};
				}
			}
			else
			{
				if(isDepth)
				{
					GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL14.GL_DEPTH_COMPONENT24, width, height);
					
					return new FBOAttachment(buffer, width, height)
					{
						public void resize(int width, int height)
						{
							this.width = width;
							this.height = height;
							
							GL30.glDeleteRenderbuffers(this.id);
							
							this.id = GL30.glGenRenderbuffers();
							
							GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, this.id);
							GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, GL14.GL_DEPTH_COMPONENT24, width, height);
						}

						public boolean isBuffer() { return isBuffer; }
						public boolean isHDR() { return isHDR; }
						public boolean isDepth() { return isDepth; }
						public boolean isMultisampled() { return samples > 1; }
						public boolean isExternal() { return isExternal; }
					};
				}
				else
				{
					GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, isHDR ? GL11.GL_RGBA16 : GL11.GL_RGBA8, width, height);
					
					return new FBOAttachment(buffer, width, height)
					{
						public void resize(int width, int height)
						{
							this.width = width;
							this.height = height;
							
							GL30.glDeleteRenderbuffers(this.id);
							
							this.id = GL30.glGenRenderbuffers();
							
							GL30.glBindRenderbuffer(GL30.GL_RENDERBUFFER, this.id);
							GL30.glRenderbufferStorage(GL30.GL_RENDERBUFFER, isHDR() ? GL11.GL_RGBA16 : GL11.GL_RGBA8, width, height);
						}

						public boolean isBuffer() { return isBuffer; }
						public boolean isHDR() { return isHDR; }
						public boolean isDepth() { return isDepth; }
						public boolean isMultisampled() { return samples > 1; }
						public boolean isExternal() { return isExternal; }
					};
				}
			}
		}
		else
		{
			int texture = GL11.glGenTextures();
			
			if(samples > 1)
			{
				GL11.glBindTexture(GL32.GL_TEXTURE_2D_MULTISAMPLE, texture);
				
				if(isDepth)
				{
					GL32.glTexImage2DMultisample(GL32.GL_TEXTURE_2D_MULTISAMPLE, samples, GL14.GL_DEPTH_COMPONENT24, width, height, false);	
					
					return new FBOAttachment(texture, width, height)
					{
						public void resize(int width, int height)
						{
							this.width = width;
							this.height = height;
							
							GL11.glBindTexture(GL32.GL_TEXTURE_2D_MULTISAMPLE, this.id);
							GL32.glTexImage2DMultisample(GL32.GL_TEXTURE_2D_MULTISAMPLE, this.samples, GL14.GL_DEPTH_COMPONENT24, width, height, false);
						}

						public boolean isBuffer() { return isBuffer; }
						public boolean isHDR() { return isHDR; }
						public boolean isDepth() { return isDepth; }
						public boolean isMultisampled() { return samples > 1; }
						public boolean isExternal() { return isExternal; }
					};
				}
				else
				{
					GL32.glTexImage2DMultisample(GL32.GL_TEXTURE_2D_MULTISAMPLE, samples, isHDR ? GL11.GL_RGBA16 : GL11.GL_RGBA8, width, height, false);	
					
					return new FBOAttachment(texture, width, height)
					{
						public void resize(int width, int height)
						{
							this.width = width;
							this.height = height;
							
							GL11.glBindTexture(GL32.GL_TEXTURE_2D_MULTISAMPLE, this.id);
							GL32.glTexImage2DMultisample(GL32.GL_TEXTURE_2D_MULTISAMPLE, this.samples, isHDR() ? GL11.GL_RGBA16 : GL11.GL_RGBA8, width, height, false);
						}

						public boolean isBuffer() { return isBuffer; }
						public boolean isHDR() { return isHDR; }
						public boolean isDepth() { return isDepth; }
						public boolean isMultisampled() { return samples > 1; }
						public boolean isExternal() { return isExternal; }
					};
				}
			}
			else
			{
				GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
				
				if(isDepth)
				{
					GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT24, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
					
					return new FBOAttachment(texture, width, height)
					{
						public void resize(int width, int height)
						{
							this.width = width;
							this.height = height;
							
							GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
							GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL14.GL_DEPTH_COMPONENT24, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
						}

						public boolean isBuffer() { return isBuffer; }
						public boolean isHDR() { return isHDR; }
						public boolean isDepth() { return isDepth; }
						public boolean isMultisampled() { return samples > 1; }
						public boolean isExternal() { return isExternal; }
					};
				}
				else
				{
					GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, isHDR ? GL11.GL_RGBA16 : GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
					
					return new FBOAttachment(texture, width, height)
					{
						public void resize(int width, int height)
						{
							this.width = width;
							this.height = height;
							
							GL11.glBindTexture(GL11.GL_TEXTURE_2D, this.id);
							GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, isHDR() ? GL11.GL_RGBA16 : GL11.GL_RGBA8, width, height, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, (ByteBuffer) null);
						}

						public boolean isBuffer() { return isBuffer; }
						public boolean isHDR() { return isHDR; }
						public boolean isDepth() { return isDepth; }
						public boolean isMultisampled() { return samples > 1; }
						public boolean isExternal() { return isExternal; }
					};
				}
			}
		}
	}
	
	public void cleanup()
	{
		if(isBuffer())
			GL30.glDeleteRenderbuffers(this.id);
		else
			GL11.glDeleteTextures(this.id);
	}
}
