package com.codered.shader;

import com.codered.light.AmbientLight;

public class UniformAmbientLight extends Uniform
{

	public UniformAmbientLight(String name)
	{
		super(name);
		
		addUniform("base.color");
		addUniform("base.intensity");
	}

	public void set(AmbientLight ambient)
	{
		loadColor3("base.color", ambient.base.color);
		loadFloat("base.intensity", ambient.base.intensity);
	}
}
