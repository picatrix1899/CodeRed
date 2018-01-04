package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.postprocess.filter.PPFShader;

public class PPF_Contrast extends PPF
{

	public static final PPF_Contrast instance = new PPF_Contrast();
	
	private float contrast;
	
	public PPF_Contrast setContrast(float f)
	{
		this.contrast = f;
		return this;
	}
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
		bindBuffer();
		
		start();
		
		PPFShader.Contrast().setInput("frame", t.getType() == 0 ? srcFbo.getBufferTexture(t) : srcFbo.getDepthTexture());
		PPFShader.Contrast().setInput("contrast", contrast);
		PPFShader.Contrast().use();	
		{
		drawQuad();
		}
		PPFShader.Contrast().stop();
		
		stop();	

		fbo.blitAttachment(dstFbo, Target.COLOR0, tRes, true);
	}

}
