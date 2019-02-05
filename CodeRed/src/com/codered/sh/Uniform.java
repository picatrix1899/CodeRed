package com.codered.sh;

import java.nio.FloatBuffer;
import java.util.Map;

import org.barghos.core.tuple.Tup4f;
import org.barghos.math.matrix.Mat4f;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import com.google.common.base.Strings;
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
		this.uniforms.put(getUniformName(id), 0);
		System.out.println("added Uniform: " + getUniformName(id));
	}
	
	public void loadUniformLocations(int shaderProgrammId)
	{
		for(String uniform : this.uniforms.keySet())
		{
			this.uniforms.put(uniform, GL20.glGetUniformLocation(shaderProgrammId, uniform));
			System.out.println("loaded Uniform: " + uniform + " " + GL20.glGetUniformLocation(shaderProgrammId, uniform));
		}

	}
	
	protected int getUniform(String name)
	{
		return this.uniforms.get(getUniformName(name));
	}
	
	private String getUniformName(String name)
	{
		return Strings.isNullOrEmpty(name) ? this.name : this.name + "." + name;
	}
	
	public abstract void set(Object obj);
	
	public abstract void load();
	
	protected void loadInt(String uniform, int value)
	{
		if(!this.uniforms.containsKey(getUniformName(uniform))) throw new IllegalArgumentException();
		
		GL20.glUniform1i(this.uniforms.get(getUniformName(uniform)), value);
	}
	
	protected void loadFloat(String uniform, float value)
	{
		if(!this.uniforms.containsKey(getUniformName(uniform))) throw new IllegalArgumentException();
		
		GL20.glUniform1f(this.uniforms.get(getUniformName(uniform)), value);
	}
	
	protected void loadVec2f(String uniform, float x, float y)
	{
		if(!this.uniforms.containsKey(getUniformName(uniform))) throw new IllegalArgumentException();
		
		GL20.glUniform2f(this.uniforms.get(getUniformName(uniform)), x, y);
	}
	
	protected void loadVec3f(String uniform, float x, float y, float z)
	{
		if(!this.uniforms.containsKey(getUniformName(uniform))) throw new IllegalArgumentException();
		
		GL20.glUniform3f(this.uniforms.get(getUniformName(uniform)), x, y, z);
	}
	
	protected void loadVec4f(String uniform, float x, float y, float z, float w)
	{
		if(!this.uniforms.containsKey(getUniformName(uniform))) throw new IllegalArgumentException();
		
		GL20.glUniform4f(this.uniforms.get(getUniformName(uniform)), x, y, z, w);
	}
	
	protected void loadMat4(String uniform, Mat4f mat)
	{
		if(!this.uniforms.containsKey(getUniformName(uniform))) throw new IllegalArgumentException();
		
		FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
		
		Tup4f c0 = mat.getColumn(0);
		matrixBuffer.put(c0.getX());
		matrixBuffer.put(c0.getY());
		matrixBuffer.put(c0.getZ());
		matrixBuffer.put(c0.getW());
		
		Tup4f c1 = mat.getColumn(1);
		matrixBuffer.put(c1.getX());
		matrixBuffer.put(c1.getY());
		matrixBuffer.put(c1.getZ());
		matrixBuffer.put(c1.getW());
		
		Tup4f c2 = mat.getColumn(2);
		matrixBuffer.put(c2.getX());
		matrixBuffer.put(c2.getY());
		matrixBuffer.put(c2.getZ());
		matrixBuffer.put(c2.getW());
		
		Tup4f c3 = mat.getColumn(3);
		matrixBuffer.put(c3.getX());
		matrixBuffer.put(c3.getY());
		matrixBuffer.put(c3.getZ());
		matrixBuffer.put(c3.getW());
		
		matrixBuffer.flip();
		
		
		
		GL20.glUniformMatrix4fv(this.uniforms.get(getUniformName(uniform)), false, matrixBuffer);
	}
	
	protected void loadTexture2D(String uniform, int attrib, int texturedId)
	{
		if(!this.uniforms.containsKey(getUniformName(uniform))) throw new IllegalArgumentException();
		
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + attrib);
		GL11.glBindTexture(GL32.GL_TEXTURE_2D, texturedId);
		loadInt(uniform, attrib);
	}
}
