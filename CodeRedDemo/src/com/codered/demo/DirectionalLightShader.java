package com.codered.demo;

import com.codered.sh.ShaderProgram;
import com.codered.sh.UniformFactory;

public class DirectionalLightShader extends ShaderProgram
{
	public DirectionalLightShader()
	{
		super();
		try
		{
			UniformFactory factory = UniformFactory.getInstance();
			addUniform("T_model", factory.createUniform("T_model", "mat4"));
			addUniform("T_projection", factory.createUniform("T_projection", "mat4"));
			addUniform("camera.T_view", factory.createUniform("camera.T_view", "mat4"));
			addUniform("camera.position", factory.createUniform("camera.position", "vec3"));
			addUniform("material.albedoMap", factory.createUniform("material.albedoMap", "sampler2D", 0));
			addUniform("material.normalMap", factory.createUniform("material.normalMap", "sampler2D", 1));
			addUniform("material.specularIntensity", factory.createUniform("material.specularIntensity", "float"));
			addUniform("material.specularPower", factory.createUniform("material.specularPower", "float"));
			addUniform("directionalLight.base.color", factory.createUniform("directionalLight.base.color", "vec3"));
			addUniform("directionalLight.base.intensity", factory.createUniform("directionalLight.base.intensity", "float"));
			addUniform("directionalLight.direction", factory.createUniform("directionalLight.direction", "vec3"));
			
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
