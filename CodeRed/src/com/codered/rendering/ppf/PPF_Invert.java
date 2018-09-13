package com.codered.rendering.ppf;

import com.codered.fbo.FBO;
import com.codered.fbo.FBOTarget;
import com.codered.ppf.PPF;
import com.codered.window.Window;

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
