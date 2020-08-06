package com.codered.demo;

import com.codered.rendering.shader.ShaderProgram;
import com.codered.rendering.shader.UniformFactory;

public class ColoredShader extends ShaderProgram
{
	public ColoredShader()
	{
		super();
		try
		{
			UniformFactory factory = UniformFactory.getInstance();
			addUniform(0, factory.createUniform("T_model", "mat4"));
			addUniform(1, factory.createUniform("T_projection", "mat4"));
			addUniform(2, factory.createUniform("T_view", "mat4"));
			addUniform(3, factory.createUniform("color", "vec3"));
			
			addAttribute(0, "vertexPos");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		addVertexShaderPart("res/shaders/o_colored.vs");
		addFragmentShaderPart("res/shaders/o_colored.fs");
		compile();
	}
}
