package com.codered.ppf;

import com.codered.fbo.FBO;
import com.codered.fbo.FBOTarget;
import com.codered.shaders.postprocess.filter.Invert_PPFilter;
import com.codered.window.WindowContext;

public class PPF_Invert extends PPF
{

	public PPF_Invert(WindowContext context)
	{
		super(context);
	}
	

	protected void doInternalPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes)
	{
		Invert_PPFilter ppf = this.context.getShader(Invert_PPFilter.class);
		ppf.setInput("frame", srcFbo.getAttachmentId(t));
		ppf.use();	
		{
			draw();
		}
		ppf.stop();	

		fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, true);
	}

}
