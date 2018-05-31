package com.codered.engine.shaders.gui;

import com.codered.engine.shader.ShaderProgram;
import com.codered.engine.window.WindowContext;

public abstract class GUIShader extends ShaderProgram
{

	public GUIShader(WindowContext context)
	{
		super(context);
	}

	protected void getAllUniformLocations()
	{
		addUniform("screenSpace");
	}
	
	public void use()
	{
		start();
		loadVector2("screenSpace", getInput("screenSpace"));
	}
}
