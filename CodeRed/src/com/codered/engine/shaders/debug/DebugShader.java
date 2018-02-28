package com.codered.engine.shaders.debug;

import java.util.ArrayList;

import com.codered.engine.shader.ShaderProgram;

import cmn.utilslib.essentials.Auto;

public abstract class DebugShader extends ShaderProgram
{
	private static ArrayList<DebugShader> shaders = Auto.ArrayList();
	
	public DebugShader()
	{
		shaders.add(this);
	}
	
	public static void clean()
	{
		for(DebugShader s : shaders)
		{
			s.cleanup();
		}
		
		shaders.clear();
	}
}
