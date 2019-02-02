package com.codered.sh;

import java.util.Map;

import org.lwjgl.opengl.GL20;

import com.google.common.collect.Maps;

public abstract class Uniform
{
	
	protected Map<String, Integer> uniforms = Maps.newHashMap();
	protected String name;
	
	public Uniform(String name)
	{
		this.name = name;
	}
	
	public void addUniform(String id)
	{
		this.uniforms.put(this.name + id, 0);
	}
	
	public void loadUniformLocations(int shaderProgrammId)
	{
		for(String uniform : this.uniforms.keySet())
			this.uniforms.put(uniform, GL20.glGetUniformLocation(shaderProgrammId, uniform));
	}
	
	protected int getUniform(String name)
	{
		return this.uniforms.get(this.name + name);
	}
	
	public abstract void set(Object obj);
	
	public abstract void load();
	
	protected void loadVec2f(String uniform, float x, float y)
	{
		if(!this.uniforms.containsKey(this.name + uniform)) throw new IllegalArgumentException();
		
		GL20.glUniform2f(this.uniforms.get(uniform), x, y);
	}
	
	protected void loadVec3f(String uniform, float x, float y, float z)
	{
		if(!this.uniforms.containsKey(this.name + uniform)) throw new IllegalArgumentException();
		
		GL20.glUniform3f(this.uniforms.get(uniform), x, y, z);
	}
	
	protected void loadVec4f(String uniform, float x, float y, float z, float w)
	{
		if(!this.uniforms.containsKey(this.name + uniform)) throw new IllegalArgumentException();
		
		GL20.glUniform4f(this.uniforms.get(uniform), x, y, z, w);
	}
}
