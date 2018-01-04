package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.PPFShaders;

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

		PPFShaders.CameraStripsHorizontal.setInput("frame", t.getType() == 0 ? srcFbo.getBufferTexture(t) : srcFbo.getDepthTexture());
		PPFShaders.CameraStripsHorizontal.setInput("height", srcFbo.getHeight());
		PPFShaders.CameraStripsHorizontal.setInput("stripWidth", this.stripWidth);
		PPFShaders.CameraStripsHorizontal.setInput("intensity", this.intensity);
		PPFShaders.CameraStripsHorizontal.use();
		{
			drawQuad();
		}
		PPFShaders.CameraStripsHorizontal.stop();	


		fbo.blitAttachment(dstFbo, Target.COLOR0, tRes, true);
	}

}
