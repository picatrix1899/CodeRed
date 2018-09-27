package com.codered.shaders.object.simple;

import com.codered.shader.UniformMaterial;
import com.codered.shaders.object.SimpleObjectShader;
import com.codered.window.WindowContext;

public abstract class TexturedObjectShader extends SimpleObjectShader
{
	public UniformMaterial u_material = new UniformMaterial("material", 0);
	
	public TexturedObjectShader(WindowContext context)
	{
		super(context);
		
		addUniform(u_material);
	}

}
