package com.codered.engine.shaders.object.derefered;

import com.codered.engine.light.PointLight;
import com.codered.engine.shaders.object.DerefObjectShader;
import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("object/derefered/do_pointLight")
@FragmentShader("object/derefered/do_pointLight")
@Attrib(pos=0, var="pos")
public class PointLight_DOShader extends DerefObjectShader
{
	protected void getAllUniformLocations()
	{
		addUniform("position");
		addUniform("normal");
		addUniform("albedo");
		addUniform("specular");
		
		for(int i = 0; i < 4; i++)
		{
			addUniform("lights[" + i + "].base.color");
			addUniform("lights[" + i + "].base.intensity");
			addUniform("lights[" + i + "].position");
			addUniform("lights[" + i + "].attenuation.constant");
			addUniform("lights[" + i + "].attenuation.linear");
			addUniform("lights[" + i + "].attenuation.exponent");
		}
		
		addUniform("cameraPos");
		
	}
	
	public void loadPointLight(PointLight light) { setInput("pointLight", light); }
	
	private void loadPointLight0(PointLight light)
	{
		loadColor3("lights[0].base.color", light.base.color);
		loadFloat("lights[0].base.intensity", light.base.intensity);
		loadVector3("lights[0].position", light.position);
		loadFloat("lights[0].attenuation.constant",light.attenuation.constant);
		loadFloat("lights[0].attenuation.linear",light.attenuation.linear);
		loadFloat("lights[0].attenuation.exponent",light.attenuation.exponent);
	}
	
	public void loadPositions(int i) { setInput("position", i); }
	public void loadNormals(int i) { setInput("normal", i); }
	public void loadAlbedo(int i) { setInput("albedo", i); }
	public void loadSpecular(int i) { setInput("specular", i); }
	
	public void use()
	{
		start();
		loadTexture("position", 0, getInput("position"));
		loadTexture("normal", 1, getInput("normal"));
		loadTexture("albedo", 2, getInput("albedo"));
		loadTexture("specular", 3, getInput("specular"));
		
		loadVector3("cameraPos", getInput("cameraPos"));
		loadPointLight0(getInput("pointLight"));
	}

}
