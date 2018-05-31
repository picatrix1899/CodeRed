package com.codered.engine.shaders.object.simple;

import java.util.List;

import com.codered.engine.light.Glow;
import com.codered.engine.managing.Material;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class Glow_OShader extends SimpleObjectShader
{

	public Glow_OShader(WindowContext context)
	{
		super(context);
	}

	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		addUniform("textureMap");	
		addUniform("glowMap");
		
		addUniform("glow.base.color");
		addUniform("glow.base.intensity");
		addUniform("glow.intensity");
		addUniform("glow.affect");
	}
	
	private void loadMaterial0(Material m)
	{
		loadTexture("glowMap", 0, this.context.getResourceManager().getTexture(m.getGlowMap()));
	}	
	
	private void loadGlow0(Glow glow)
	{
		loadColor3("glow.base.color", glow.base.color);
		loadFloat("glow.base.intensity", glow.base.intensity);
		loadFloat("glow.intensity", glow.intesity);
		loadFloat("glow.affect", glow.affect);
	}
	
	public void use()
	{
		start();
		
		super.use();
		
		loadMaterial0(getInput("material"));
		loadGlow0(getInput("glow"));
	}

	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("o_glow"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("o_glow"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "vertexPos"));
		attribs.add(new DMap2<Integer,String>(0, "texCoords"));
	}
	
}
