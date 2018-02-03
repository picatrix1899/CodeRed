package com.codered.engine.managing;

import com.codered.engine.GLUtils;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;

public abstract class PPF
{
	
	protected static FBO fbo;

	static
	{
		fbo = new FBO();
		fbo.applyColorTextureAttachment(FBOTarget.COLOR0, true);
		fbo.applyDepthTextureAttachment();
	}

	
	public void bindBuffer()
	{
		GLUtils.bindFramebuffer(fbo);
		fbo.clearAllAttachments();
	}
	
	public abstract void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend);
	
	protected void drawQuad()
	{
		UnityScreen.draw();
	}
	
	public int getTexture()
	{
		return fbo.getAttachmentId(FBOTarget.COLOR0);
	}
	
	public FBO getFBO()
	{
		return fbo;
	}
}
