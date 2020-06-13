package com.codered.rendering.fbo;

import org.barghos.core.tuple.tuple2.Tup2iR;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

public class MSFBO extends Framebuffer
{
	private int samples;
	
	public MSFBO(int samples)
	{
		super();
		this.samples = samples;
	}
	
	public MSFBO(int samples, Tup2iR size)
	{
		super(size);
		this.samples = samples;
	}
	
	public MSFBO initDefault()
	{
		addRenderbufferAttachment(FBOTarget.DEPTH);
		addTextureAttachment(FBOTarget.COLOR0);
		return this;
	}
	
	public void addTextureAttachment(FBOTarget t)
	{
		addTextureAttachment(t, false);
	}
	
	public void addTextureAttachment(FBOTarget t, boolean hdr)
	{
		int internalformat = 0;
		if(t != FBOTarget.DEPTH && t != FBOTarget.DEPTH_STENCIL)
			internalformat = hdr ? GL11.GL_RGBA16 : GL11.GL_RGBA8;
		else
			internalformat = t == FBOTarget.DEPTH_STENCIL ? GL30.GL_DEPTH24_STENCIL8 : GL30.GL_DEPTH_COMPONENT32;
		
		FBOAttachment a = new FBOAttachmentMSTexture(this.samples, internalformat, this.size);
		
		a.bindToFramebuffer(this, t);
		
		this.attachmentList.set(t, a);
	}

	public void addTextureAttachments(FBOTarget... targets)
	{
		addTextureAttachments(false, targets);
	}
	
	public void addTextureAttachments(boolean hdr, FBOTarget... targets)
	{
		for(int i = 0; i < targets.length; i++)
		{
			FBOTarget t = targets[i];
			int internalformat = 0;
			if(t != FBOTarget.DEPTH && t != FBOTarget.DEPTH_STENCIL)
				internalformat = hdr ? GL11.GL_RGBA16 : GL11.GL_RGBA8;
			else
				internalformat = t == FBOTarget.DEPTH_STENCIL ? GL30.GL_DEPTH24_STENCIL8 : GL30.GL_DEPTH_COMPONENT32;
			
			FBOAttachment a = new FBOAttachmentMSTexture(this.samples, internalformat, this.size);
			
			a.bindToFramebuffer(this, t);
			
			this.attachmentList.set(t, a);
		}
	}

	public void addRenderbufferAttachment(FBOTarget t)
	{
		addRenderbufferAttachment(t, false);
	}
	
	public void addRenderbufferAttachment(FBOTarget t, boolean hdr)
	{
		int internalformat = 0;
		if(t != FBOTarget.DEPTH && t != FBOTarget.DEPTH_STENCIL)
			internalformat = hdr ? GL11.GL_RGBA16 : GL11.GL_RGBA8;
		else
			internalformat = t == FBOTarget.DEPTH_STENCIL ? GL30.GL_DEPTH24_STENCIL8 : GL30.GL_DEPTH_COMPONENT32;
		
		FBOAttachment a = new FBOAttachmentMSBuffer(this.samples, internalformat, this.size);
		
		a.bindToFramebuffer(this, t);
		
		this.attachmentList.set(t, a);
	}
}
