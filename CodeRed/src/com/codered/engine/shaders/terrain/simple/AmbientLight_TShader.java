package com.codered.engine.shaders.terrain.simple;

import java.util.List;

import com.codered.engine.light.AmbientLight;
import com.codered.engine.material.Material;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;
import com.codered.engine.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class AmbientLight_TShader extends SimpleTerrainShader
{
	public AmbientLight_TShader(WindowContext context)
	{
		super(context);
	}

	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		addUniform("textureMap");
		
		addUniform("ambientColor");
		addUniform("ambientIntensity");
	}
	
	public void loadAmbientLight(AmbientLight light) { setInput("ambientLight", light); }
	
	public void loadMaterial(Material m) { setInput("material", m); }
	
	private void loadAmbientLight0(AmbientLight light)
	{
		loadColor3("ambientColor", light.base.color);
		loadFloat("ambientIntensity", light.base.intensity);
	}
	
	private void loadMaterial0(Material mat)
	{
		loadTexture("textureMap", 0, mat.getAlbedoMap());
	}
	
	
	public void use()
	{
		start();
		
		super.use();
		
		loadMaterial0(getInput("material"));
		loadAmbientLight0(getInput("ambientLight"));
	}
	
	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("t_ambientLight"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("t_ambientLight"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "vertexPos"));
		attribs.add(new DMap2<Integer,String>(1, "texCoords"));
		attribs.add(new DMap2<Integer,String>(2, "normal"));
		attribs.add(new DMap2<Integer,String>(3, "tanget"));
	}
}
