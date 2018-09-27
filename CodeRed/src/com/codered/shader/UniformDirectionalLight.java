package com.codered.shader;

import com.codered.light.DirectionalLight;

public class UniformDirectionalLight extends Uniform
{

	private DirectionalLight light;
	
	public UniformDirectionalLight(String name)
	{
		super(name);
		
		addUniform("base.color");
		addUniform("base.intensity");
		addUniform("direction");
	}

	public void load()
	{
		loadColor3("base.color", this.light.base.color);
		loadFloat("base.intensity", this.light.base.intensity);
		loadVector3("direction", this.light.direction);
	}

	public void set(DirectionalLight light)
	{
		this.light = light;
	}
	
}
