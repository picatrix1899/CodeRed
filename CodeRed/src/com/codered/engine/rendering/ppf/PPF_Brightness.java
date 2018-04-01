package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.managing.Window;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;

public class PPF_Brightness extends PPF
{

	public static final PPF_Brightness instance = new PPF_Brightness();
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
		bindBuffer();
		
		Window.active.getContext().ppfShaders.Brightness.setInput("frame", srcFbo.getAttachmentId(t));
		Window.active.getContext().ppfShaders.Brightness.use();
		{
			drawQuad();
		}
		Window.active.getContext().ppfShaders.Brightness.stop();	


		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}

}
