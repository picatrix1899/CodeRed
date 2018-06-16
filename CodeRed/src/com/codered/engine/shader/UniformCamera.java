package com.codered.engine.shader;

import com.codered.engine.entities.Camera;
import com.codered.engine.window.WindowContext;

public class UniformCamera extends Uniform
{

	private Camera camera;
	
	public UniformCamera(String name, WindowContext context, ShaderProgram shader)
	{
		super(name, context, shader);
	}

	public void getUniformLocations()
	{
		addUniform(this.name + ".position");
		addUniform(this.name + ".T_view");
	}

	public void load()
	{
		loadVector3(this.name + ".position", camera.getTotalPos());
		loadMatrix(this.name + ".T_view", camera.getViewMatrix());
	}

	public void set(Camera camera)
	{
		this.camera = camera;
	}
}