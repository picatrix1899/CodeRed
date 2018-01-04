package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.Window;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.PPFShaders;

public class PPF_DepthMap extends PPF
{

	public static final PPF_DepthMap instance = new PPF_DepthMap();
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
		bindBuffer();

		PPFShaders.DepthMap.setInput("frame", t.getType() == 0 ? srcFbo.getBufferTexture(t) : srcFbo.getDepthTexture());
		PPFShaders.DepthMap.setInput("near", Window.active.NEAR);
		PPFShaders.DepthMap.setInput("far", Window.active.FAR);
		PPFShaders.DepthMap.use();	
		{
			drawQuad();
		}
		PPFShaders.DepthMap.stop();	

		fbo.blitAttachment(dstFbo, Target.COLOR0, tRes, true);
	}

}