package com.codered.engine.shaders.terrain.simple;

import com.codered.engine.managing.Material;
import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;
import com.codered.engine.shaders.terrain.SimpleTerrainShader;

@VertexShader("t_no")
@FragmentShader("t_no")
@Attrib(pos=0, var="vertexPos")
@Attrib(pos=1, var="texCoords")
public class No_TShader extends SimpleTerrainShader
{

	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();

		addUniform("textureMap");
	}
	
	public void loadMaterial(Material m) { setInput("material", m); }
	
	private void loadMaterial0(Material mat)
	{
		loadTexture("textureMap", 0, mat.getColorMap().getId());
	}

	public void use()
	{
		start();
		
		super.use();
		
		loadMaterial0(getInput("material"));
	}
}
