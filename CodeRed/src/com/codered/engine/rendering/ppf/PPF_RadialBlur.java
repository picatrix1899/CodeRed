package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.FBO;
import com.codered.engine.managing.PPF;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.shaders.postprocess.filter.PPFShader;

public class PPF_RadialBlur extends PPF
{

	public static final PPF_RadialBlur instance = new PPF_RadialBlur();
	
	private float amp = 0.5f;
	private float delta = 0.992f;
	private int cycles = 20;
	
	public PPF_RadialBlur setCycles(int cycles)
	{
		this.cycles = cycles;
		return this;
	}
	
	public PPF_RadialBlur setDelta(float delta)
	{
		this.delta = delta;
		return this;
	}
	
	public PPF_RadialBlur setAmplitude(float amp)
	{
		this.amp = amp;
		return this;
	}
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
		bindBuffer();
		
		start();
		
		PPFShader.RadialBlur().setInput("frame", t.getType() == 0 ? srcFbo.getBufferTexture(t) : srcFbo.getDepthTexture());
		PPFShader.RadialBlur().setInput("amplitude", this.amp);
		PPFShader.RadialBlur().setInput("delta", this.delta);
		PPFShader.RadialBlur().setInput("cycles", this.cycles);
		PPFShader.RadialBlur().use();	
		{
			drawQuad();
		}
		PPFShader.RadialBlur().stop();	
		
		stop();

		PPFilter.SimpleBlend().doPostProcess(fbo, dstFbo, dstFbo, FBO.Target.COLOR0, tRes);
	}

}
