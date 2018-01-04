package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.postprocess.filter.PPFShader;

public class PPF_CameraStripsVertical extends PPF
{

	public static final PPF_CameraStripsVertical instance = new PPF_CameraStripsVertical();
	
	private float stripWidth = 0.0f;
	private float intensity = 0.0f;
	
	public PPF_CameraStripsVertical setStripWidth(float f)
	{
		this.stripWidth = f;
		
		return this;
	}
	
	public PPF_CameraStripsVertical setIntensity(float f)
	{
		this.intensity = f;
		
		return this;
	}
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
		bindBuffer();
		
		start();
		
		PPFShader.CameraStripsVertical().setInput("frame", t.getType() == 0 ? srcFbo.getBufferTexture(t) : srcFbo.getDepthTexture());
		PPFShader.CameraStripsVertical().setInput("width", srcFbo.getWidth());
		PPFShader.CameraStripsVertical().setInput("stripWidth", this.stripWidth);
		PPFShader.CameraStripsVertical().setInput("intensity", this.intensity);
		PPFShader.CameraStripsVertical().use();
		{
			drawQuad();
		}
		PPFShader.CameraStripsVertical().stop();	
		
		stop();

		fbo.blitAttachment(dstFbo, Target.COLOR0, tRes, true);
	}

}
