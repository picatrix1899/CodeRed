package com.codered.engine.shaders.object.simple;

import com.codered.engine.light.AmbientLight;
import com.codered.engine.managing.Material;
import com.codered.engine.shaders.object.SimpleObjectShader;
import com.codered.engine.shader.Shader.Attrib;
import com.codered.engine.shader.Shader.FragmentShader;
import com.codered.engine.shader.Shader.VertexShader;

import cmn.utilslib.math.vector.Vector3f;

@VertexShader("o_ambientLight")
@FragmentShader("o_ambientLight")
@Attrib(pos=0, var="vertexPos")
@Attrib(pos=1, var="texCoords")
@Attrib(pos=2, var="normal")
@Attrib(pos=3, var="tangent")
public class AmbientLight_OShader extends SimpleObjectShader
{

	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		addUniform("textureMap");
		
		addUniform("ambientLight.base.color");
		addUniform("ambientLight.base.intensity");
		
		addUniform("skyColor");
	}
	

	
	public void loadAmbientLight(AmbientLight light)
	{
		setInput("ambientLight", light);
	}
	
	
	private void loadMaterial0(Material mat)
	{
		loadTexture("textureMap", 0, mat.getColorMap().getId());
	}	
	
	private void loadAmbientLight0(AmbientLight light)
	{
		loadColor3("ambientLight.base.color", light.base.color);
		loadFloat("ambientLight.base.intensity", light.base.intensity);
	}
	
	public void use()
	{
		start();
		
		super.use();
		
		loadMaterial0(getInput("material"));
		loadAmbientLight0(getInput("ambientLight"));
		loadVector3("skyColor", new Vector3f(0.0f));
	}
	
}
