package com.codered.demo;

import com.codered.sh.ShaderProgram;
import com.codered.sh.UniformFactory;

public class AmbientLightShader extends ShaderProgram
{
	public AmbientLightShader()
	{
		super();
		try
		{
			addUniform("T_model", UniformFactory.getInstance().createUniform("T_model", "mat4"));
			addUniform("T_projection", UniformFactory.getInstance().createUniform("T_projection", "mat4"));
			addUniform("camera.T_view", UniformFactory.getInstance().createUniform("camera.T_view", "mat4"));
			addUniform("material.albedoMap", UniformFactory.getInstance().createUniform("material.albedoMap", "sampler2D", 0));
			addUniform("ambientLight.base.color", UniformFactory.getInstance().createUniform("ambientLight.base.color", "vec3"));
			addUniform("ambientLight.base.intensity", UniformFactory.getInstance().createUniform("ambientLight.base.intensity", "float"));
			addAttribute(0, "vertexPos");
			addAttribute(1, "texCoords");
			addAttribute(2, "normal");
			addAttribute(3, "tangent");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
