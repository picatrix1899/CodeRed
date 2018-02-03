package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.shaders.PPFShaders;

public class PPF_BlurV extends PPF
{

	public static final PPF_BlurV instance = new PPF_BlurV();
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
		bindBuffer();
		
		PPFShaders.BlurV.setInput("frame", srcFbo.getAttachmentId(t));
		PPFShaders.BlurV.setInput("targetHeight", (float)srcFbo.getHeight());
		PPFShaders.BlurV.use();	
		{
			drawQuad();
		}
		PPFShaders.BlurV.stop();	

		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}

}
