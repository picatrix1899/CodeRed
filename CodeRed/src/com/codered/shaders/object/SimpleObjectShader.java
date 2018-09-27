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
		
		this.u_camera = new UniformCamera("camera");
		this.u_T_model = new UniformMatrix4x4("T_model");
		this.u_T_projection = new UniformMatrix4x4("T_projection");
		
		addUniform(u_camera);
		addUniform(u_T_model);
		addUniform(u_T_projection);
	}

	
	public void use()
	{	
		this.u_camera.load();
		this.u_T_model.load();
		this.u_T_projection.load();
	}
}
