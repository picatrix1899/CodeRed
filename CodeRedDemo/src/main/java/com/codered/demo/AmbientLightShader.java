package com.codered.demo;

import com.codered.rendering.shader.ShaderProgram;
import com.codered.rendering.shader.UniformFactory;

public class AmbientLightShader extends ShaderProgram
{
	public AmbientLightShader()
	{
		super();
		try
		{
			UniformFactory factory = UniformFactory.getInstance();
			addUniform(0, factory.createUniform("T_model", "mat4"));
			addUniform(1, factory.createUniform("T_projection", "mat4"));
			addUniform(2, factory.createUniform("camera", "camera"));
			addUniform(3, factory.createUniform("material", "material", 0, 1));
			addUniform(4, factory.createUniform("ambientLight.base.color", "vec3"));
			addUniform(5, factory.createUniform("ambientLight.base.intensity", "float"));
			
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
