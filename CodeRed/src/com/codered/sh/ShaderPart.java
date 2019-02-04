package com.codered.sh;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.codered.shader.MalformedShaderException;

public class ShaderPart
{
	private int id;
	private String name;
	
	public ShaderPart(String name, int type, String data)
	{
		this.id = GL20.glCreateShader(type);
		this.name = name;
		
		GL20.glShaderSource(this.id, data);
		
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
				System.out.println(data);
			}
		}
	}
	
	public int getId()
	{
		return id;
	}
	
	public String getName()
	{
		return this.name;
	}
	
	public void release()
	{
		GL20.glDeleteShader(this.id);
	}
}
