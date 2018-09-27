package com.codered.shader;

import cmn.utilslib.math.matrix.Matrix4f;

public class UniformMatrix4x4 extends Uniform
{

	public UniformMatrix4x4(String name)
	{
		super(name);
		
		addUniform("");	
	}

	public void set(Matrix4f matrix)
	{
		loadMatrix("", matrix);
	}
}
