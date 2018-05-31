package com.codered.engine.fbo;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.codered.engine.utils.GL;
import com.codered.engine.utils.GLUtils;
import com.codered.engine.window.WindowContext;

public abstract class Framebuffer
{
	protected int framebuffer;
	protected int width;
	protected int height;
	
	protected FBOAttachment[] attachments = new FBOAttachment[8];
	
	protected FBOAttachment depth;
	
	protected WindowContext context;
	
	public Framebuffer(WindowContext context)
	{
		this.framebuffer = GL30.glGenFramebuffers();
		this.context = context;
		this.width = context.getWidth();
		this.height = context.getHeight();
	}
	
	public Framebuffer(WindowContext context, int width, int height)
	{
		this.framebuffer = GL30.glGenFramebuffers();
		this.context = context;
		this.width = width;
		this.height = height;
	}
	
	public int getFramebuffer() { return this.framebuffer; }
	
	public int getWidth() { return this.width; }
	
	public int getHeight() { return this.height; }
	
	public void resize(int width, int height)
	{
		for(int i = 0; i < this.attachments.length; i++)
			if(this.attachments[i] != null) this.attachments[i].resize(width, height);
		
		if(this.depth != null) this.depth.resize(width, height);
	}
	
	public void clearDraws()
	{
		GLUtils.bindFramebuffer(this.framebuffer);
		GL.glDrawBuffersFirst();
	}
	
	public void blitAttachment(Framebuffer dstFBO, FBOTarget tSrc, FBOTarget tDst, boolean depth)
	{
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, this.framebuffer);
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, dstFBO.framebuffer);
		GL11.glReadBuffer(tSrc.getTarget());
		GL11.glDrawBuffer(tDst.getTarget());
		GL30.glBlitFramebuffer(0, 0, this.width, this.height, 0, 0, dstFBO.width, dstFBO.height, GL11.GL_COLOR_BUFFER_BIT | (depth ? GL11.GL_DEPTH_BUFFER_BIT : 0), GL11.GL_NEAREST);
	}
	
	public void resolveAttachmentToScreen(FBOTarget t)
	{
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, this.framebuffer);
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
		GL11.glReadBuffer(t.getTarget());
		GL11.glDrawBuffer(GL11.GL_BACK);
		
		GL30.glBlitFramebuffer(0, 0, this.width, this.height, 0, 0, this.context.getWidth(), this.context.getHeight(), GL11.GL_COLOR_BUFFER_BIT, GL11.GL_NEAREST);
	}
	
	public void clearAllAttachments()
	{
		GLUtils.bindFramebuffer(this.framebuffer);
		GL.glDrawBuffersAll();
		GL11.glClearColor(0,0,0,1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public void clear()
	{
		GLUtils.bindFramebuffer(this.framebuffer);
		GL.glDrawBuffersAll();
		GL11.glClearColor(0,0,0,1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
	}
	
	public void clearAttachment(FBOTarget t)
	{
		GLUtils.bindFramebuffer(this.framebuffer);
		GL11.glDrawBuffer(t.getTarget());
		GL11.glClearColor(0,0,0,1);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
		
		GL.glDrawBuffersAll();
	}
	
	public FBOAttachment getAttachment(FBOTarget t)
	{
		return t.getType() == FBOTarget.DST_COLOR ? this.attachments[t.getIndex()] : this.depth;
	}
	
	public int getAttachmentId(FBOTarget t)
	{
		FBOAttachment att = t.getType() == FBOTarget.DST_COLOR ? this.attachments[t.getIndex()] : this.depth;

		return att != null ? att.getId() : 0;
	}
}
