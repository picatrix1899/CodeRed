package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.PPFShaders;

public class PPF_SimpleBlend extends PPF
{

	private int src = Blend.ONE;
	private int dst = Blend.ONE;
	private int op = Blend.OP_ADD;
	
	public static final PPF_SimpleBlend instance = new PPF_SimpleBlend();
	
	public void setBlendFunc(int src, int dst)
	{
		this.src = src;
		this.dst = dst;
		
	}
	
	public void setBlendMode(int op)
	{
		this.op = op;
	}
	
	public void doPostProcess(FBO fbo1, FBO fbo2, FBO re, Target t1, Target t2)
	{
		doPostProcess(fbo1, fbo2, re, t1, t2, Target.COLOR0);
	}
	
	public void doPostProcess(FBO fbo1, FBO fbo2, FBO re, Target t1, Target t2, Target tRes)
	{
		bindBuffer();

		PPFShaders.Blend.setInput("scene1", t1.getType() == 0 ? fbo1.getBufferTexture(t1) : fbo1.getDepthTexture());
		PPFShaders.Blend.setInput("scene2", t2.getType() == 0 ? fbo2.getBufferTexture(t2) : fbo2.getDepthTexture());
		PPFShaders.Blend.setInput("src", this.src);
		PPFShaders.Blend.setInput("dst", this.dst);
		PPFShaders.Blend.setInput("op", this.op);
		PPFShaders.Blend.use();	
		{
			drawQuad();			
		}
		PPFShaders.Blend.stop();			

		fbo.blitAttachment(re, Target.COLOR0, tRes, true);
	}
	
	

	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend) { }

}
