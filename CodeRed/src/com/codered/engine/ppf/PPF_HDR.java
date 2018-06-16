package com.codered.engine.ppf;

import com.codered.engine.shaders.postprocess.filter.HDR_PPFilter;
import com.codered.engine.window.WindowContext;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;

public class PPF_HDR extends PPF
{
	public PPF_HDR(WindowContext context)
	{
		super(context);
	}

	private float exposure = 1f;
	
	public PPF_HDR setExposure(float exp)
	{
		this.exposure = exp;
		
		return this;
	}
	
	protected void doInternalPostProcess(FBO srcFbo,  FBOTarget t, FBO dstFbo, FBOTarget tRes)
	{
		HDR_PPFilter ppf = this.context.getShader(HDR_PPFilter.class);
		
		ppf.setInput("textureMap", srcFbo.getAttachmentId(t));
		ppf.setInput("exposure", this.exposure);
		ppf.use();
		{
			draw();
		}
		ppf.stop();	

		this.fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, false);
	}
	
}
