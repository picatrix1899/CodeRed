package com.codered.engine.fbo;

import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import com.codered.engine.utils.GLUtils;


public class MSFBO extends Framebuffer
{
	private int samples;
	
	public MSFBO(int samples)
	{
		super();
		
		this.samples = samples;
	}
	
	public MSFBO(int width, int height, int samples)
	{
		super(width, height);
		
		this.samples = samples;
	}

	public void applyColorTextureAttachment(FBOTarget t, boolean hdr)
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, this.samples, false, hdr, false, false);
		
		GLUtils.bindFramebuffer(this.framebuffer);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, t.getTarget(), GL32.GL_TEXTURE_2D_MULTISAMPLE, att.getId(), 0);
		
		this.attachments[t.getIndex()] = att;
	}
	
	public void applyColorTextureAttachment(FBOTarget t, FBOAttachment attachment)
	{
		GLUtils.bindFramebuffer(this.framebuffer);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, t.getTarget(), GL32.GL_TEXTURE_2D_MULTISAMPLE, attachment.getId(), 0);
		
		this.attachments[t.getIndex()] = attachment;
	}
	
	public void applyDepthTextureAttachment()
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, this.samples, false, false, true, false);
		
		GLUtils.bindFramebuffer(this.framebuffer);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL32.GL_TEXTURE_2D_MULTISAMPLE, att.getId(), 0);
		
		this.depth = att;
	}
	
	public void applyDepthTextureAttachment(FBOAttachment attachment)
	{
		GLUtils.bindFramebuffer(this.framebuffer);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL32.GL_TEXTURE_2D_MULTISAMPLE, attachment.getId(), 0);
		
		this.depth = attachment;
	}
	
	public void applyColorBufferAttachment(FBOTarget t, boolean hdr)
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, this.samples, true, hdr, false, false);
		
		GLUtils.bindFramebuffer(this.framebuffer);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, t.getTarget(), GL30.GL_RENDERBUFFER, att.getId());
			
		this.attachments[t.getIndex()] = att;	
	}
	
	public void applyColorBufferAttachment(FBOTarget t, FBOAttachment attachment)
	{
		GLUtils.bindFramebuffer(this.framebuffer);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, t.getTarget(), GL30.GL_RENDERBUFFER, attachment.getId());
			
		this.attachments[t.getIndex()] = attachment;
	}
	
	public void applyDepthBufferAttachment()
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, this.samples, true, false, true, false);
		
		GLUtils.bindFramebuffer(this.framebuffer);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, att.getId());
		
		this.depth = att;	
	}

	public void applyDepthBufferAttachment(FBOAttachment attachment)
	{
		GLUtils.bindFramebuffer(this.framebuffer);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, attachment.getId());
		
		this.depth = attachment;	
	}
}
