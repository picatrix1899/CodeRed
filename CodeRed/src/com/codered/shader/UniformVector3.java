package com.codered.shader;

import cmn.utilslib.math.vector.api.Vec3fBase;

public class UniformVector3 extends Uniform
{

	private Vec3fBase vector;
	
	public UniformVector3(String name)
	{
		super(name);
		
		addUniform("");
	}

	public void load()
	{
		loadVector3("", this.vector);
	}

	public void set(Vec3fBase vector)
	{
		this.vector = vector;
	}
	
}
