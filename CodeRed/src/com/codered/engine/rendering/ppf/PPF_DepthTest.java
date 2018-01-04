package com.codered.engine.rendering.ppf;

import com.codered.engine.GLUtils;
import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.Window;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.PPFShaders;

public class PPF_DepthTest extends PPF
{

	public static final PPF_DepthTest instance = new PPF_DepthTest();
	
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target t2, FBO out, Target tRes, boolean blend)
	{
		bindBuffer();

		GLUtils.toggleDepthTest(true);
		
		PPFShaders.DepthTest.setInput("frameSrc", t.getType() == 0 ? srcFbo.getBufferTexture(t) : srcFbo.getDepthTexture());
		PPFShaders.DepthTest.setInput("frameDst", t2.getType() == 0 ? dstFbo.getBufferTexture(t2) : dstFbo.getDepthTexture());
		PPFShaders.DepthTest.setInput("depthSrc", srcFbo.getDepthTexture());
		PPFShaders.DepthTest.setInput("depthDst", dstFbo.getDepthTexture());
		PPFShaders.DepthTest.setInput("near", Window.active.NEAR);
		PPFShaders.DepthTest.setInput("far", Window.active.FAR);
		PPFShaders.DepthTest.use();	
		{
			drawQuad();
		}
		PPFShaders.DepthTest.stop();	

		GLUtils.toggleDepthTest(false);
		
		fbo.blitAttachment(out, Target.COLOR0, tRes, true);
	}

	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
	}

}