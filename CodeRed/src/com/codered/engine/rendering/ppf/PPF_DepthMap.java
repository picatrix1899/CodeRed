package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.managing.Window;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.shaders.PPFShaders;

public class PPF_DepthMap extends PPF
{

	public static final PPF_DepthMap instance = new PPF_DepthMap();
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
		bindBuffer();

		PPFShaders.DepthMap.setInput("frame", srcFbo.getAttachmentId(t));
		PPFShaders.DepthMap.setInput("near", Window.active.NEAR);
		PPFShaders.DepthMap.setInput("far", Window.active.FAR);
		PPFShaders.DepthMap.use();	
		{
			drawQuad();
		}
		PPFShaders.DepthMap.stop();	

		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}

}