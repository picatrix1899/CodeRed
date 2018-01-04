package com.codered.engine.shaders.object.derefered;

import com.codered.engine.light.AmbientLight;
import com.codered.engine.shaders.object.DerefObjectShader;
import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("object/derefered/do_ambientLight")
@FragmentShader("object/derefered/do_ambientLight")
@Attrib(pos=0, var="pos")
public class AmbientLight_DOShader extends DerefObjectShader
{
	protected void getAllUniformLocations()
	{
		addUniform("position");
		addUniform("normal");
		addUniform("albedo");
		
		addUniform("ambientLight.base.color");
		addUniform("ambientLight.base.intensity");
	}
	
	public void loadAmbientLight(AmbientLight light) { setInput("ambientLight", light); }
	
	private void loadAmbientLight0(AmbientLight light)
	{
		loadColor3("ambientLight.base.color", light.base.color);
		loadFloat("ambientLight.base.intensity", light.base.intensity);
	}
	
	public void loadPositions(int i) { setInput("position", i); }
	public void loadNormals(int i) { setInput("normal", i); }
	public void loadAlbedo(int i) { setInput("albedo", i); }
	
	
	public void use()
	{
		start();
		loadTexture("position", 0, getInput("position"));
		loadTexture("normal", 1,  getInput("normal"));
		loadTexture("albedo", 2,  getInput("albedo"));
		loadAmbientLight0(getInput("ambientLight"));
	}

}
