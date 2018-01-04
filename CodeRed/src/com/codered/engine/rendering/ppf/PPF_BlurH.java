package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.postprocess.filter.PPFShader;

public class PPF_BlurH extends PPF
{

	public static final PPF_BlurH instance = new PPF_BlurH();
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
		bindBuffer();
		
		start();
		
		PPFShader.BlurH().setInput("frame", t.getType() == 0 ? srcFbo.getBufferTexture(t) : srcFbo.getDepthTexture());
		PPFShader.BlurH().setInput("targetWidth", (float)srcFbo.getWidth());
		PPFShader.BlurH().use();	
		{
			drawQuad();
		}
		PPFShader.BlurH().stop();	
		
		stop();
		
		fbo.blitAttachment(dstFbo, Target.COLOR0, tRes, true);
	}

}
