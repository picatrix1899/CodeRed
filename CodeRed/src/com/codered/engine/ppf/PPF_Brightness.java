package com.codered.engine.ppf;

import com.codered.engine.shaders.postprocess.filter.Brightness_PPFilter;
import com.codered.engine.window.WindowContext;
import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;

public class PPF_Brightness extends PPF
{
	public PPF_Brightness(WindowContext context)
	{
		super(context);
	}

	protected void doInternalPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes)
	{
		Brightness_PPFilter ppf = this.context.getShader(Brightness_PPFilter.class);
		
		ppf.setInput("textureMap", srcFbo.getAttachmentId(t));
		ppf.use();
		{
			draw();
		}
		ppf.stop();	

		this.fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, false);
	}

}
