package com.codered.engine.shaders.object;

import java.util.ArrayList;

import com.codered.engine.shaders.shader.ShaderProgram;
import com.google.common.collect.Lists;

public abstract class DerefObjectShader extends ShaderProgram
{
	private static ArrayList<DerefObjectShader> shaders = Lists.newArrayList();
	
	public DerefObjectShader()
	{
		shaders.add(this);
	}
	
	public static void clean()
	{
		for(DerefObjectShader s : shaders)
		{
			s.cleanup();
		}
		
		shaders.clear();
	}
}
