package com.codered.engine.shader;

import com.codered.engine.light.AmbientLight;
import com.codered.engine.window.IWindowContext;

public class UniformAmbientLight extends Uniform
{
	private AmbientLight ambient;
	
	public UniformAmbientLight(String name, IWindowContext context, ShaderProgram shader)
	{
		super(name, context, shader);
	}

	public void getUniformLocations()
	{
		addUniform(this.name + ".base.color");
		addUniform(this.name + ".base.intensity");
	}

	public void load()
	{
		loadColor3(this.name + ".base.color", this.ambient.base.color);
		loadFloat(this.name + ".base.intensity", this.ambient.base.intensity);
	}

	public void set(AmbientLight ambient)
	{
		this.ambient = ambient;
	}
}
