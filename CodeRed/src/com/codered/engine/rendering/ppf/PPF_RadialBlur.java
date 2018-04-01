package com.codered.engine.rendering.ppf;

import com.codered.engine.managing.PPF;
import com.codered.engine.managing.Window;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;

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

		Window.active.getContext().ppfShaders.RadialBlur.setInput("frame", srcFbo.getAttachmentId(t));
		Window.active.getContext().ppfShaders.RadialBlur.setInput("amplitude", this.amp);
		Window.active.getContext().ppfShaders.RadialBlur.setInput("delta", this.delta);
		Window.active.getContext().ppfShaders.RadialBlur.setInput("cycles", this.cycles);
		Window.active.getContext().ppfShaders.RadialBlur.use();	
		{
			drawQuad();
		}
		Window.active.getContext().ppfShaders.RadialBlur.stop();	

		PPFilter.SimpleBlend().doPostProcess(fbo, dstFbo, dstFbo, FBOTarget.COLOR0, tRes);
	}

}
