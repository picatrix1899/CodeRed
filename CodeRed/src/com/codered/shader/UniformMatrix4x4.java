package com.codered.shader;

import com.codered.window.WindowContext;

import cmn.utilslib.math.matrix.Matrix4f;

public class UniformMatrix4x4 extends Uniform
{

	private Matrix4f matrix;
	
	public UniformMatrix4x4(String name, WindowContext context, ShaderProgram shader)
	{
		super(name, context, shader);
	}

	public void getUniformLocations()
	{
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
