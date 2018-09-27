package com.codered.shader;

import com.codered.light.DirectionalLight;

public class UniformDirectionalLight extends Uniform
{

	public UniformDirectionalLight(String name)
	{
		super(name);
		
		addUniform("base.color");
		addUniform("base.intensity");
		addUniform("direction");
	}

	public void set(DirectionalLight light)
	{
		loadColor3("base.color", light.base.color);
		loadFloat("base.intensity", light.base.intensity);
		loadVector3("direction", light.direction);
	}
	
}
