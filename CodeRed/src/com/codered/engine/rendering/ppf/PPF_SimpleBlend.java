package com.codered.engine.rendering.ppf;

import com.codered.engine.window.Window;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.ppf.PPF;

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
	
	public void doPostProcess(FBO fbo1, FBO fbo2, FBO re, FBOTarget t1, FBOTarget t2)
	{
		doPostProcess(fbo1, fbo2, re, t1, t2, FBOTarget.COLOR0);
	}
	
	public void doPostProcess(FBO fbo1, FBO fbo2, FBO re, FBOTarget t1, FBOTarget t2, FBOTarget tRes)
	{
		bindBuffer();

		Window.active.getContext().ppfShaders.Blend.setInput("scene1", fbo1.getAttachmentId(t1));
		Window.active.getContext().ppfShaders.Blend.setInput("scene2", fbo2.getAttachmentId(t2));
		Window.active.getContext().ppfShaders.Blend.setInput("src", this.src);
		Window.active.getContext().ppfShaders.Blend.setInput("dst", this.dst);
		Window.active.getContext().ppfShaders.Blend.setInput("op", this.op);
		Window.active.getContext().ppfShaders.Blend.use();	
		{
			drawQuad();			
		}
		Window.active.getContext().ppfShaders.Blend.stop();			

		fbo.blitAttachment(re, FBOTarget.COLOR0, tRes, true);
	}
	
	

	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend) { }

}
