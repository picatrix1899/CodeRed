package com.codered.shaders.object.simple;

import com.codered.shader.UniformMaterial;
import com.codered.shaders.object.SimpleObjectShader;
import com.codered.window.WindowContext;

public abstract class TexturedObjectShader extends SimpleObjectShader
{
	public UniformMaterial u_material;
	
	public TexturedObjectShader(WindowContext context)
	{
		super(context);
		
		this.u_material = new UniformMaterial("material", 0);
		
		addUniform(u_material);
	}
	
	
	public void use()
	{
		super.use();
		
		this.u_material.load();
	}
}
