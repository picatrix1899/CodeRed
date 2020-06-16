package com.codered.demo;

import com.codered.rendering.shader.ShaderProgram;
import com.codered.rendering.shader.UniformFactory;

public class ShadowShader extends ShaderProgram
{
	public ShadowShader()
	{
		super();
		try
		{
			UniformFactory factory = UniformFactory.getInstance();
			addUniform(0, factory.createUniform("mvpMatrix", "mat4"));
			//addUniform(1, factory.createUniform("modelTexture", "sampler2D"));
			
			addAttribute(0, "in_position");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		addVertexShaderPart("res/shaders/shadowVertexShader.txt");
		addFragmentShaderPart("res/shaders/shadowFragmentShader.txt");
		compile();
	}
}