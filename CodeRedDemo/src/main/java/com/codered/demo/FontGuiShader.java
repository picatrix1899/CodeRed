package com.codered.demo;

import com.codered.rendering.shader.ShaderProgram;
import com.codered.rendering.shader.UniformFactory;

public class FontGuiShader extends ShaderProgram
{
	public FontGuiShader()
	{
		super();
		try
		{
			UniformFactory factory = UniformFactory.getInstance();
			addUniform(0, factory.createUniform("screenSpace", "vec2"));
			addUniform(1, factory.createUniform("color", "vec3", 0));
			addUniform(2, factory.createUniform("fontAtlas", "sampler2D", 0));
			
			addAttribute(0, "position");
			addAttribute(1, "textureCoords");
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
