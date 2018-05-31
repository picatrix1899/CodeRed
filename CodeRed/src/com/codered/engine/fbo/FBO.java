package com.codered.engine.fbo;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import com.codered.engine.utils.GLUtils;
import com.codered.engine.window.WindowContext;



public class FBO extends Framebuffer
{
	public FBO(WindowContext context)
	{
		super(context);
	}
	
	public FBO(WindowContext context, int width, int height)
	{
		super(context, width, height);
	}
	
	public void applyColorTextureAttachment(FBOTarget t, boolean hdr)
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, 0, false, hdr, false, false);
		
		GLUtils.bindFramebuffer(this.framebuffer);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, t.getTarget(), GL11.GL_TEXTURE_2D, att.getId(), 0);
		
		this.attachments[t.getIndex()] = att;
	}

	public void applyColorTextureAttachment(FBOTarget t, FBOAttachment attachment)
	{
		GLUtils.bindFramebuffer(this.framebuffer);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, t.getTarget(), GL11.GL_TEXTURE_2D, attachment.getId(), 0);
		
		this.attachments[t.getIndex()] = attachment;
	}
	
	public void applyDepthTextureAttachment()
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, 0, false, false, true, false);
		
		GLUtils.bindFramebuffer(this.framebuffer);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, att.getId(), 0);
	
		this.depth = att;
	}
	
	public void applyDepthTextureAttachment(FBOAttachment attachment)
	{
		GLUtils.bindFramebuffer(this.framebuffer);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL14.GL_DEPTH_COMPONENT24, GL11.GL_TEXTURE_2D, attachment.getId(), 0);
	
		this.depth = attachment;
	}
	
	public void applyDepthBufferAttachment()
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, 0, true, false, true, false);
		
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
	
	public void applyColorBufferAttachment(FBOTarget t)
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, 0, true, false, true, false);
		
		GLUtils.bindFramebuffer(this.framebuffer);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, att.getId());
		
		this.attachments[t.getIndex()] = att;
	}
	
	public void applyColorBufferAttachment(FBOTarget t, FBOAttachment attachment)
	{
		GLUtils.bindFramebuffer(this.framebuffer);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, attachment.getId());
		
		this.attachments[t.getIndex()] = attachment;
	}
}
