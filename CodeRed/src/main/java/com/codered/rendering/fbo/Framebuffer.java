package com.codered.rendering.fbo;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.codered.CodeRed;
import com.codered.engine.EngineRegistry;
import com.codered.utils.BindingUtils;
import com.codered.utils.GLCommon;
import com.codered.utils.GLUtils;
import com.codered.window.WindowContext;

public abstract class Framebuffer
{	
	protected int id;
	protected int width;
	protected int height;
	
	protected FBOAttachmentList attachmentList = new FBOAttachmentList();
	
	protected FBOAttachment[] attachments = new FBOAttachment[CodeRed.AVAILABLE_FBO_ATTACHMENTS];
	
	protected FBOAttachment depth;
	
	protected WindowContext context;
	
	public Framebuffer()
	{
		this.id = GLCommon.genFramebuffer();
		this.context = EngineRegistry.getCurrentWindowContext();
		this.width = context.getWindow().getWidth();
		this.height = context.getWindow().getHeight();
	}
	
	public Framebuffer(int width, int height)
	{
		this.id = GLCommon.genFramebuffer();
		this.context = EngineRegistry.getCurrentWindowContext();
		this.width = width;
		this.height = height;
	}
	
	public int getId() { return this.id; }
	
	public int getWidth() { return this.width; }
	
	public int getHeight() { return this.height; }
	
	public void resize(int width, int height)
	{
		for(int i = 0; i < CodeRed.AVAILABLE_FBO_ATTACHMENTS; i++)
			if(this.attachments[i] != null) this.attachments[i].resize(width, height);

		if(this.depth != null) this.depth.resize(width, height);
		
		if(CodeRed.RECREATE_FBOS_ON_RESIZE)
		{
			GLCommon.deleteFramebuffer(this.id);
			
			this.id = GLCommon.genFramebuffer();
		}
		
		this.width = width;
		this.height = height;
	}
	
	public void clearDraws()
	{
		BindingUtils.bindFramebuffer(this.id);
		GLUtils.glDrawBuffersFirst();
	}
	
	public void blitAttachment(Framebuffer dstFBO, FBOTarget tSrc, FBOTarget tDst, boolean depth)
	{
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, this.id);
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, dstFBO.id);
		GL11.glReadBuffer(tSrc.getTarget());
		GL11.glDrawBuffer(tDst.getTarget());
		GL30.glBlitFramebuffer(0, 0, this.width, this.height, 0, 0, dstFBO.width, dstFBO.height, GL11.GL_COLOR_BUFFER_BIT | (depth ? GL11.GL_DEPTH_BUFFER_BIT : 0), GL11.GL_NEAREST);
	}
	
	public void blitAttachment(Framebuffer dstFBO, int tSrc, int tDst, boolean depth)
	{
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, this.id);
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, dstFBO.id);
		GL11.glReadBuffer(FBOTarget.getByIndex(tSrc).getTarget());
		GL11.glDrawBuffer(FBOTarget.getByIndex(tDst).getTarget());
		GL30.glBlitFramebuffer(0, 0, this.width, this.height, 0, 0, dstFBO.width, dstFBO.height, GL11.GL_COLOR_BUFFER_BIT | (depth ? GL11.GL_DEPTH_BUFFER_BIT : 0), GL11.GL_NEAREST);
	}
	
	public void resolveAttachmentToScreen(FBOTarget t)
	{
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, this.id);
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
		GL11.glReadBuffer(t.getTarget());
		GL11.glDrawBuffer(GL11.GL_BACK);
		
		GL30.glBlitFramebuffer(0, 0, this.width, this.height, 0, 0, this.context.getWindow().getWidth(), this.context.getWindow().getHeight(), GL11.GL_COLOR_BUFFER_BIT, GL11.GL_NEAREST);
	}
	
	public void resolveAttachmentToScreen(int t)
	{
		GL30.glBindFramebuffer(GL30.GL_READ_FRAMEBUFFER, this.id);
		GL30.glBindFramebuffer(GL30.GL_DRAW_FRAMEBUFFER, 0);
		GL11.glReadBuffer(FBOTarget.getByIndex(t).getTarget());
		GL11.glDrawBuffer(GL11.GL_BACK);
		
		GL30.glBlitFramebuffer(0, 0, this.width, this.height, 0, 0, this.context.getWindow().getWidth(), this.context.getWindow().getHeight(), GL11.GL_COLOR_BUFFER_BIT, GL11.GL_NEAREST);
	}
	
	public void clearAllAttachments()
	{
		BindingUtils.bindFramebuffer(this.id);
		GLUtils.glDrawBuffersAll();
		GL11.glClearColor(0,0,0,1);
		GLUtils.clearColor();
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);
	}
	
	public void clear()
	{
		BindingUtils.bindFramebuffer(this.id);
		GLUtils.glDrawBuffersAll();
		GL11.glClearColor(0,0,0,1);
		GLUtils.clear(true, true);
	}
	
	public void clearAttachment(FBOTarget t)
	{
		BindingUtils.bindFramebuffer(this.id);
		GL11.glDrawBuffer(t.getTarget());
		GL11.glClearColor(0, 0, 0, 1);
		GLUtils.clearColor();
		
		GLUtils.glDrawBuffersAll();
	}
	
	public void clearAttachment(int t)
	{
		BindingUtils.bindFramebuffer(this.id);
		GL11.glDrawBuffer(FBOTarget.getByIndex(t).getTarget());
		GL11.glClearColor(0, 0, 0, 1);
		GLUtils.clearColor();
		
		GLUtils.glDrawBuffersAll();
	}
	
	public FBOAttachment getAttachment(FBOTarget t)
	{
		return t.getType() == FBOTarget.DST_COLOR ? this.attachments[t.getIndex()] : this.depth;
	}
	
	public FBOAttachment getAttachment(int t)
	{
		FBOTarget target = FBOTarget.getByIndex(t);
		
		return target.getType() == FBOTarget.DST_COLOR ? this.attachments[target.getIndex()] : this.depth;
	}
	
	public int getAttachmentId(FBOTarget t)
	{
		FBOAttachment att = t.getType() == FBOTarget.DST_COLOR ? this.attachments[t.getIndex()] : this.depth;

		return att != null ? att.getId() : 0;
	}
	
	public int getAttachmentId(int t)
	{
		FBOTarget target = FBOTarget.getByIndex(t);
		
		FBOAttachment att = target.getType() == FBOTarget.DST_COLOR ? this.attachments[target.getIndex()] : this.depth;

		return att != null ? att.getId() : 0;
	}
	
	public void release()
	{
		GLCommon.deleteFramebuffer(this.id);

		FBOAttachment att;
		for(int i = 0; i < CodeRed.AVAILABLE_FBO_ATTACHMENTS; i++)
		{
			att = this.attachments[i];
			if(att != null) att.release();
		}

	}
}
