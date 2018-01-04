package com.codered.engine.shaders.shader;

public class ShaderParts
{
	private static ShaderPartList builtIn = new ShaderPartList();
	private static ShaderPartList custom = new ShaderPartList();
	
	public static ShaderPartList builtIn()
	{
		return builtIn;
	}
	
	public static ShaderPartList custom()
	{
		return custom;
	}
}
