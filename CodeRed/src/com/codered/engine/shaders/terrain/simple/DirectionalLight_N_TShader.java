package com.codered.engine.shaders.terrain.simple;

import com.codered.engine.light.DirectionalLight;
import com.codered.engine.managing.Material;
import com.codered.engine.shader.Shader.Attrib;
import com.codered.engine.shader.Shader.FragmentShader;
import com.codered.engine.shader.Shader.VertexShader;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;


@VertexShader("t_directionalLight_N")
@FragmentShader("t_directionalLight_N")
@Attrib(pos=0, var="vertexPos")
@Attrib(pos=1, var="texCoords")
@Attrib(pos=2, var="normal")
@Attrib(pos=3, var="tangent")
public class DirectionalLight_N_TShader extends SimpleTerrainShader
{

	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		addUniform("textureMap");		
		addUniform("normalMap");		
		
		
		addUniform("directionalLight.base.color");
		addUniform("directionalLight.base.intensity");
		addUniform("directionalLight.direction");

		addUniform("specularPower");
		addUniform("specularIntensity");
	}
	
	public void loadMaterial(Material m) { setInput("material", m); }
	
	public void loadDirectionalLight(DirectionalLight light) { setInput("directionalLight", light); }	
	
	private void loadMaterial0(Material mat)
	{
		loadTexture("textureMap", 0, mat.getColorMap().getId());
		loadTexture("normalMap", 1, mat.getNormalMap().getId());
		
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
}
