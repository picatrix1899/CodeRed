package com.codered.shader;

import org.barghos.core.api.tuple.ITup2R;

import cmn.utilslib.math.vector.api.Vec2fBase;

public class UniformVector2 extends Uniform
{

	public UniformVector2(String name)
	{
		super(name);
		
		addUniform("");
	}

	public void set(Vec2fBase vector)
	{
		loadVector2("", vector);
	}
	
	public void set(ITup2R vector)
	{
		loadVector2("", vector);
	}
}
