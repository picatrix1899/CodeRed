package com.codered.shaders.gui;

import com.codered.shader.ShaderProgram;
import com.codered.window.WindowContext;

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

		loadVector2("screenSpace", this.context.getSize());
	}
}
