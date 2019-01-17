package com.codered.shader;

import org.barghos.math.matrix.Mat4f;

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
	
	public void set(Mat4f matrix)
	{
		loadMatrix("", matrix);
	}
}
