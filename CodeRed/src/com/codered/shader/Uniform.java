package com.codered.shader;

import org.barghos.math.matrix.Mat4f;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

public abstract class Uniform
{
	protected String name;
	
	public Uniform(String name, Object... data)
	{
		this.name = name;
	}
	
	public abstract void loadUniformLocations(int shaderProgrammId);
	
	public int getLocationFor(String uniform, int shaderProgrammId)
	{
		int position = GL20.glGetUniformLocation(shaderProgrammId, uniform);
		
		System.out.println("program: " + shaderProgrammId + " uniform: " + uniform + " location: " + position);
		
		return position;
	}
	
	public abstract void set(Object... obj);
	
	public abstract void load();
	
	protected void loadInt(int uniform, int value)
	{
		GL20.glUniform1i(uniform, value);
	}
	
	protected void loadFloat(int uniform, float value)
	{
		GL20.glUniform1f(uniform, value);
	}
	
	protected void loadVec2f(int uniform, float x, float y)
	{
		GL20.glUniform2f(uniform, x, y);
	}
	
	protected void loadVec3f(int uniform, float x, float y, float z)
	{
		GL20.glUniform3f(uniform, x, y, z);
	}
	
	protected void loadVec4f(int uniform, float x, float y, float z, float w)
	{
		GL20.glUniform4f(uniform, x, y, z, w);
	}
	
	protected void loadMat4(int uniform, Mat4f mat)
	{
		GL20.glUniformMatrix4fv(uniform, false, mat.toArrayColumnMajor());
	}
	
	protected void loadTexture2D(int uniform, int attrib, int texturedId)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + attrib);
		GL11.glBindTexture(GL32.GL_TEXTURE_2D, texturedId);
		loadInt(uniform, attrib);
	}
}
