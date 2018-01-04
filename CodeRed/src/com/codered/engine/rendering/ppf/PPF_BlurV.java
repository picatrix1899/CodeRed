package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.postprocess.filter.PPFShader;

public class PPF_BlurV extends PPF
{

	public static final PPF_BlurV instance = new PPF_BlurV();
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
		bindBuffer();
		
		start();
		
		PPFShader.BlurV().setInput("frame", t.getType() == 0 ? srcFbo.getBufferTexture(t) : srcFbo.getDepthTexture());
		PPFShader.BlurV().setInput("targetHeight", (float)srcFbo.getHeight());
		PPFShader.BlurV().use();	
		{
			drawQuad();
		}
		PPFShader.BlurV().stop();	
		
		stop();

		fbo.blitAttachment(dstFbo, Target.COLOR0, tRes, true);
	}

}
