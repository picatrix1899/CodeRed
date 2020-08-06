package com.codered.rendering.shader;

import java.nio.FloatBuffer;

import org.barghos.core.tuple3.api.Tup3fR;
import org.barghos.math.matrix.Mat4;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;
import org.lwjgl.system.MemoryUtil;

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
		
		return position;
	}
	
	public abstract void set(Object... obj);
	
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
	
	protected void loadVec3f(int uniform, Tup3fR t)
	{
		GL20.glUniform3f(uniform, t.getX(), t.getY(), t.getZ());
	}
	
	protected void loadVec3f(int uniform, float x, float y, float z)
	{
		GL20.glUniform3f(uniform, x, y, z);
	}
	
	protected void loadVec4f(int uniform, float x, float y, float z, float w)
	{
		GL20.glUniform4f(uniform, x, y, z, w);
	}
	
	protected void loadMat4(int uniform, Mat4 mat)
	{
		FloatBuffer buffer = MemoryUtil.memAllocFloat(Mat4.ROWS * Mat4.COLUMNS);
		mat.toBufferColumnMajor(buffer);

		GL20.glUniformMatrix4fv(uniform, false, buffer);
		
		MemoryUtil.memFree(buffer);
	}
	
	protected void loadTexture2D(int uniform, int attrib, int texturedId)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + attrib);
		GL11.glBindTexture(GL32.GL_TEXTURE_2D, texturedId);
		loadInt(uniform, attrib);
	}
}
