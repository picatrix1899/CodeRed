package com.codered.engine.shaders.gui;

import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("gui_texture")
@FragmentShader("gui_texture")
@Attrib(pos=0, var="vertexPos")
@Attrib(pos=1, var="texCoords")
public class Texture_GUIShader extends GUIShader
{
	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		addUniform("textureMap");
	}

	public void loadTextureMap(int id)
	{
		setInput("textureMap", id);
	}

	public void use()
	{
		super.use();
		
		loadTexture("textureMap", 0, getInput("textureMap"));
	}
}
