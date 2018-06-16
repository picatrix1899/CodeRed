package com.codered.engine.ppf;

import com.codered.engine.shaders.postprocess.filter.Contrast_PPFilter;
import com.codered.engine.window.WindowContext;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;

public class PPF_Contrast extends PPF
{
	public PPF_Contrast(WindowContext context)
	{
		super(context);
	}

	private float contrast;
	
	public PPF_Contrast setContrast(float f)
	{
		this.contrast = f;
		return this;
	}

	protected void doInternalPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes)
	{
		Contrast_PPFilter ppf = this.context.getShader(Contrast_PPFilter.class);
		
		ppf.setInput("textureMap", srcFbo.getAttachmentId(t));
		ppf.setInput("contrast", contrast);
		ppf.use();	
		{
			draw();
		}
		ppf.stop();

		this.fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, false);
	}

}
