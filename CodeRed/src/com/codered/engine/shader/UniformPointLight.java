package com.codered.engine.shader;

import com.codered.engine.light.PointLight;
import com.codered.engine.window.IWindowContext;

public class UniformPointLight extends Uniform
{

	private PointLight light;
	
	public UniformPointLight(String name, IWindowContext context, ShaderProgram shader)
	{
		super(name, context, shader);
	}

	public void getUniformLocations()
	{
		addUniform(this.name + ".base.color");
		addUniform(this.name + ".base.intensity");
		addUniform(this.name + ".position");
		addUniform(this.name + ".attenuation.constant");
		addUniform(this.name + ".attenuation.linear");
		addUniform(this.name + ".attenuation.exponent");
	}

	public void load()
	{
		loadColor3(this.name + ".base.color", light.base.color);
		loadFloat(this.name + ".base.intensity", light.base.intensity);
		loadVector3(this.name + ".position", light.position);
		loadFloat(this.name + ".attenuation.constant",light.attenuation.constant);
		loadFloat(this.name + ".attenuation.linear",light.attenuation.linear);
		loadFloat(this.name + ".attenuation.exponent",light.attenuation.exponent);
	}

	public void set(PointLight light)
	{
		this.light = light;
	}
}
