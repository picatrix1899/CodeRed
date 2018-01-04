package com.codered.engine.shaders.gui;

import com.codered.engine.shaders.shader.ShaderProgram;

public abstract class GUIShader extends ShaderProgram
{
	public static No_GUIShader No()
	{
		return No_GUIShader.instance;
	}
	
	public static Color_GUIShader Color()
	{
		return Color_GUIShader.instance;
	}
}
