package com.codered.engine.managing;

import org.lwjgl.opengl.GL11;

import com.codered.engine.GLUtils;
import com.codered.engine.managing.Window;
import com.codered.engine.managing.FBO.Target;

public abstract class PPF
{
	
	protected static FBO fbo;

	static
	{
		fbo = new FBO(Window.active.WIDTH, Window.active.HEIGHT);
		fbo.applyColorAttachment(Target.COLOR0, true);
		fbo.applyDepthBufferAttachment();
	}

	
	public void bindBuffer()
	{
		GLUtils.bindFramebuffer(fbo);
	}
	
	public abstract void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend);
	
	public void start()
	{
		UnityScreen.quad.getVAO().bind(0);
		//GL11.glDisable(GL11.GL_DEPTH_TEST);	
	}
	
	protected void drawQuad()
	{
		GL11.glDrawArrays(GL11.GL_TRIANGLE_STRIP, 0, 4);	
	}
	
	public void stop()
	{
		//GL11.glEnable(GL11.GL_DEPTH_TEST);
	}
	
	public int getTexture()
	{
		return fbo.getBufferTexture(Target.COLOR0);
	}
	
	public FBO getFBO()
	{
		return fbo;
	}
}
