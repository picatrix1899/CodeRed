package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.postprocess.filter.PPFShader;

public class PPF_CameraStripsHorizontal extends PPF
{

	public static final PPF_CameraStripsHorizontal instance = new PPF_CameraStripsHorizontal();
	
	private float stripWidth = 0.0f;
	private float intensity = 0.0f;
	
	public PPF_CameraStripsHorizontal setStripWidth(float f)
	{
		this.stripWidth = f;
		
		return this;
	}
	
	public PPF_CameraStripsHorizontal setIntensity(float f)
	{
		this.intensity = f;
		
		return this;
	}
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
		bindBuffer();
		
		start();
		
		PPFShader.CameraStripsHorizontal().setInput("frame", t.getType() == 0 ? srcFbo.getBufferTexture(t) : srcFbo.getDepthTexture());
		PPFShader.CameraStripsHorizontal().setInput("height", srcFbo.getHeight());
		PPFShader.CameraStripsHorizontal().setInput("stripWidth", this.stripWidth);
		PPFShader.CameraStripsHorizontal().setInput("intensity", this.intensity);
		PPFShader.CameraStripsHorizontal().use();
		{
			drawQuad();
		}
		PPFShader.CameraStripsHorizontal().stop();	
		
		stop();

		fbo.blitAttachment(dstFbo, Target.COLOR0, tRes, true);
	}

}
