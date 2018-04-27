package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.window.Window;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;

public class PPF_DepthMap extends PPF
{

	public static final PPF_DepthMap instance = new PPF_DepthMap();
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
		bindBuffer();

		Window.active.getContext().ppfShaders.DepthMap.setInput("frame", srcFbo.getAttachmentId(t));
		Window.active.getContext().ppfShaders.DepthMap.setInput("near", com.codered.engine.window.active.NEAR);
		Window.active.getContext().ppfShaders.DepthMap.setInput("far", com.codered.engine.window.active.FAR);
		Window.active.getContext().ppfShaders.DepthMap.use();	
		{
			drawQuad();
		}
		Window.active.getContext().ppfShaders.DepthMap.stop();	

		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}

}