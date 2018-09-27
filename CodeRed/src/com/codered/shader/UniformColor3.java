package com.codered.shader;

import cmn.utilslib.color.colors.api.IColor3Base;

public class UniformColor3 extends Uniform
{

	public UniformColor3(String name)
	{
		super(name);
		
		addUniform("");
	}

	public void load()
	{
		
	}

	public void set(IColor3Base color)
	{
		loadColor3("", color);
	}
	
}
