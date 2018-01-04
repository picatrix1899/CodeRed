package com.codered.engine.shaders.shader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

import com.codered.engine.managing.Paths;

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
	
	public ShaderPart loadShader(URL url, Class<?> clazz, int type) throws ShaderNotFoundException, MalformedShaderException
	{
		this.id = GL20.glCreateShader(type);
		
		try
		{
			GL20.glShaderSource(this.id, readShader(url.openStream(), clazz));
		}
		catch (IOException e)
		{
			throw new ShaderNotFoundException(url);
		}
		
		GL20.glCompileShader(this.id);
		
		if(GL20.glGetShaderi(this.id,GL20.GL_COMPILE_STATUS) == GL11.GL_FALSE)
		{
			throw new MalformedShaderException(url, GL20.glGetShaderInfoLog(this.id, 500));
		}
		
		return this;
	}
	
	public void clear()
	{
		GL20.glDeleteShader(this.id);
	}
	
	private String readShader(InputStream stream, Class<?> clazz) throws ShaderNotFoundException, MalformedShaderException
	{
		StringBuilder shaderSource = new StringBuilder();
		
		String line = "";
		String type = "";
		String dest = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
		
		URL url;

		try
		{
			while((line = reader.readLine()) != null)
			{
				
				if(line.startsWith("#include embeded base "))
				{
					dest = line.substring("#include embeded base".length() + 2, line.length() - 1);
					dest = "/resources/shaders/" + dest;
					type = "embeded";
					
					url = ShaderPart.class.getResource(dest);

					shaderSource.append(readShader(url.openStream(), clazz));
				}
				else if(line.startsWith("#include embeded "))
				{
					dest = line.substring("#include embeded".length() + 2, line.length() - 1);
					dest = "/resources/shaders/" + dest;
					type = "embeded";
					
					url = clazz.getResource(dest);
					
					shaderSource.append(readShader(url.openStream(), clazz));
				}
				else if(line.startsWith("#include "))
				{
					dest = line.substring("#include".length() + 2, line.length() - 1);

					
					dest = dest.replaceAll("\\\\","\\\\").replaceAll("/", "\\\\");
					if(dest.regionMatches(true, 1, ":\\\\", 0, ":\\\\".length()))
					{
						type = "fullpath";
						url = new File(dest).toURI().toURL();
					}
					else if(dest.startsWith("http://") || dest.startsWith("https://"))
					{
						type = "url";
						url = new URL(dest);
					}
					else
					{
						dest = Paths.p_shaders + dest;
						
						type = "relative";
						url = new File(dest).toURI().toURL();
					}
					
					shaderSource.append(readShader(url.openStream(), clazz));
				}
				else
				{
					shaderSource.append(line).append("\n");					
				}
			}
			

		}
		catch (IOException e)
		{
			throw new ShaderNotFoundException(type + " : " + dest);
		}
		finally
		{
			try
			{
				reader.close();
				stream.close();	
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}

		return shaderSource.toString();
	}
}
