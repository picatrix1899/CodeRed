package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.shaders.PPFShaders;

public class PPF_BlurH extends PPF
{

	public static final PPF_BlurH instance = new PPF_BlurH();
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
		bindBuffer();
		
		PPFShaders.BlurH.setInput("frame", srcFbo.getAttachmentId(t));
		PPFShaders.BlurH.setInput("targetWidth", (float)srcFbo.getWidth());
		PPFShaders.BlurH.use();	
		{
			drawQuad();
		}
		PPFShaders.BlurH.stop();	

		
		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}

}
