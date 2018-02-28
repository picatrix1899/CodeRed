package com.codered.engine.shaders.object.simple;

import com.codered.engine.light.PointLight;
import com.codered.engine.managing.Material;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.shader.Shader.Attrib;
import com.codered.engine.shader.Shader.FragmentShader;
import com.codered.engine.shader.Shader.VertexShader;

@VertexShader("o_pointLight_N")
@FragmentShader("o_pointLight_N")
@Attrib(pos=0, var="vertexPos")
@Attrib(pos=1, var="texCoords")
@Attrib(pos=2, var="normal")
@Attrib(pos=3, var="tangent")
public class PointLight_N_OShader extends SimpleObjectShader
{

	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		for(int i = 0; i < 4; i++)
		{
			addUniform("lights[" + i + "].base.color");
			addUniform("lights[" + i + "].base.intensity");
			addUniform("lights[" + i + "].position");
			addUniform("lights[" + i + "].attenuation.constant");
			addUniform("lights[" + i + "].attenuation.linear");
			addUniform("lights[" + i + "].attenuation.exponent");
		}
		
		addUniform("textureMap");
		addUniform("normalMap");		
		
		addUniform("specularPower");
		addUniform("specularIntensity");
	}
	
	public void loadPointLight(PointLight light)
	{
		setInput("pointLight", light);
	}
	
	private void loadMaterial0(Material mat)
	{
		loadTexture("textureMap", 0, mat.getColorMap().getId());
		loadTexture("normalMap", 1, mat.getNormalMap().getId());		
		
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
