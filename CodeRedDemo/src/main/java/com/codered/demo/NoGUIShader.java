package com.codered.demo;

import com.codered.rendering.shader.ShaderProgram;
import com.codered.rendering.shader.UniformFactory;

public class NoGUIShader extends ShaderProgram
{
	public NoGUIShader()
	{
		super();
		try
		{
			UniformFactory factory = UniformFactory.getInstance();
			addUniform(0, factory.createUniform("screenSpace", "vec2"));
			addUniform(1, factory.createUniform("textureMap", "sampler2D", 0));
			
			addAttribute(0, "vertexPos");
			addAttribute(1, "texCoords");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		addFragmentShaderPart("res/shaders/gui_no.fs");
		addVertexShaderPart("res/shaders/gui_no.vs");
		compile();
	}
}
