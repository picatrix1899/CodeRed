package com.codered.shaders.terrain;

import com.codered.entities.Camera;
import com.codered.shader.ShaderProgram;
import com.codered.window.WindowContext;

import cmn.utilslib.math.matrix.Matrix4f;

public abstract class SimpleTerrainShader extends ShaderProgram
{
	
	public SimpleTerrainShader(WindowContext context)
	{
		super(context);
	}

	protected void getAllUniformLocations()
	{
		addUniform("T_model");
		addUniform("T_projection");
		addUniform("camera.position");
		addUniform("camera.T_view");
	}
	
	public void loadModelMatrix(Matrix4f m) { setInput("T_model", m); }
	
	public void loadProjectionMatrix(Matrix4f m) { setInput("T_projection", m); }
	
	private void loadMatrices0(Matrix4f model, Matrix4f proj)
	{
		loadMatrix("T_model", model);
		loadMatrix("T_projection", proj);
	}
	
	private void loadCamera0(Camera c)
	{
		loadVector3("camera.position", c.getTotalPos());
		loadMatrix("camera.T_view", c.getViewMatrix());
	}
	
	public void loadCamera(Camera cam)
	{
		setInput("camera", cam);
	}
	
	public void use()
	{
		loadMatrices0((Matrix4f) getInput("T_model"), (Matrix4f) getInput("T_projection"));		
		loadCamera0((Camera) getInput("camera"));
	}
	
}
