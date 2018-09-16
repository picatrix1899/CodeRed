package com.codered.ppf;

import com.codered.fbo.FBO;
import com.codered.fbo.FBOTarget;
import com.codered.shaders.postprocess.filter.DepthMap_PPFilter;
import com.codered.window.WindowContext;

public class PPF_DepthMap extends PPF
{

	private float near;
	private float far;
	
	public PPF_DepthMap(WindowContext context)
	{
		super(context);
	}

	public void setParameters(float near, float far)
	{
		this.near = near;
		this.far = far;
	}
	
	protected void doInternalPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes)
	{
		DepthMap_PPFilter ppf = this.context.getShader(DepthMap_PPFilter.class);
		ppf.setInput("frame", srcFbo.getAttachmentId(t));
		ppf.setInput("near",near);
		ppf.setInput("far", far);
		ppf.use();	
		{
			draw();
		}
		ppf.stop();	

		this.fbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, false);
	}

}