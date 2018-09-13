package com.codered.shader;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.codered.managing.loader.data.ShaderPartData;

public class ShaderPart
{
	private int id;
	
	public ShaderPart()
	{
	}
	
	public int getId()
	{
		return this.id;
	}
	
	public ShaderPart loadShader(String name, ShaderPartData data, int type) throws MalformedShaderException
	{
		this.id = GL20.glCreateShader(type);
		
		GL20.glShaderSource(this.id, data.getData());
		
		GL20.glCompileShader(this.id);
		
		if(GL20.glGetShaderi(this.id,GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			try
			{
				throw new MalformedShaderException(name + " " + GL20.glGetShaderInfoLog(this.id, 500));
			}
			catch(Exception e)
			{
				e.printStackTrace();
				System.out.println(data.getData());
			}
		}
		
		return this;
	}
	
	public void clear()
	{
		GL20.glDeleteShader(this.id);
	}
	

}
