package com.codered.sh;

import java.util.Map;

import org.barghos.math.matrix.Mat4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import com.google.common.collect.Maps;

import cmn.utilslib.essentials.BufferUtils;

public abstract class Uniform
{
	
	protected Map<String, Integer> uniforms = Maps.newHashMap();
	protected String name;
	
	public Uniform(String name, Object... data)
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
	
	protected void loadInt(String uniform, int value)
	{
		if(!this.uniforms.containsKey(this.name + uniform)) throw new IllegalArgumentException();
		
		GL20.glUniform1i(this.uniforms.get(this.name + uniform), value);
	}
	
	protected void loadFloat(String uniform, float value)
	{
		if(!this.uniforms.containsKey(this.name + uniform)) throw new IllegalArgumentException();
		
		GL20.glUniform1f(this.uniforms.get(this.name + uniform), value);
	}
	
	protected void loadVec2f(String uniform, float x, float y)
	{
		if(!this.uniforms.containsKey(this.name + uniform)) throw new IllegalArgumentException();
		
		GL20.glUniform2f(this.uniforms.get(this.name + uniform), x, y);
	}
	
	protected void loadVec3f(String uniform, float x, float y, float z)
	{
		if(!this.uniforms.containsKey(this.name + uniform)) throw new IllegalArgumentException();
		
		GL20.glUniform3f(this.uniforms.get(this.name + uniform), x, y, z);
	}
	
	protected void loadVec4f(String uniform, float x, float y, float z, float w)
	{
		if(!this.uniforms.containsKey(this.name + uniform)) throw new IllegalArgumentException();
		
		GL20.glUniform4f(this.uniforms.get(this.name + uniform), x, y, z, w);
	}
	
	protected void loadMat4(String uniform, Mat4f mat)
	{
		if(!this.uniforms.containsKey(this.name + uniform)) throw new IllegalArgumentException();
		
		GL20.glUniformMatrix4fv(this.uniforms.get(this.name + uniform), false, BufferUtils.wrapFlippedFloatBuffer(mat.toArrayColumnMajor()));
	}
	
	protected void loadTexture2D(String uniform, int attrib, int texturedId)
	{
		if(!this.uniforms.containsKey(this.name + uniform)) throw new IllegalArgumentException();
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + attrib);
		GL11.glBindTexture(GL32.GL_TEXTURE_2D, texturedId);
		loadInt(uniform, attrib);
	}
}
