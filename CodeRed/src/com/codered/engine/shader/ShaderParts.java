package com.codered.engine.shader;

public class ShaderParts
{
	private ShaderPartList builtIn = new ShaderPartList();
	private ShaderPartList custom = new ShaderPartList();
	
	public ShaderParts()
	{
		
	}
	
	public ShaderPartList builtIn()
	{
		return builtIn;
	}
	
	public ShaderPartList custom()
	{
		return custom;
	}
}
