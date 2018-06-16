package com.codered.engine.rendering.ppf;

import com.codered.engine.window.Window;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.ppf.PPF;

public class PPF_Invert extends PPF
{

	public static final PPF_Invert instance = new PPF_Invert();
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
		bindBuffer();

		Window.active.getContext().ppfShaders.Invert.setInput("frame", srcFbo.getAttachmentId(t));
		Window.active.getContext().ppfShaders.Invert.use();	
		{
			drawQuad();
		}
		Window.active.getContext().ppfShaders.Invert.stop();	

		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}

}
