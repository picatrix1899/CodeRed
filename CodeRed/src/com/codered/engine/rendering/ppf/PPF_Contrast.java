package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.shaders.PPFShaders;

public class PPF_Contrast extends PPF
{

	public static final PPF_Contrast instance = new PPF_Contrast();
	
	private float contrast;
	
	public PPF_Contrast setContrast(float f)
	{
		this.contrast = f;
		return this;
	}
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
		bindBuffer();
		
		PPFShaders.Contrast.setInput("frame", srcFbo.getAttachmentId(t));
		PPFShaders.Contrast.setInput("contrast", contrast);
		PPFShaders.Contrast.use();	
		{
		drawQuad();
		}
		PPFShaders.Contrast.stop();

		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}

}
