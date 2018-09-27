package com.codered.shader;

import com.codered.light.AmbientLight;

public class UniformAmbientLight extends Uniform
{
	private AmbientLight ambient;
	
	public UniformAmbientLight(String name)
	{
		super(name);
		
		addUniform("base.color");
		addUniform("base.intensity");
	}

	public void load()
	{
		loadColor3("base.color", this.ambient.base.color);
		loadFloat("base.intensity", this.ambient.base.intensity);
	}

	public void set(AmbientLight ambient)
	{
		this.ambient = ambient;
	}
}
