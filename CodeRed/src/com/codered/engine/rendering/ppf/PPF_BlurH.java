package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.PPFShaders;

public class PPF_BlurH extends PPF
{

	public static final PPF_BlurH instance = new PPF_BlurH();
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
		bindBuffer();
		
		PPFShaders.BlurH.setInput("frame", t.getType() == 0 ? srcFbo.getBufferTexture(t) : srcFbo.getDepthTexture());
		PPFShaders.BlurH.setInput("targetWidth", (float)srcFbo.getWidth());
		PPFShaders.BlurH.use();	
		{
			drawQuad();
		}
		PPFShaders.BlurH.stop();	

		
		fbo.blitAttachment(dstFbo, Target.COLOR0, tRes, true);
	}

}
