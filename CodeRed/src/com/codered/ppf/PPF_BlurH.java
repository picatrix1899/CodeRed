package com.codered.ppf;

import com.codered.fbo.FBO;
import com.codered.fbo.FBOTarget;
import com.codered.shaders.postprocess.filter.BlurH_PPFilter;
import com.codered.window.WindowContext;

public class PPF_BlurH extends PPF
{
	public PPF_BlurH(WindowContext context)
	{
		super(context);
	}
	
	protected void doInternalPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes)
	{
		BlurH_PPFilter ppf = this.context.getShader(BlurH_PPFilter.class);
		
		ppf.setInput("textureMap", srcFbo.getAttachmentId(t));
		ppf.setInput("targetWidth", (float)srcFbo.getWidth());
		ppf.use();	
		{
			draw();
		}
		ppf.stop();	

		this.fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, false);
	}
}
