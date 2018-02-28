package com.codered.engine.shaders.gui;

import com.codered.engine.shader.Shader.Attrib;
import com.codered.engine.shader.Shader.FragmentShader;
import com.codered.engine.shader.Shader.VertexShader;

@VertexShader("gui_no")
@FragmentShader("gui_no")
@Attrib(pos=0, var="vertexPos")
@Attrib(pos=1, var="texCoords")
public class No_GUIShader extends GUIShader
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
