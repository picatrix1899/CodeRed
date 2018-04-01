package com.codered.engine.rendering.ppf;

import com.codered.engine.GLUtils;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.Window;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.fbo.MSFBO;

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
		Window.active.getContext().ppfShaders.DepthTestMS.setInput("near", Window.active.NEAR);
		Window.active.getContext().ppfShaders.DepthTestMS.setInput("far", Window.active.FAR);
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