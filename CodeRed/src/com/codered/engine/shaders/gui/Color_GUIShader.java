package com.codered.engine.shaders.gui;

import com.codered.engine.shaders.shader.ShaderProgram;
import com.codered.engine.shaders.shader.Shader.Attrib;
import com.codered.engine.shaders.shader.Shader.FragmentShader;
import com.codered.engine.shaders.shader.Shader.VertexShader;

@VertexShader("gui/gui_colorShading")
@FragmentShader("gui/gui_colorShading")
@Attrib(pos=0, var="vertexPos")
public class Color_GUIShader extends ShaderProgram
{

	
	public static final Color_GUIShader instance = new Color_GUIShader();

	
	protected void getAllUniformLocations()
	{
		addUniform("screenSpace");
		addUniform("color");
	}


	public void use()
	{
		start();
		
		loadVector2("screenSpace", getInput("screenSpace"));
		loadColor3("color", getInput("color"));
	}
}
