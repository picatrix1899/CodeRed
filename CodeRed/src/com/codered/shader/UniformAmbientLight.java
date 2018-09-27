package com.codered.shader;

import com.codered.light.AmbientLight;

public class UniformAmbientLight extends Uniform
{
	private AmbientLight ambient;
	
	public UniformAmbientLight(String name)
	{
		super(name);
		
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
