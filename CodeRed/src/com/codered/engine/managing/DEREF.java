package com.codered.engine.managing;

import org.lwjgl.opengl.GL11;

import com.codered.engine.GLUtils;
import com.codered.engine.managing.Window;

public abstract class DEREF
{
	protected FBO fbo;

	
	public DEREF()
	{
		fbo = new FBO(Window.active.WIDTH, Window.active.HEIGHT);
		fbo.applyColorAttachment(FBO.Target.COLOR0, true);
		fbo.applyDepthBufferAttachment();
	}
	
	public void bindBuffer()
	{
		GLUtils.bindFramebuffer(this.fbo);
	}
	
	public void doPostProcess(FBO fbo, FBO.Target t)
	{
		doPostProcess(fbo, t, fbo, FBO.Target.COLOR0, false);
	}
	
	public abstract void doPostProcess(FBO srcFbo, FBO.Target t, FBO dstFbo, FBO.Target tRes, boolean blend);
	
	public void start()
	{
		UnityScreen.quad.getVAO().bind(0);
		GL11.glDisable(GL11.GL_DEPTH_TEST);	
	}
	
	protected void drawQuad()
	{
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);	
	}
	
	public void clear()
	{
		this.fbo.clearAttachment(FBO.Target.COLOR0);
	}

	
	public void stop()
	{
		GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public int getTexture()
	{
		return this.fbo.getBufferTexture(FBO.Target.COLOR0);
	}
	
	public FBO getFBO()
	{
		return this.fbo;
	}
}
