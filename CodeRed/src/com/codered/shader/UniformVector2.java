package com.codered.shader;

import cmn.utilslib.math.vector.api.Vec2fBase;

public class UniformVector2 extends Uniform
{

	public UniformVector2(String name)
	{
		super(name);
		
		addUniform("");
	}

	public void load(Vec2fBase vector)
	{
		loadVector2("", vector);
	}
	
}
