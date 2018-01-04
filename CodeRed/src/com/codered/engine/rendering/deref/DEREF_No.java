package com.codered.engine.rendering.deref;

import com.codered.engine.managing.DEREF;
import com.codered.engine.managing.FBO;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.rendering.ppf.PPFilter;
import com.codered.engine.shaders.DOShaders;


public class DEREF_No extends DEREF
{
	public static final DEREF_No instance = new DEREF_No();
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
		bindBuffer();
		
		start();

		DOShaders.No.setInput("albedo", srcFbo.getBufferTexture(t));
		
		DOShaders.No.use();
		{
			drawQuad();
		}
		DOShaders.No.stop();	
		
		stop();

		if(blend)
		{
			PPFilter.SimpleBlend().doPostProcess(this.fbo, dstFbo, dstFbo, FBO.Target.COLOR0, tRes);
		}
		else
		{
			this.fbo.blitAttachment(dstFbo, Target.COLOR0, tRes, true);
		}
	}
}
