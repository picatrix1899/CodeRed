package com.codered.engine.shaders.postprocess.filter;

import java.util.ArrayList;

import com.codered.engine.shader.ShaderProgram;
import com.google.common.collect.Lists;

public abstract class PPFShader extends ShaderProgram
{
	
	private static ArrayList<PPFShader> shaders = Lists.newArrayList();
	
	public PPFShader()
	{
		shaders.add(this);
	}
	
	public static void clean()
	{
		for(PPFShader s : shaders)
		{
			s.cleanup();
		}
		
		shaders.clear();
	}
}
