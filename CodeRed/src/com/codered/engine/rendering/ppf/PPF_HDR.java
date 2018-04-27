package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.window.Window;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;

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

		Window.active.getContext().ppfShaders.HDR.setInput("frame", srcFbo.getAttachmentId(t));
		Window.active.getContext().ppfShaders.HDR.setInput("exposure", this.exposure);
		Window.active.getContext().ppfShaders.HDR.use();
		{
			drawQuad();
		}
		Window.active.getContext().ppfShaders.HDR.stop();	

		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}


}
