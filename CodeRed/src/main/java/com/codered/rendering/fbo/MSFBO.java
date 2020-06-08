package com.codered.rendering.fbo;

import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL32;

import com.codered.CodeRed;
import com.codered.utils.BindingUtils;

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

	public void resize(int width, int height)
	{
		super.resize(width, height);
		
		if(CodeRed.RECREATE_FBOS_ON_RESIZE)
		{
			BindingUtils.bindFramebuffer(this.id);
			
			FBOAttachment att;
			for(int i = 0; i < CodeRed.AVAILABLE_FBO_ATTACHMENTS; i++)
			{
				att = this.attachments[i];
				
				if(att != null)
					if(att.isBuffer())
						GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, FBOTarget.cachedValues()[i].getTarget(), GL30.GL_RENDERBUFFER, att.getId());
					else
						GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, FBOTarget.cachedValues()[i].getTarget(), GL32.GL_TEXTURE_2D_MULTISAMPLE, att.getId(), 0);
			}
			
			if(this.depth != null)
				if(this.depth.isBuffer())
					GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL30.GL_RENDERBUFFER, this.depth.getId());
				else
					GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL32.GL_TEXTURE_2D_MULTISAMPLE, this.depth.getId(), 0);
		}
	}
	
	public void applyColorTextureAttachment(FBOTarget t, boolean hdr)
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, this.samples, false, hdr, false, false, false);
		
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, t.getTarget(), GL32.GL_TEXTURE_2D_MULTISAMPLE, att.getId(), 0);
		
		this.attachments[t.getIndex()] = att;
	}
	
	public void applyColorTextureAttachment(int t, boolean hdr)
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, this.samples, false, hdr, false, false, false);
		
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, FBOTarget.getByIndex(t).getTarget(), GL32.GL_TEXTURE_2D_MULTISAMPLE, att.getId(), 0);
		
		this.attachments[t] = att;
	}
	
	public void applyColorTextureAttachment(FBOTarget t, FBOAttachment attachment)
	{
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, t.getTarget(), GL32.GL_TEXTURE_2D_MULTISAMPLE, attachment.getId(), 0);
		
		this.attachments[t.getIndex()] = attachment;
	}
	
	public void applyColorTextureAttachment(int t, FBOAttachment attachment)
	{
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, FBOTarget.getByIndex(t).getTarget(), GL32.GL_TEXTURE_2D_MULTISAMPLE, attachment.getId(), 0);
		
		this.attachments[t] = attachment;
	}
	
	public void applyDepthTextureAttachment()
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, this.samples, false, false, true, false, false);
		
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL32.GL_TEXTURE_2D_MULTISAMPLE, att.getId(), 0);
		
		this.depth = att;
	}
	
	public void applyDepthTextureAttachment(FBOAttachment attachment)
	{
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_ATTACHMENT, GL32.GL_TEXTURE_2D_MULTISAMPLE, attachment.getId(), 0);
		
		this.depth = attachment;
	}
	
	public void applyDepthStencilTextureAttachment()
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, this.samples, false, false, true, true, false);
		
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL32.GL_TEXTURE_2D_MULTISAMPLE, att.getId(), 0);
		
		this.depth = att;
	}
	
	public void applyDepthStencilTextureAttachment(FBOAttachment attachment)
	{
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferTexture2D(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL32.GL_TEXTURE_2D_MULTISAMPLE, attachment.getId(), 0);
		
		this.depth = attachment;
	}
	
	public void applyColorBufferAttachment(FBOTarget t, boolean hdr)
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, this.samples, true, hdr, false, false, false);
		
		BindingUtils.bindFramebuffer(this.id);
	
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, t.getTarget(), GL30.GL_RENDERBUFFER, att.getId());

		this.attachments[t.getIndex()] = att;	
	}
	
	public void applyColorBufferAttachment(int t, boolean hdr)
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, this.samples, true, hdr, false, false, false);
		
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
	
	public void applyDepthBufferAttachment()
	{
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, this.samples, true, false, true, false, false);
		
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
		FBOAttachment att = FBOAttachment.createNewWithValidation(this.width, this.height, this.samples, true, false, true, true, false);
		
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL30.GL_RENDERBUFFER, att.getId());
		
		this.depth = att;	
	}

	public void applyDepthStencilBufferAttachment(FBOAttachment attachment)
	{
		BindingUtils.bindFramebuffer(this.id);
		GL30.glFramebufferRenderbuffer(GL30.GL_FRAMEBUFFER, GL30.GL_DEPTH_STENCIL_ATTACHMENT, GL30.GL_RENDERBUFFER, attachment.getId());
		
		this.depth = attachment;	
	}
}
