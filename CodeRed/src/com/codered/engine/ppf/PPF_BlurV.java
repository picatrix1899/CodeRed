package com.codered.engine.ppf;

import com.codered.engine.shaders.postprocess.filter.BlurV_PPFilter;
import com.codered.engine.window.WindowContext;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;

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
