package com.codered.shader;

import cmn.utilslib.math.matrix.Matrix4f;

public class UniformMatrix4x4 extends Uniform
{

	private Matrix4f matrix;
	
	public UniformMatrix4x4(String name)
	{
		super(name);
		
		addUniform(this.name);	
	}


	public void load()
	{
		loadMatrix(this.name, this.matrix);
	}

	public void set(Matrix4f matrix)
	{
		this.matrix = matrix;
	}
}
