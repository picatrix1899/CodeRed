package com.codered.engine.rendering.deref;

import com.codered.engine.light.PointLight;
import com.codered.engine.managing.DEREF;
import com.codered.engine.managing.FBO;
import com.codered.engine.managing.FBO.Target;
import com.codered.engine.rendering.ppf.PPFilter;
import com.codered.engine.shaders.DOShaders;

import cmn.utilslib.color.colors.LDRColor3;
import cmn.utilslib.math.vector.Vector3f;

public class DEREF_PointLight extends DEREF
{
	public static final DEREF_PointLight instance = new DEREF_PointLight();
	
	PointLight pointLight = new PointLight(new Vector3f(1.0f), LDRColor3.BLACK, 1.0f, 1.0f, 1.0f, 1.0f);
	
	public DEREF_PointLight setPointLight(PointLight d)
	{
		this.pointLight = d;
		
		return this;
	}
	
	public void doPostProcess(FBO srcFbo, Target t, FBO dstFbo, Target tRes, boolean blend)
	{
		bindBuffer();
		
		start();
		
		DOShaders.PointLight.loadPointLight(pointLight);
		
		
		DOShaders.PointLight.setInput("specular", srcFbo.getBufferTexture(Target.COLOR4));
		DOShaders.PointLight.setInput("albedo", srcFbo.getBufferTexture(Target.COLOR2));
		DOShaders.PointLight.setInput("normal", srcFbo.getBufferTexture(Target.COLOR1));
		DOShaders.PointLight.setInput("position", srcFbo.getBufferTexture(Target.COLOR0));
		DOShaders.PointLight.use();
		{
			drawQuad();
		}
		DOShaders.PointLight.stop();	
		
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
