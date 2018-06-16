package com.codered.engine.ppf;

import com.codered.engine.fbo.FBO;
import com.codered.engine.fbo.FBOTarget;
import com.codered.engine.managing.UnityScreen;
import com.codered.engine.utils.BindingUtils;
import com.codered.engine.utils.GLUtils;
import com.codered.engine.window.WindowContext;

public abstract class PPF
{
	protected WindowContext context;
	
	protected UnityScreen screen;
	
	protected FBO fbo;
	
	public PPF(WindowContext context)
	{
		this.context = context;
		
		this.screen = new UnityScreen();
		
		this.fbo = new FBO();
		this.fbo.applyColorTextureAttachment(0, true);
		this.context.getWindow().addResizeHandler((src, ref) -> { this.fbo.resize(src.width, src.height); });
	}
	
	public void doPostProcess(FBO srcFbo, int t, FBO dstFbo, int tRes)
	{
		BindingUtils.bindFramebuffer(this.fbo);
		GLUtils.clearColor();
		
		doInternalPostProcess(srcFbo, FBOTarget.getByIndex(t), dstFbo, FBOTarget.getByIndex(tRes));
	}
	
	public void doPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes)
	{
		BindingUtils.bindFramebuffer(this.fbo);
		GLUtils.clearColor();
		
		doInternalPostProcess(srcFbo, t, dstFbo, tRes);
	}
	
	protected void draw()
	{
		this.screen.draw();
	}
	
	protected abstract void doInternalPostProcess(FBO srcFbo, FBOTarget t, FBO dstFbo, FBOTarget tRes);
}
