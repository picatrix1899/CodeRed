package com.codered.engine.shaders.gui;

import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("gui_color")
@FragmentShader("gui_color")
@Attrib(pos=0, var="vertexPos")
public class Color_GUIShader extends GUIShader
{
	
	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		addUniform("color");
	}


	public void use()
	{
		super.use();
		
		loadColor3("color", getInput("color"));
	}
}
