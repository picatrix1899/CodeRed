package com.codered.engine.ppf;

import com.codered.engine.window.WindowContext;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.shaders.postprocess.filter.RadialBlur_PPFilter;

public class PPF_RadialBlur extends PPF
{

	public PPF_RadialBlur(WindowContext context)
	{
		super(context);
	}

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
	
	public void doInternalPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes)
	{
		RadialBlur_PPFilter ppf = this.context.getShader(RadialBlur_PPFilter.class);
		
		ppf.setInput("textureMap", srcFbo.getAttachmentId(t));
		ppf.setInput("amplitude", this.amp);
		ppf.setInput("delta", this.delta);
		ppf.setInput("cycles", this.cycles);
		ppf.use();	
		{
			draw();
		}
		ppf.stop();	

		this.fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, false);
	}

}
