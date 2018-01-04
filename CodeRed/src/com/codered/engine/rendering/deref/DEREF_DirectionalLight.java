package com.codered.engine.rendering.deref;

import com.codered.engine.light.DirectionalLight;
import com.codered.engine.managing.DEREF;
import com.codered.engine.managing.FBO;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.rendering.ppf.PPFilter;
import com.codered.engine.shaders.DOShaders;

import cmn.utilslib.color.colors.LDRColor3;
import cmn.utilslib.math.vector.Vector3f;

public class DEREF_DirectionalLight extends DEREF
{
	public static final DEREF_DirectionalLight instance = new DEREF_DirectionalLight();
	
	DirectionalLight directionalLight = new DirectionalLight(LDRColor3.BLACK, 1.0f, new Vector3f(0.0f, -1.0f, 0.0f));
	
	public DEREF_DirectionalLight setDirectionalLight(DirectionalLight d)
	{
		this.directionalLight = d;
		
		return this;
	}
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
		bindBuffer();
		
		start();
		
		DOShaders.DirectionalLight.loadDirectionalLight(directionalLight);
		
		
		DOShaders.DirectionalLight.setInput("specular", srcFbo.getBufferTexture(Target.COLOR4));
		DOShaders.DirectionalLight.setInput("albedo", srcFbo.getBufferTexture(Target.COLOR2));
		DOShaders.DirectionalLight.setInput("normal", srcFbo.getBufferTexture(Target.COLOR1));
		DOShaders.DirectionalLight.setInput("position", srcFbo.getBufferTexture(Target.COLOR0));
		DOShaders.DirectionalLight.use();
		{
			drawQuad();
		}
		DOShaders.DirectionalLight.stop();	
		
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
