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
			UniformFactory factory = UniformFactory.getInstance();
			addUniform("T_model", factory.createUniform("T_model", "mat4"));
			addUniform("T_projection", factory.createUniform("T_projection", "mat4"));
			addUniform("camera.T_view", factory.createUniform("camera.T_view", "mat4"));
			addUniform("material.albedoMap", factory.createUniform("material.albedoMap", "sampler2D", 0));
			addUniform("ambientLight.base.color", factory.createUniform("ambientLight.base.color", "vec3"));
			addUniform("ambientLight.base.intensity", factory.createUniform("ambientLight.base.intensity", "float"));
			
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
