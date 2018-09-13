package com.codered.ppf;

import com.codered.fbo.FBO;
import com.codered.fbo.FBOTarget;
import com.codered.shaders.postprocess.filter.No_PPFilter;
import com.codered.window.WindowContext;

public class PPF_No extends PPF
{
	public PPF_No(WindowContext context)
	{
		super(context);
	}

	protected void doInternalPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes)
	{
		No_PPFilter ppf = this.context.getShader(No_PPFilter.class);
		
		ppf.setInput("textureMap", srcFbo.getAttachmentId(t));
		ppf.use();
		{
			draw();
		}
		ppf.stop();	

		this.fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, false);
	}

}
