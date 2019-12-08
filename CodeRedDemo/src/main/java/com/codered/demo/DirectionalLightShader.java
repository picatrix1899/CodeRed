package com.codered.demo;

import com.codered.rendering.shader.ShaderProgram;
import com.codered.rendering.shader.UniformFactory;

public class DirectionalLightShader extends ShaderProgram
{
	public DirectionalLightShader()
	{
		super();
		try
		{
			UniformFactory factory = UniformFactory.getInstance();
			addUniform(0, factory.createUniform("T_model", "mat4"));
			addUniform(1, factory.createUniform("T_projection", "mat4"));
			addUniform(2, factory.createUniform("camera", "camera"));
			addUniform(3, factory.createUniform("material", "material", 0, 1));
			addUniform(4, factory.createUniform("directionalLight.base.color", "vec3"));
			addUniform(5, factory.createUniform("directionalLight.base.intensity", "float"));
			addUniform(6, factory.createUniform("directionalLight.direction", "vec3"));
			
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
