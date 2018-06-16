package com.codered.engine.ppf;

import com.codered.engine.shaders.postprocess.filter.No_PPFilter;
import com.codered.engine.window.WindowContext;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;

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
