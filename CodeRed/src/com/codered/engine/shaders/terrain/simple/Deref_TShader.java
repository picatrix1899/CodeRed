package com.codered.engine.shaders.terrain.simple;

import com.codered.engine.managing.Material;
import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;

@VertexShader("t_deref")
@FragmentShader("t_deref")
@Attrib(pos=0, var="vertexPos")
@Attrib(pos=1, var="texCoords")
@Attrib(pos=2, var="normal")
@Attrib(pos=3, var="tangent")
public class Deref_TShader extends SimpleTerrainShader
{
	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		addUniform("textureMap");
		addUniform("normalMap");
		addUniform("glowMap");
		
		addUniform("specularIntensity");
		addUniform("specularPower");
	}
	
	private void loadMaterial0(Material mat)
	{
		loadTexture("textureMap", 0, mat.getColorMap().getId());
		loadTexture("normalMap", 1, mat.getNormalMap().getId());
		loadTexture("glowMap", 2, mat.getGlowMap().getId());
		
		loadFloat("specularIntensity", mat.getSpecularIntensity());
		loadFloat("specularPower", mat.getSpecularPower());
	}

	public void use()
	{
		start();
	
		super.use();
		
		loadMaterial0(getInput("material"));
	}

}
