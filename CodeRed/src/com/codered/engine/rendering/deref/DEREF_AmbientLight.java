package com.codered.engine.rendering.deref;

import com.codered.engine.light.AmbientLight;
import com.codered.engine.managing.DEREF;
import com.codered.engine.managing.FBO;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.rendering.ppf.PPFilter;
import com.codered.engine.shaders.DOShaders;

import cmn.utilslib.color.colors.LDRColor3;

public class DEREF_AmbientLight extends DEREF
{
	public static final DEREF_AmbientLight instance = new DEREF_AmbientLight();
	
	AmbientLight ambient = new AmbientLight(LDRColor3.BLACK, 1.0f);
	
	public DEREF_AmbientLight setAmbient(AmbientLight a)
	{
		this.ambient = a;
		
		return this;
	}
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo , Target tRes, boolean blend)
	{
		bindBuffer();
		
		start();
		
		DOShaders.AmbientLight.loadAmbientLight(ambient);
		
		
		DOShaders.AmbientLight.setInput("albedo", srcFbo.getBufferTexture(Target.COLOR2));
		DOShaders.AmbientLight.setInput("normal", srcFbo.getBufferTexture(Target.COLOR1));
		DOShaders.AmbientLight.setInput("position", srcFbo.getBufferTexture(Target.COLOR0));
		DOShaders.AmbientLight.use();
		{
			drawQuad();
		}
		DOShaders.AmbientLight.stop();	
		
		stop();

		if(blend)
		{
			PPFilter.SimpleBlend().doPostProcess(this.fbo, dstFbo, dstFbo, FBO.Target.COLOR0, tRes);
		}
		else
		{
			this.fbo.blitAttachment(dstFbo, Target.COLOR0, tRes, true);
		}

	}
}
