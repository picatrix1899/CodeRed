package com.codered.shader;

import org.barghos.math.experimental.matrix.Mat4f;

public class UniformMat4 extends Uniform
{

	private Mat4f mat = new Mat4f();
	
	private int location = -1;
	
	public UniformMat4(String name, Object... data)
	{
		super(name);
	}

	@Override
	public void set(Object... obj)
	{
		if(!(obj[0] instanceof Mat4f)) throw new IllegalArgumentException();
		Mat4f m = (Mat4f)obj[0];
		mat.set(m);
	}

	@Override
	public void load()
	{
		loadMat4(this.location, this.mat);
	}

	@Override
	public void loadUniformLocations(int shaderProgrammId)
	{
		this.location = getLocationFor(this.name, shaderProgrammId);
	}

}
