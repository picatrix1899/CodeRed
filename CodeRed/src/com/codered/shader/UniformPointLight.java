package com.codered.shader;

import com.codered.light.PointLight;

public class UniformPointLight extends Uniform
{

	public UniformPointLight(String name)
	{
		super(name);
		
		addUniform("base.color");
		addUniform("base.intensity");
		addUniform("position");
		addUniform("attenuation.constant");
		addUniform("attenuation.linear");
		addUniform("attenuation.exponent");
	}

	public void set(PointLight light)
	{
		loadColor3("base.color", light.base.color);
		loadFloat("base.intensity", light.base.intensity);
		loadVector3("position", light.position);
		loadFloat("attenuation.constant",light.attenuation.constant);
		loadFloat("attenuation.linear",light.attenuation.linear);
		loadFloat("attenuation.exponent",light.attenuation.exponent);
	}
}
