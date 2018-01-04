package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.PPFShaders;

public class PPF_No extends PPF
{

	public static final PPF_No instance = new PPF_No();
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
		bindBuffer();

		PPFShaders.No.setInput("frame", t.getType() == 0 ? srcFbo.getBufferTexture(t) : srcFbo.getDepthTexture());
		PPFShaders.No.use();
		{
			drawQuad();
		}
		PPFShaders.No.stop();	

		fbo.blitAttachment(dstFbo, Target.COLOR0, tRes, true);
	}

}
