package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.window.Window;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;

public class PPF_BlurH extends PPF
{

	public static final PPF_BlurH instance = new PPF_BlurH();
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
		bindBuffer();
		
		Window.active.getContext().ppfShaders.BlurH.setInput("frame", srcFbo.getAttachmentId(t));
		Window.active.getContext().ppfShaders.BlurH.setInput("targetWidth", (float)srcFbo.getWidth());
		Window.active.getContext().ppfShaders.BlurH.use();	
		{
			drawQuad();
		}
		Window.active.getContext().ppfShaders.BlurH.stop();	

		
		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}

}
