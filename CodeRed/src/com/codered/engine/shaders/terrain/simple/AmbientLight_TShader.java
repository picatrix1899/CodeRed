package com.codered.engine.shaders.terrain.simple;

import com.codered.engine.light.AmbientLight;
import com.codered.engine.managing.Material;
import com.codered.engine.resource.ResourceManager;
import com.codered.engine.shader.Shader.Attrib;
import com.codered.engine.shader.Shader.FragmentShader;
import com.codered.engine.shader.Shader.VertexShader;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;

@VertexShader("t_ambientLight")
@FragmentShader("t_ambientLight")
@Attrib(pos=0, var="vertexPos")
@Attrib(pos=1, var="texCoords")
@Attrib(pos=2, var="normal")
@Attrib(pos=3, var="tanget")
public class AmbientLight_TShader extends SimpleTerrainShader
{
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
		loadTexture("textureMap", 0, ResourceManager.getColorMap(mat.getColorMap()).getId());
	}
	
	
	public void use()
	{
		start();
		
		super.use();
		
		loadMaterial0(getInput("material"));
		loadAmbientLight0(getInput("ambientLight"));
	}
	
}
