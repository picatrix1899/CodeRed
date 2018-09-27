package com.codered.shader;

import cmn.utilslib.color.colors.api.IColor3Base;
import cmn.utilslib.math.vector.api.Vec3fBase;

public class UniformColor3 extends Uniform
{

	public UniformColor3(String name)
	{
		super(name);
		
		addUniform("");
	}

	public void load(Vec3fBase vector)
	{
		loadVector3("", vector);
	}
	
	public void load(IColor3Base color)
	{
		loadColor3("", color);
	}
}
