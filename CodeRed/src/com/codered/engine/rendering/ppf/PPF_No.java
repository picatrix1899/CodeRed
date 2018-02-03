package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.shaders.PPFShaders;

public class PPF_No extends PPF
{

	public static final PPF_No instance = new PPF_No();
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
		bindBuffer();

		PPFShaders.No.setInput("frame", srcFbo.getAttachmentId(t));
		PPFShaders.No.use();
		{
			drawQuad();
		}
		PPFShaders.No.stop();	

		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}

}
