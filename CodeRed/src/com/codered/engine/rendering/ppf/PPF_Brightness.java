package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.postprocess.filter.PPFShader;

public class PPF_Brightness extends PPF
{

	public static final PPF_Brightness instance = new PPF_Brightness();
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
		bindBuffer();
		
		start();
		
		PPFShader.Brightness().setInput("frame", t.getType() == 0 ? srcFbo.getBufferTexture(t) : srcFbo.getDepthTexture());
		PPFShader.Brightness().use();
		{
			drawQuad();
		}
		PPFShader.Brightness().stop();	
		
		stop();

		fbo.blitAttachment(dstFbo, Target.COLOR0, tRes, true);
	}

}
