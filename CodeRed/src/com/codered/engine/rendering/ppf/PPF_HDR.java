package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.postprocess.filter.PPFShader;

public class PPF_HDR extends PPF
{

	public static final PPF_HDR instance = new PPF_HDR();
	
	private float exposure = 1f;
	
	public PPF_HDR setExposure(float exp)
	{
		this.exposure = exp;
		
		return this;
	}
	
	public void doPostProcess(FBO srcFbo,  Target t, FBO dstFbo, Target tRes, boolean blend)
	{
		bindBuffer();
		
		start();
		
		PPFShader.HDR().setInput("frame", t.getType() == 0 ? srcFbo.getBufferTexture(t) : srcFbo.getDepthTexture());
		PPFShader.HDR().setInput("exposure", this.exposure);
		PPFShader.HDR().use();
		{
			drawQuad();
		}
		PPFShader.HDR().stop();	
		
		stop();
	
		fbo.blitAttachment(dstFbo, Target.COLOR0, tRes, true);
	}


}
