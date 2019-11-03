package com.codered.fbo;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL30;

import com.codered.CodeRed;
import com.codered.utils.BindingUtils;

public class FBO extends Framebuffer
{
	public FBO()
	{
		super();
	}
	
	public FBO(int width, int height)
	{
		super(width, height);
	}
	
	public void resize(int width, int height)
	{
		super.resize(width, height);
		
		if(CodeRed.RECREATE_FBOS_ON_RESIZE)
		{
			BindingUtils.bindFramebuffer(this.id);
			
			for(int i = 0; i < this.attachments.length; i++)
			{
				FBOAttachment att = this.attachments[i];
				
				if(att != null)
					if(att.isBuffer())
						GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, FBOTarget.cachedValues()[i].getTarget(), GL30.GL_RENDERBUFFER, att.getId());
					else
						GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, FBOTarget.cachedValues()[i].getTarget(), GL11.GL_TEXTURE_2D, att.getId(), 0);
			}
			
			if(this.depth != null)
				if(this.depth.isBuffer())
					GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, this.depth.getId());
				else
					GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL14.GL_DEPTH_COMPONENT24, GL11.GL_TEXTURE_2D, this.depth.getId(), 0);
		}
	}
	
	public void applyColorTextureAttachment(FBOTarget t, boolean hdr)
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, 0, false, hdr, false, false, false);
		
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, t.getTarget(), GL11.GL_TEXTURE_2D, att.getId(), 0);
		
		this.attachments[t.getIndex()] = att;
	}

	public void applyColorTextureAttachments(boolean hdr, FBOTarget... targets)
	{
		BindingUtils.bindFramebuffer(this.id);
		
		FBOTarget t;
		for(int i = 0; i < targets.length; i++)
		{
			t = targets[i];
			
			FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, 0, false, hdr, false, false, false);
			
			GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, t.getTarget(), GL11.GL_TEXTURE_2D, att.getId(), 0);
			
			this.attachments[t.getIndex()] = att;
		}
	}
	
	public void applyColorTextureAttachment(int t, boolean hdr)
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, 0, false, hdr, false, false, false);
		
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, FBOTarget.getByIndex(t).getTarget(), GL11.GL_TEXTURE_2D, att.getId(), 0);
		
		this.attachments[t] = att;
	}
	
	public void applyColorTextureAttachments(boolean hdr, int... targets)
	{
		BindingUtils.bindFramebuffer(this.id);
		
		int t;
		for(int i = 0; i < targets.length; i++)
		{
			t = targets[i];
			FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, 0, false, hdr, false, false, false);
			
			GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, FBOTarget.getByIndex(t).getTarget(), GL11.GL_TEXTURE_2D, att.getId(), 0);
			
			this.attachments[t] = att;
		}
	}
	
	public void applyColorTextureAttachment(FBOTarget t, FBOAttachment attachment)
	{
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, t.getTarget(), GL11.GL_TEXTURE_2D, attachment.getId(), 0);
		
		this.attachments[t.getIndex()] = attachment;
	}
	
	public void applyColorTextureAttachment(int t, FBOAttachment attachment)
	{
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, FBOTarget.getByIndex(t).getTarget(), GL11.GL_TEXTURE_2D, attachment.getId(), 0);
		
		this.attachments[t] = attachment;
	}
	
	public void applyDepthTextureAttachment()
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, 0, false, false, true, false, false);
		
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, att.getId(), 0);
	
		this.depth = att;
	}
	
	public void applyDepthTextureAttachment(FBOAttachment attachment)
	{
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL11.GL_TEXTURE_2D, attachment.getId(), 0);
	
		this.depth = attachment;
	}
	
	public void applyDepthStencilTextureAttachment()
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, 0, false, false, true, true, false);
		
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL11.GL_TEXTURE_2D, att.getId(), 0);
	
		this.depth = att;
	}
	
	public void applyDepthStencilTextureAttachment(FBOAttachment attachment)
	{
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL11.GL_TEXTURE_2D, attachment.getId(), 0);
	
		this.depth = attachment;
	}
	
	public void applyDepthBufferAttachment()
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, 0, true, false, true, false, false);
		
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, att.getId());
		
		this.depth = att;
	}
	
	public void applyDepthBufferAttachment(FBOAttachment attachment)
	{
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, attachment.getId());
		
		this.depth = attachment;
	}
	
	public void applyDepthStencilBufferAttachment()
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, 0, true, false, true, true, false);
		BindingUtils.bindFramebuffer(this.id);
		int attId = att.getId();
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, attId);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_STENCIL_ATTACHMENT, GL30.GL_RENDERBUFFER, attId);
		
		this.depth = att;
	}
	
	public void applyDepthStencilBufferAttachment(FBOAttachment attachment)
	{
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL30.GL_RENDERBUFFER, attachment.getId());
		
		this.depth = attachment;
	}
	
	public void applyColorBufferAttachment(FBOTarget t)
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, 0, true, false, false, false, false);
		
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, t.getTarget(), GL30.GL_RENDERBUFFER, att.getId());
		
		this.attachments[t.getIndex()] = att;
	}
	
	public void applyColorBufferAttachment(int t)
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, 0, true, false, false, false, false);
		
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, FBOTarget.getByIndex(t).getTarget(), GL30.GL_RENDERBUFFER, att.getId());
		
		this.attachments[t] = att;
	}
	
	public void applyColorBufferAttachment(FBOTarget t, FBOAttachment attachment)
	{
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, t.getTarget(), GL30.GL_RENDERBUFFER, attachment.getId());
		
		this.attachments[t.getIndex()] = attachment;
	}
	
	public void applyColorBufferAttachment(int t, FBOAttachment attachment)
	{
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, FBOTarget.getByIndex(t).getTarget(), GL30.GL_RENDERBUFFER, attachment.getId());
		
		this.attachments[t] = attachment;
	}
}
