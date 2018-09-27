package com.codered.shader;

import com.codered.entities.Camera;

import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.Vector3f;

public class UniformCamera extends Uniform
{

	private Camera camera;
	private Matrix4f view;
	private Vector3f pos;
	
	public UniformCamera(String name)
	{
		super(name);
		
		addUniform("position");
		addUniform("T_view");
	}

	public void load()
	{
		if(camera != null)
		{
			this.view = camera.getViewMatrix();
			this.pos = camera.getTotalPos();
		}
		
		loadVector3("position", this.pos);
		loadMatrix("T_view", this.view);
	}

	public void set(Matrix4f view, Vector3f pos)
	{
		this.view = view;
		this.pos = pos;
	}
	
	public void set(Camera camera)
	{
		this.camera = camera;
	}
}
