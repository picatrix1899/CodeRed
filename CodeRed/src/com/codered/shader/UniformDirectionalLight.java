package com.codered.shader;

import com.codered.light.DirectionalLight;
import com.codered.window.WindowContext;

public class UniformDirectionalLight extends Uniform
{

	private DirectionalLight light;
	
	public UniformDirectionalLight(String name, WindowContext context, ShaderProgram shader)
	{
		super(name, context, shader);
		
		addUniform(this.name + ".base.color");
		addUniform(this.name + ".base.intensity");
		addUniform(this.name + ".direction");
	}

	public void load()
	{
		loadColor3(this.name + ".base.color", this.light.base.color);
		loadFloat(this.name + ".base.intensity", this.light.base.intensity);
		loadVector3(this.name + ".direction", this.light.direction);
	}

	public void set(DirectionalLight light)
	{
		this.light = light;
	}
	
}
