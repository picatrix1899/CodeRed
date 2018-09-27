package com.codered.shader;

import com.codered.entities.Camera;

import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.Vector3f;

public class UniformCamera extends Uniform
{

	public UniformCamera(String name)
	{
		super(name);
		
		addUniform("position");
		addUniform("T_view");
	}

	public void set(Matrix4f view, Vector3f pos)
	{
		loadVector3("position", pos);
		loadMatrix("T_view", view);
	}
	
	public void set(Camera camera)
	{
		loadVector3("position", camera.getTotalPos());
		loadMatrix("T_view", camera.getViewMatrix());
	}
}
