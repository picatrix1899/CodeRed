package com.codered.engine.rendering.ppf;


import org.lwjgl.opengl.GL11;

import com.codered.engine.GLUtils;
import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.Window;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.postprocess.filter.PPFShader;

public class PPF_DepthTest extends PPF
{

	public static final PPF_DepthTest instance = new PPF_DepthTest();
	
	private static FBO depthTestFbo;
	
	static
	{
		depthTestFbo = new FBO(Window.active.WIDTH, Window.active.HEIGHT);
		depthTestFbo.applyColorAttachment(Target.COLOR0, true);
		depthTestFbo.applyDepthColorAttachment();
	}
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target t2, FBO out, Target tRes, boolean blend)
	{
		//bindBuffer();
		
		GLUtils.bindFramebuffer(depthTestFbo);
		
		FBO.updateDraws();
		
		start();
		
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		
		PPFShader.DepthTest().setInput("frameSrc", t.getType() == 0 ? srcFbo.getBufferTexture(t) : srcFbo.getDepthTexture());
		PPFShader.DepthTest().setInput("frameDst", t2.getType() == 0 ? dstFbo.getBufferTexture(t2) : dstFbo.getDepthTexture());
		PPFShader.DepthTest().setInput("near", Window.active.NEAR);
		PPFShader.DepthTest().setInput("far", Window.active.FAR);
		PPFShader.DepthTest().use();	
		{
			drawQuad();
		}
		PPFShader.DepthTest().stop();	
		
		stop();

		depthTestFbo.blitAttachment(out, Target.COLOR0, tRes, true);
	}

	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
	}

}