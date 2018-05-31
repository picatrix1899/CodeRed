package com.codered.engine.shader;

import java.nio.FloatBuffer;
import java.util.HashMap;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import com.codered.engine.managing.Texture;
import com.codered.engine.window.WindowContext;
import com.google.common.collect.Maps;

import cmn.utilslib.color.colors.api.IColor3Base;
import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.api.Vec2fBase;
import cmn.utilslib.math.vector.api.Vec3fBase;
import cmn.utilslib.math.vector.api.Vec4fBase;

public abstract class Uniform
{
	private FloatBuffer matrixBuffer = BufferUtils.createFloatBuffer(16);
	
	protected String name;
	protected WindowContext context;
	
	protected ShaderProgram shader;
	
	private HashMap<String,Integer> uniforms = Maps.newHashMap();
	
	public Uniform(String name, WindowContext context, ShaderProgram shader)
	{
		this.name = name;
		this.context = context;
		this.shader = shader;
	}
	
	public abstract void getUniformLocations();
	
	public abstract void load();
	
	protected void addUniform(String uniform)
	{
		this.uniforms.put(uniform, GL20.glGetUniformLocation(this.shader.programID, uniform));
	}
	
	protected void loadFloat(int location, float val)
	{
		GL20.glUniform1f(location, val);
	}
	
	protected void loadColor3(int location, IColor3Base val)
	{
		GL20.glUniform3f(location, val.getUnityR(), val.getUnityG(), val.getUnityB());
	}
	
	protected void loadVector2(int location, Vec2fBase val)
	{
		GL20.glUniform2f(location,val.getX(), val.getY());
	}
	
	protected void loadVector3(int location, Vec3fBase val)
	{
		GL20.glUniform3f(location, val.getX(), val.getY(), val.getZ());
	}
	
	protected void loadBoolean(int location, boolean val)
	{
		loadFloat(location, val ? 1 : 0);
	}
	
	protected void loadInt(int location, int val)
	{
		GL20.glUniform1i(location, val);
	}
	
	protected void loadMatrix(int location, Matrix4f val)
	{
		matrixBuffer.put(val.getColMajor());
		
		matrixBuffer.flip();
		
		GL20.glUniformMatrix4fv(location, false, matrixBuffer);
		
		matrixBuffer = BufferUtils.createFloatBuffer(16);
	}
	
	protected void loadFloat(String uniform, float val)
	{
		GL20.glUniform1f(this.uniforms.get(uniform), val);
	}
	
	protected void loadVector2(String uniform, Vec2fBase val)
	{
		GL20.glUniform2f(this.uniforms.get(uniform),val.getX(), val.getY());
	}
	
	protected void loadColor3(String uniform, IColor3Base val)
	{
		GL20.glUniform3f(this.uniforms.get(uniform), val.getUnityR(), val.getUnityG(), val.getUnityB());
	}
	
	protected void loadVector3(String uniform, Vec3fBase val)
	{
		GL20.glUniform3f(this.uniforms.get(uniform), val.getX(), val.getY(), val.getZ());
	}
	
	protected void loadVector4(String uniform, Vec4fBase val)
	{
		GL20.glUniform4f(this.uniforms.get(uniform), val.getX(), val.getY(), val.getZ(), val.getA());
	}
	
	protected void loadBoolean(String uniform, boolean val)
	{
		loadFloat(this.uniforms.get(uniform), val ? 1 : 0);
	}
	
	protected void loadInt(String uniform, int val)
	{
		GL20.glUniform1i(this.uniforms.get(uniform), val);
	}
	
	protected void loadMatrix(String uniform, Matrix4f val)
	{
		matrixBuffer.put(val.getColMajor());
		
		matrixBuffer.flip();
		
		GL20.glUniformMatrix4fv(this.uniforms.get(uniform), false, matrixBuffer);
		
		matrixBuffer = BufferUtils.createFloatBuffer(16);
	}
	
	protected void loadTextureId(String uniform, int attrib, int texture)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + attrib);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		loadInt(uniform, attrib);
	}
	
	protected void loadTexture(String uniform, int attrib, Texture texture)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + attrib);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());
		loadInt(uniform, attrib);
	}
	
	protected void loadTextureMSId(String uniform, int attrib, int texture)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + attrib);
		GL11.glBindTexture(GL32.GL_TEXTURE_2D_MULTISAMPLE, texture);
		loadInt(uniform, attrib);
	}
	
	protected void loadTextureMS(String uniform, int attrib, Texture texture)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + attrib);
		GL11.glBindTexture(GL32.GL_TEXTURE_2D_MULTISAMPLE, texture.getId());
		loadInt(uniform, attrib);
	}
}
