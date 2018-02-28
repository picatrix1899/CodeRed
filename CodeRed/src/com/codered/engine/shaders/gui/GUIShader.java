package com.codered.engine.shaders.gui;

import java.util.ArrayList;

import com.codered.engine.shader.ShaderProgram;
import com.google.common.collect.Lists;

public abstract class GUIShader extends ShaderProgram
{
	private static ArrayList<GUIShader> shaders = Lists.newArrayList();
	
	public GUIShader()
	{
		shaders.add(this);
	}
	
	public static void clean()
	{
		for(GUIShader s : shaders)
		{
			s.cleanup();
		}
		
		shaders.clear();
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
