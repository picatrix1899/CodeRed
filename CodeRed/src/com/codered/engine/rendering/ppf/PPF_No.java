package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.managing.Window;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;

public class PPF_No extends PPF
{

	public static final PPF_No instance = new PPF_No();
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
		bindBuffer();

		Window.active.getContext().ppfShaders.No.setInput("frame", srcFbo.getAttachmentId(t));
		Window.active.getContext().ppfShaders.No.use();
		{
			drawQuad();
		}
		Window.active.getContext().ppfShaders.No.stop();	

		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}

}
