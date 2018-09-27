package com.codered.shaders.object;

import com.codered.shader.ShaderProgram;
import com.codered.shader.UniformCamera;
import com.codered.shader.UniformMatrix4x4;
import com.codered.window.WindowContext;

public abstract class SimpleObjectShader extends ShaderProgram
{

	public UniformCamera u_camera;
	public UniformMatrix4x4 u_T_model;
	public UniformMatrix4x4 u_T_projection;
	
	public SimpleObjectShader(WindowContext context)
	{
		super(context);
		
		this.u_camera = new UniformCamera("camera", context, this);
		this.u_T_model = new UniformMatrix4x4("T_model", context, this);
		this.u_T_projection = new UniformMatrix4x4("T_projection", context, this);
	}

	protected void getAllUniformLocations()
	{
		this.u_T_model.getUniformLocations(this);
		this.u_T_projection.getUniformLocations(this);
		this.u_camera.getUniformLocations(this);
	}
	
	public void use()
	{	
		this.u_camera.load();
		this.u_T_model.load();
		this.u_T_projection.load();
	}
}
