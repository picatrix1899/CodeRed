package com.codered.ppf;

import com.codered.fbo.FBO;
import com.codered.fbo.FBOTarget;
import com.codered.shaders.postprocess.filter.BlurV_PPFilter;
import com.codered.window.WindowContext;

public class PPF_BlurV extends PPF
{

	public PPF_BlurV(WindowContext context)
	{
		super(context);
	}
	
	protected void doInternalPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes)
	{
		BlurV_PPFilter ppf = this.context.getShader(BlurV_PPFilter.class);
		
		ppf.setInput("textureMap", srcFbo.getAttachmentId(t));
		ppf.setInput("targetHeight", (float)srcFbo.getHeight());
		ppf.use();	
		{
			draw();
		}
		ppf.stop();	

		this.fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, false);
	}

}
