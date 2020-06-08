package com.codered.rendering.shader;

import org.barghos.math.matrix.Mat4;

public class UniformMat4 extends Uniform
{

	private Mat4 mat = new Mat4();
	
	private int location = -1;
	
	public UniformMat4(String name, Object... data)
	{
		super(name);
	}

	@Override
	public void set(Object... obj)
	{
		if(!(obj[0] instanceof Mat4)) throw new IllegalArgumentException();
		Mat4 m = (Mat4)obj[0];
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
