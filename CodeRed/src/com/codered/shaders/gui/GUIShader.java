package com.codered.shaders.gui;

import com.codered.shader.ShaderProgram;
import com.codered.shader.UniformVector2;
import com.codered.window.WindowContext;

public abstract class GUIShader extends ShaderProgram
{

	public UniformVector2 u_screenSpace = new UniformVector2("screenSpace");
	
	public GUIShader(WindowContext context)
	{
		super(context);
		
		addUniform(u_screenSpace);
	}
}
