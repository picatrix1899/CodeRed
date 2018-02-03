package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.shaders.PPFShaders;

public class PPF_HDR extends PPF
{

	public static final PPF_HDR instance = new PPF_HDR();
	
	private float exposure = 1f;
	
	public PPF_HDR setExposure(float exp)
	{
		this.exposure = exp;
		
		return this;
	}
	
	public void doPostProcess(FBO srcFbo,  FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
		bindBuffer();

		PPFShaders.HDR.setInput("frame", srcFbo.getAttachmentId(t));
		PPFShaders.HDR.setInput("exposure", this.exposure);
		PPFShaders.HDR.use();
		{
			drawQuad();
		}
		PPFShaders.HDR.stop();	

		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}


}
