package com.codered.engine.rendering.ppf;

import com.codered.engine.shaders.postprocess.filter.ContrastMS_PPFilter;
import com.codered.engine.utils.BindingUtils;
import com.codered.engine.utils.GLUtils;
import com.codered.engine.window.WindowContext;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.fbo.Framebuffer;
import com.codered.engine.fbo.MSFBO;
import com.codered.engine.ppf.PPF;

public class PPF_ContrastMS extends PPF
{
	public PPF_ContrastMS(WindowContext context)
	{
		super(context);
		
		this.msfbo = new MSFBO(4);
		this.msfbo.applyColorTextureAttachment(FBOTarget.COLOR0, true);
		this.context.getWindow().addResizeHandler((src, ref) -> { this.msfbo.resize(src.width, src.height); });
	}

	private float contrast;
	
	private MSFBO msfbo;

	
	public PPF_ContrastMS setContrast(float f)
	{
		this.contrast = f;
		return this;
	}
	
	public void doPostProcess(MSFBO srcFbo, int t, MSFBO dstFbo, int tRes)
	{
		doPostProcess(srcFbo, FBOTarget.getByIndex(t), dstFbo, FBOTarget.getByIndex(tRes));
	}
	
	public void doPostProcess(MSFBO srcFbo, FBOTarget t, Framebuffer dstFbo, FBOTarget tRes)
	{
		BindingUtils.bindFramebuffer(this.msfbo);
		GLUtils.clearColor();
		
		ContrastMS_PPFilter ppf = this.context.getShader(ContrastMS_PPFilter.class);
		
		ppf.setInput("textureMap", srcFbo.getAttachmentId(t));
		ppf.setInput("contrast", contrast);
		ppf.use();	
		{
			this.screen.draw();
		}
		ppf.stop();

		this.msfbo.blitAttachment(dstFbo, FBOTarget.COLOR0, tRes, false);
	}
	
}
