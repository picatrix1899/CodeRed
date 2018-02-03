package com.codered.engine.rendering.ppf;

import com.codered.engine.GLUtils;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.Window;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.shaders.PPFShaders;

public class PPF_DepthTest extends PPF
{

	public static final PPF_DepthTest instance = new PPF_DepthTest();
	
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget t2, FBO out, FBOTarget tRes, boolean blend)
	{
		bindBuffer();

		GLUtils.toggleDepthTest(true);
		
		PPFShaders.DepthTest.setInput("frameSrc", srcFbo.getAttachmentId(t));
		PPFShaders.DepthTest.setInput("frameDst", dstFbo.getAttachmentId(t2));
		PPFShaders.DepthTest.setInput("depthSrc", srcFbo.getAttachmentId(FBOTarget.DEPTH));
		PPFShaders.DepthTest.setInput("depthDst", dstFbo.getAttachmentId(FBOTarget.DEPTH));
		PPFShaders.DepthTest.setInput("near", Window.active.NEAR);
		PPFShaders.DepthTest.setInput("far", Window.active.FAR);
		PPFShaders.DepthTest.use();	
		{
			drawQuad();
		}
		PPFShaders.DepthTest.stop();	

		GLUtils.toggleDepthTest(false);
		
		fbo.blitAttachment(out, FBOTarget.COLOR0, tRes, true);
	}

	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
	}

}