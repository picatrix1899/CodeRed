package com.codered.engine.shaders.object.derefered;

import com.codered.engine.light.DirectionalLight;
import com.codered.engine.shaders.object.DerefObjectShader;
import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("object/derefered/do_directionalLight")
@FragmentShader("object/derefered/do_directionalLight")
@Attrib(pos=0, var="pos")
public class DirectionalLight_DOShader extends DerefObjectShader
{
	protected void getAllUniformLocations()
	{
		addUniform("position");
		addUniform("normal");
		addUniform("albedo");
		addUniform("specular");
		
		addUniform("cameraPos");
		
		addUniform("directionalLight.base.color");
		addUniform("directionalLight.base.intensity");
		addUniform("directionalLight.direction");
	}
	
	public void loadDirectionalLight(DirectionalLight light) { setInput("directionalLight", light); }
	
	private void loadDirectionalLight0(DirectionalLight light)
	{
		loadColor3("directionalLight.base.color", light.base.color);
		loadFloat("directionalLight.base.intensity", light.base.intensity);
		loadVector3("directionalLight.direction", light.direction);
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
		loadDirectionalLight0(getInput("directionalLight"));
	}

}
