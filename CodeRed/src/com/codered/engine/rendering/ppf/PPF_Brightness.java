package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.shaders.PPFShaders;

public class PPF_Brightness extends PPF
{

	public static final PPF_Brightness instance = new PPF_Brightness();
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
		bindBuffer();
		
		PPFShaders.Brightness.setInput("frame", srcFbo.getAttachmentId(t));
		PPFShaders.Brightness.use();
		{
			drawQuad();
		}
		PPFShaders.Brightness.stop();	


		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}

}
