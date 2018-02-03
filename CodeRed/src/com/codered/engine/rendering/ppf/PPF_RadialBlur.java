package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.shaders.PPFShaders;

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
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes, boolean blend)
	{
		bindBuffer();

		PPFShaders.RadialBlur.setInput("frame", srcFbo.getAttachmentId(t));
		PPFShaders.RadialBlur.setInput("amplitude", this.amp);
		PPFShaders.RadialBlur.setInput("delta", this.delta);
		PPFShaders.RadialBlur.setInput("cycles", this.cycles);
		PPFShaders.RadialBlur.use();	
		{
			drawQuad();
		}
		PPFShaders.RadialBlur.stop();	

		PPFilter.SimpleBlend().doPostProcess(fbo, dstFbo, dstFbo, FBOTarget.COLOR0, tRes);
	}

}
