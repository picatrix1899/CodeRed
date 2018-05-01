package com.codered.engine.shaders.postprocess.filter;

import com.codered.engine.shader.ShaderProgram;
import com.codered.engine.window.IWindowContext;

public abstract class PPFShader extends ShaderProgram
{

	public PPFShader(IWindowContext context)
	{
		super(context);
	}
	
}
