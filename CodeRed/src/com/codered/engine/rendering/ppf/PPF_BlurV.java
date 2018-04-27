package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.window.Window;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;

public class PPF_BlurV extends PPF
{

	public static final PPF_BlurV instance = new PPF_BlurV();
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
		bindBuffer();
		
		Window.active.getContext().ppfShaders.BlurV.setInput("frame", srcFbo.getAttachmentId(t));
		Window.active.getContext().ppfShaders.BlurV.setInput("targetHeight", (float)srcFbo.getHeight());
		Window.active.getContext().ppfShaders.BlurV.use();	
		{
			drawQuad();
		}
		Window.active.getContext().ppfShaders.BlurV.stop();	

		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}

}
