package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.PPFShaders;

public class PPF_BlurV extends PPF
{

	public static final PPF_BlurV instance = new PPF_BlurV();
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
		bindBuffer();
		
		PPFShaders.BlurV.setInput("frame", t.getType() == 0 ? srcFbo.getBufferTexture(t) : srcFbo.getDepthTexture());
		PPFShaders.BlurV.setInput("targetHeight", (float)srcFbo.getHeight());
		PPFShaders.BlurV.use();	
		{
			drawQuad();
		}
		PPFShaders.BlurV.stop();	

		fbo.blitAttachment(dstFbo, Target.COLOR0, tRes, true);
	}

}
