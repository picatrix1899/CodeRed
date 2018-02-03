package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.shaders.PPFShaders;

public class PPF_Invert extends PPF
{

	public static final PPF_Invert instance = new PPF_Invert();
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
		bindBuffer();

		PPFShaders.Invert.setInput("frame", srcFbo.getAttachmentId(t));
		PPFShaders.Invert.use();	
		{
			drawQuad();
		}
		PPFShaders.Invert.stop();	

		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}

}
