package com.codered.rendering.fbo;

import org.barghos.core.tuple.tuple2.Tup2i;
import org.barghos.core.tuple.tuple2.Tup2iR;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL30;

import com.codered.ResourceHolder;
import com.codered.engine.EngineRegistry;
import com.codered.utils.BindingUtils;
import com.codered.utils.GLCommon;
import com.codered.window.WindowContext;

public abstract class Framebuffer implements ResourceHolder
{	
	protected int id;
	protected final Tup2i size = new Tup2i();
	
	protected FBOAttachmentList attachmentList = new FBOAttachmentList();

	protected WindowContext context;
	
	public Framebuffer()
	{
		this.id = GLCommon.genFramebuffer();
		this.context = EngineRegistry.getCurrentWindowContext();
		this.size.set(context.getWindow().getWidth(), context.getWindow().getHeight());
	}
	
	public Framebuffer(Tup2iR size)
	{
		this.id = GLCommon.genFramebuffer();
		this.context = EngineRegistry.getCurrentWindowContext();
		this.size.set(size);
	}
	
	public int getId() { return this.id; }
	
	public Tup2iR getSize() { return this.size; }
	
	public void resize(Tup2iR size)
	{
		this.attachmentList.resize(size);
	}

	public void blitAttachment(Framebuffer dstFBO, FBOTarget tSrc, FBOTarget tDst, boolean depth)
	{
		BindingUtils.bindFramebuffers(this, dstFBO);
		//GL11.glReadBuffer(tSrc.getTarget());
		//GL11.glDrawBuffer(tDst.getTarget());
		GL30.glBlitFramebuffer(0, 0, this.size.getX(), this.size.getY(), 0, 0, dstFBO.getSize().getX(), dstFBO.getSize().getY(), GL11.GL_COLOR_BUFFER_BIT | (depth ? GL11.GL_DEPTH_BUFFER_BIT : 0), GL11.GL_NEAREST);
		BindingUtils.unbindFramebuffer();
	}
	
	public void blitAttachment(Framebuffer dstFBO, int tSrc, int tDst, boolean depth)
	{
		BindingUtils.bindFramebuffers(this, dstFBO);
		GL11.glReadBuffer(tSrc);
		GL11.glDrawBuffer(tDst);
		GL30.glBlitFramebuffer(0, 0, this.size.getX(), this.size.getY(), 0, 0, dstFBO.getSize().getX(), dstFBO.getSize().getY(), GL11.GL_COLOR_BUFFER_BIT | (depth ? GL11.GL_DEPTH_BUFFER_BIT : 0), GL11.GL_NEAREST);
	}
	
	public void resolveAttachmentToMain(FBOTarget t)
	{
		BindingUtils.bindReadFramebuffer(this);
		BindingUtils.bindDefaultDrawFramebuffer();
		GL11.glReadBuffer(t.getTarget());
		GL11.glDrawBuffer(GL11.GL_BACK);
		
		GL30.glBlitFramebuffer(0, 0, this.size.getX(), this.size.getY(), 0, 0, this.context.getWindow().getWidth(), this.context.getWindow().getHeight(), GL11.GL_COLOR_BUFFER_BIT, GL11.GL_NEAREST);
	}
	
	public void resolveAttachmentToMain(int t)
	{
		BindingUtils.bindReadFramebuffer(this);
		BindingUtils.bindDefaultDrawFramebuffer();
		GL11.glReadBuffer(t);
		GL11.glDrawBuffer(GL11.GL_BACK);
		
		GL30.glBlitFramebuffer(0, 0, this.size.getX(), this.size.getY(), 0, 0, this.context.getWindow().getWidth(), this.context.getWindow().getHeight(), GL11.GL_COLOR_BUFFER_BIT, GL11.GL_NEAREST);
	}
	
	public FBOAttachment getAttachment(FBOTarget t)
	{
		return this.attachmentList.get(t);
	}
	
	public int getAttachmentId(FBOTarget t)
	{
		return this.attachmentList.getId(t);
	}
	
	public void release(boolean forced)
	{
		this.attachmentList.release(forced);
		
		GLCommon.deleteFramebuffer(this.id);
	}
	
	public abstract Framebuffer initDefault();
	public abstract void addTextureAttachment(FBOTarget t);
	public abstract void addTextureAttachment(FBOTarget t, boolean hdr);
	public abstract void addTextureAttachments(FBOTarget... targets);
	public abstract void addTextureAttachments(boolean hdr, FBOTarget... targets);
	public abstract void addRenderbufferAttachment(FBOTarget t);
	public abstract void addRenderbufferAttachment(FBOTarget t, boolean hdr);
}
