package com.codered.engine.shader;

import java.util.HashMap;

public class ShaderList
{
	private HashMap<Class<? extends ShaderProgram>, ShaderProgram> shaders = new HashMap<Class<? extends ShaderProgram>, ShaderProgram>();

	public void addShader(Class<? extends ShaderProgram> clazz, ShaderProgram shader)
	{
		this.shaders.put(clazz, shader);
	}
	
	@SuppressWarnings("unchecked")
	public <A extends ShaderProgram> A getShader(Class<A> clazz)
	{
		return (A)this.shaders.get(clazz);
	}
}
