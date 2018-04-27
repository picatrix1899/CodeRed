package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.utils.GLUtils;
import com.codered.engine.window.Window;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;

public class PPF_DepthTest extends PPF
{

	public static final PPF_DepthTest instance = new PPF_DepthTest();
	
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget t2, FBO out, FBOTarget tRes, boolean blend)
	{
		bindBuffer();

		GLUtils.toggleDepthTest(true);
		
		Window.active.getContext().ppfShaders.DepthTest.setInput("frameSrc", srcFbo.getAttachmentId(t));
		Window.active.getContext().ppfShaders.DepthTest.setInput("frameDst", dstFbo.getAttachmentId(t2));
		Window.active.getContext().ppfShaders.DepthTest.setInput("depthSrc", srcFbo.getAttachmentId(FBOTarget.DEPTH));
		Window.active.getContext().ppfShaders.DepthTest.setInput("depthDst", dstFbo.getAttachmentId(FBOTarget.DEPTH));
		Window.active.getContext().ppfShaders.DepthTest.setInput("near", com.codered.engine.window.active.NEAR);
		Window.active.getContext().ppfShaders.DepthTest.setInput("far", com.codered.engine.window.active.FAR);
		Window.active.getContext().ppfShaders.DepthTest.use();	
		{
			drawQuad();
		}
		Window.active.getContext().ppfShaders.DepthTest.stop();	

		GLUtils.toggleDepthTest(false);
		
		fbo.blitAttachment(out, FBOTarget.COLOR0, tRes, true);
	}

	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
	}

}