package com.codered.sh;

public class AmbientLightShader extends ShaderProgram
{
	public AmbientLightShader()
	{
		super();
		try
		{
			addUniform("T_model", UniformFactory.getInstance().createUniform("T_model", "mat4"));
			addUniform("T_projection", UniformFactory.getInstance().createUniform("T_projection", "mat4"));
			addUniform("camera.position", UniformFactory.getInstance().createUniform("camera.position", "vec3"));
			addUniform("camera.T_view", UniformFactory.getInstance().createUniform("camera.T_view", "mat4"));
			addUniform("material.albedoMap", UniformFactory.getInstance().createUniform("material.albedoMap", "sampler2D"));
			addUniform("ambientLight.base.color", UniformFactory.getInstance().createUniform("ambientLight.base.color", "vec3"));
			addUniform("ambientLight.base.color", UniformFactory.getInstance().createUniform("ambientLight.base.color", "vec3"));
			addUniform("ambientLight.base.color", UniformFactory.getInstance().createUniform("ambientLight.base.color", "vec3"));
		}
		catch(Exception e)
		{
			
		}
	}
}
