package com.codered.shader;

import cmn.utilslib.color.colors.api.IColor3Base;

public class UniformColor3 extends Uniform
{

	private IColor3Base color;
	
	public UniformColor3(String name)
	{
		super(name);
		
		addUniform("");
	}

	public void load()
	{
		loadColor3("", this.color);
	}

	public void set(IColor3Base color)
	{
		this.color = color;
	}
	
}
