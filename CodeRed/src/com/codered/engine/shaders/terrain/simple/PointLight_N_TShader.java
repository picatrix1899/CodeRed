package com.codered.engine.shaders.terrain.simple;

import com.codered.engine.light.PointLight;
import com.codered.engine.managing.Material;
import com.codered.engine.resource.ResourceManager;
import com.codered.engine.shader.Shader.Attrib;
import com.codered.engine.shader.Shader.FragmentShader;
import com.codered.engine.shader.Shader.VertexShader;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;

@VertexShader("t_pointLight_N")
@FragmentShader("t_pointLight_N")
@Attrib(pos=0, var="vertexPos")
@Attrib(pos=1, var="texCoords")
@Attrib(pos=2, var="normal")
@Attrib(pos=3, var="tangent")
public class PointLight_N_TShader extends SimpleTerrainShader
{

	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		addUniform("textureMap");		
		addUniform("normalMap");
		
		for(int i = 0; i < 4; i++)
		{
			addUniform("lights[" + i + "].base.color");
			addUniform("lights[" + i + "].base.intensity");
			addUniform("lights[" + i + "].position");
			addUniform("lights[" + i + "].attenuation.constant");
			addUniform("lights[" + i + "].attenuation.linear");
			addUniform("lights[" + i + "].attenuation.exponent");
		}

		addUniform("specularPower");
		addUniform("specularIntensity");
	}

	public void loadMaterial(Material m) { setInput("material", m); }

	public void loadPointLight(PointLight light) { setInput("pointLight", light); }
	
	private void loadMaterial0(Material mat)
	{
		loadTexture("textureMap", 0, ResourceManager.getColorMap(mat.getColorMap()).getId());
		loadTexture("normalMap", 1, ResourceManager.getNormalMap(mat.getNormalMap()).getId());		
		loadFloat("specularPower", mat.getSpecularPower());
		loadFloat("specularIntensity", mat.getSpecularIntensity());
	}
	
	private void loadPointLight0(PointLight light)
	{
		loadColor3("lights[0].base.color", light.base.color);
		loadFloat("lights[0].base.intensity", light.base.intensity);
		loadVector3("lights[0].position", light.position);
		loadFloat("lights[0].attenuation.constant",light.attenuation.constant);
		loadFloat("lights[0].attenuation.linear",light.attenuation.linear);
		loadFloat("lights[0].attenuation.exponent",light.attenuation.exponent);
	}
	

	
	public void use()
	{
		start();
		
		super.use();
		
		loadMaterial0(getInput("material"));
		
		loadPointLight0(getInput("pointLight"));
	}
}
