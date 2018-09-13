package com.codered.rendering.ppf;

import com.codered.fbo.FBO;
import com.codered.fbo.FBOTarget;
import com.codered.fbo.MSFBO;
import com.codered.ppf.PPF;
import com.codered.utils.GLUtils;
import com.codered.window.Window;

public class PPF_DepthTestMS extends PPF
{

	public static final PPF_DepthTestMS instance = new PPF_DepthTestMS();
	
	protected static MSFBO msfbo;

	static
	{
		msfbo = new MSFBO(4);
		msfbo.applyColorTextureAttachment(FBOTarget.COLOR0, true);
		msfbo.applyDepthTextureAttachment();
	}
	
	public void doPostProcess(MSFBO srcFbo, FBOTarget t, MSFBO dstFbo, FBOTarget t2, MSFBO out, FBOTarget tRes, boolean blend)
	{
		GLUtils.bindFramebuffer(msfbo);
		msfbo.clear();

		Window.active.getContext().ppfShaders.DepthTestMS.setInput("frameSrc", srcFbo.getAttachmentId(t));
		Window.active.getContext().ppfShaders.DepthTestMS.setInput("frameDst", dstFbo.getAttachmentId(t2));
		Window.active.getContext().ppfShaders.DepthTestMS.setInput("depthSrc", srcFbo.getAttachmentId(FBOTarget.DEPTH));
		Window.active.getContext().ppfShaders.DepthTestMS.setInput("depthDst", dstFbo.getAttachmentId(FBOTarget.DEPTH));
		Window.active.getContext().ppfShaders.DepthTestMS.setInput("near", com.codered.active.NEAR);
		Window.active.getContext().ppfShaders.DepthTestMS.setInput("far", com.codered.active.FAR);
		Window.active.getContext().ppfShaders.DepthTestMS.use();	
		{
			drawQuad();
		}
		Window.active.getContext().ppfShaders.DepthTestMS.stop();	
		
		msfbo.blitAttachment(out, FBOTarget.COLOR0, tRes, true);
	}

	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
	}

}