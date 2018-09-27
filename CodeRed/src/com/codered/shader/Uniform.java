package com.codered.shader;

import java.util.HashMap;

import org.barghos.core.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

import com.codered.texture.Texture;
import com.google.common.collect.Maps;

import cmn.utilslib.color.colors.api.IColor3Base;
import cmn.utilslib.math.matrix.Matrix4f;
import cmn.utilslib.math.vector.api.Vec2fBase;
import cmn.utilslib.math.vector.api.Vec3fBase;
import cmn.utilslib.math.vector.api.Vec4fBase;

public abstract class Uniform
{
	protected String name;
	
	private HashMap<String,Integer> uniforms = Maps.newHashMap();
	
	public Uniform(String name)
	{
		this.name = name;
	}
	
	public void getUniformLocations(ShaderProgram shader)
	{
		for(String uniform : this.uniforms.keySet())
		{
			this.uniforms.put(uniform, GL20.glGetUniformLocation(shader.programID, uniform));
		}
	}
	
	public abstract void load();
	
	protected void addUniform(String uniform)
	{
		this.uniforms.put(uniform, 0);
	}
	
	protected void loadTextureId(int location, int attrib, int texture)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + attrib);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture);
		loadInt(location, attrib);
	}
	
	protected void loadTexture(int location, int attrib, Texture texture)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + attrib);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, texture.getId());
		loadInt(location, attrib);
	}
	
	protected void loadTextureMSId(int location, int attrib, int texture)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + attrib);
		GL11.glBindTexture(GL32.GL_TEXTURE_2D_MULTISAMPLE, texture);
		loadInt(location, attrib);
	}
	
	protected void loadTextureMS(int location, int attrib, Texture texture)
	{
		GL13.glActiveTexture(GL13.GL_TEXTURE0 + attrib);
		GL11.glBindTexture(GL32.GL_TEXTURE_2D_MULTISAMPLE, texture.getId());
		loadInt(location, attrib);
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
	
	protected void loadVector4(int location, Vec4fBase val)
	{
		GL20.glUniform4f(location, val.getX(), val.getY(), val.getZ(), val.getA());
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
		GL20.glUniformMatrix4fv(location, false, BufferUtils.wrapFlippedFloatBuffer(val.getColMajor()));
	}
	
	protected void loadFloat(String uniform, float val) { loadFloat(this.uniforms.get(uniform), val); }
	
	protected void loadVector2(String uniform, Vec2fBase val) { loadVector2(this.uniforms.get(uniform),val); }
	
	protected void loadColor3(String uniform, IColor3Base val) { loadColor3(this.uniforms.get(uniform), val); }
	
	protected void loadVector3(String uniform, Vec3fBase val) { loadVector3(this.uniforms.get(uniform), val); }
	
	protected void loadVector4(String uniform, Vec4fBase val) { loadVector4(this.uniforms.get(uniform), val); }
	
	protected void loadBoolean(String uniform, boolean val) { loadBoolean(this.uniforms.get(uniform), val); }
	
	protected void loadInt(String uniform, int val) { loadInt(this.uniforms.get(uniform), val); }
	
	protected void loadMatrix(String uniform, Matrix4f val) { loadMatrix(this.uniforms.get(uniform), val); }
	
	protected void loadTextureId(String uniform, int attrib, int texture) { loadTextureId(this.uniforms.get(uniform), attrib, texture); }
	
	protected void loadTexture(String uniform, int attrib, Texture texture) { loadTexture(this.uniforms.get(uniform), attrib, texture); }
	
	protected void loadTextureMSId(String uniform, int attrib, int texture) { loadTextureMSId(this.uniforms.get(uniform), attrib, texture); }
	
	protected void loadTextureMS(String uniform, int attrib, Texture texture) { loadTextureMS(this.uniforms.get(uniform), attrib, texture); }
}
