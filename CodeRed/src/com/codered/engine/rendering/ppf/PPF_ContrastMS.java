package com.codered.engine.rendering.ppf;

import com.codered.engine.GLUtils;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.Window;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.fbo.MSFBO;

public class PPF_ContrastMS extends PPF
{

	public static final PPF_ContrastMS instance = new PPF_ContrastMS();
	
	private float contrast;
	
	private static MSFBO msfbo = new MSFBO(Window.active.WIDTH, Window.active.HEIGHT, 4);
	
	static
	{
		msfbo.applyColorTextureAttachment(FBOTarget.COLOR0, true);
		msfbo.applyDepthTextureAttachment();
	}
	
	public PPF_ContrastMS setContrast(float f)
	{
		this.contrast = f;
		return this;
	}
	
	public void doPostProcess(MSFBO srcFbo, FBOTarget t, MSFBO dstFbo, FBOTarget tRes)
	{
		GLUtils.bindFramebuffer(msfbo);
		msfbo.clearAllAttachments();
		
		Window.active.getContext().ppfShaders.ContrastMS.setInput("frame", srcFbo.getAttachmentId(t));
		Window.active.getContext().ppfShaders.ContrastMS.setInput("contrast", contrast);
		Window.active.getContext().ppfShaders.ContrastMS.use();	
		{
		drawQuad();
		}
		Window.active.getContext().ppfShaders.ContrastMS.stop();

		msfbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, false);
	}

	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
	}

}
