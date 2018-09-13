package com.codered.rendering.ppf;

import com.codered.fbo.FBO;
import com.codered.fbo.FBOTarget;
import com.codered.ppf.PPF;
import com.codered.window.Window;

public class PPF_DepthMap extends PPF
{

	public static final PPF_DepthMap instance = new PPF_DepthMap();
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
		bindBuffer();

		Window.active.getContext().ppfShaders.DepthMap.setInput("frame", srcFbo.getAttachmentId(t));
		Window.active.getContext().ppfShaders.DepthMap.setInput("near", com.codered.active.NEAR);
		Window.active.getContext().ppfShaders.DepthMap.setInput("far", com.codered.active.FAR);
		Window.active.getContext().ppfShaders.DepthMap.use();	
		{
			drawQuad();
		}
		Window.active.getContext().ppfShaders.DepthMap.stop();	

		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}

}