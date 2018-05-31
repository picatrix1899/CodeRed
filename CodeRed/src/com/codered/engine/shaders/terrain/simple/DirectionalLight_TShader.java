package com.codered.engine.shaders.terrain.simple;

import java.util.List;

import com.codered.engine.light.DirectionalLight;
import com.codered.engine.managing.Material;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;
import com.codered.engine.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;


public class DirectionalLight_TShader extends SimpleTerrainShader
{

	public DirectionalLight_TShader(WindowContext context)
	{
		super(context);
	}

	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();

		addUniform("textureMap");		
		
		addUniform("directionalLight.base.color");
		addUniform("directionalLight.base.intensity");
		addUniform("directionalLight.direction");

		addUniform("specularPower");
		addUniform("specularIntensity");
		addUniform("skyColor");
	}
	
	public void loadMaterial(Material m) { setInput("material", m); }
	
	public void loadDirectionalLight(DirectionalLight light) { setInput("directionalLight", light); }	
	
	private void loadMaterial0(Material mat)
	{
		loadTexture("textureMap", 0, mat.getAlbedoMap());
		loadFloat("specularPower", mat.getSpecularPower());
		loadFloat("specularIntensity", mat.getSpecularIntensity());
	}
	
	private void loadDirectionalLight0(DirectionalLight light)
	{
		loadColor3("directionalLight.base.color", light.base.color);
		loadFloat("directionalLight.base.intensity", light.base.intensity);
		loadVector3("directionalLight.direction", light.direction);
	}

	
	public void use()
	{
		start();
		
		super.use();
		
		loadMaterial0(getInput("material"));
		
		loadDirectionalLight0(getInput("directionalLight"));
	}
	
	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("t_directionalLight"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("t_directionalLight"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "vertexPos"));
		attribs.add(new DMap2<Integer,String>(0, "texCoords"));
		attribs.add(new DMap2<Integer,String>(0, "normal"));
	}
}
