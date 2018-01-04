package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.Window;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.postprocess.filter.PPFShader;

public class PPF_DepthMap extends PPF
{

	public static final PPF_DepthMap instance = new PPF_DepthMap();
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
		bindBuffer();
		
		start();
		
		PPFShader.DepthMap().setInput("frame", t.getType() == 0 ? srcFbo.getBufferTexture(t) : srcFbo.getDepthTexture());
		PPFShader.DepthMap().setInput("near", Window.active.NEAR);
		PPFShader.DepthMap().setInput("far", Window.active.FAR);
		PPFShader.DepthMap().use();	
		{
			drawQuad();
		}
		PPFShader.DepthMap().stop();	
		
		stop();
		
		fbo.blitAttachment(dstFbo, Target.COLOR0, tRes, true);
	}

}