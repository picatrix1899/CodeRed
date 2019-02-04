package com.codered.sh;

import org.barghos.math.matrix.Mat4f;

public class UniformMat4 extends Uniform
{

	Mat4f mat = new Mat4f();
	
	public UniformMat4(String name, Object... data)
	{
		super(name);
		addUniform("");
	}

	@Override
	public void set(Object obj)
	{
		if(!(obj instanceof Mat4f)) throw new IllegalArgumentException();
		Mat4f m = (Mat4f)obj;
		mat.set(m);
	}

	@Override
	public void load()
	{
		loadMat4("", this.mat);
	}

}
