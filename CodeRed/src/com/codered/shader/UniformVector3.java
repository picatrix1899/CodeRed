package com.codered.shader;

import cmn.utilslib.math.vector.api.Vec3fBase;

public class UniformVector3 extends Uniform
{

	private Vec3fBase vector;
	
	public UniformVector3(String name)
	{
		super(name);
		
		addUniform(this.name);
	}

	public void load()
	{
		loadVector3(this.name, this.vector);
	}

	public void set(Vec3fBase vector)
	{
		this.vector = vector;
	}
	
}
