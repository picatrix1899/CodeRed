package com.codered.engine.managing;

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
		fbo.applyDepthColorAttachment();
	}

	
	public void bindBuffer()
	{
		GLUtils.bindFramebuffer(fbo);
		fbo.clearAllAttachments();
	}
	
	public abstract void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend);
	
	protected void drawQuad()
	{
		UnityScreen.draw();
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
