package com.codered.engine.shader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.codered.engine.managing.loader.data.ShaderPartData;

public class ShaderPartLoader
{
	public static ShaderPartData readShader(InputStream stream, String shaderDst, Class<?> clazz)
	{
		StringBuilder shaderSource = new StringBuilder();
		
		String line = "";
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
					
					url = ShaderPart.class.getResource(dest);

					shaderSource.append(readShader(url.openStream(), shaderDst, clazz).getData());
				}
				else if(line.startsWith("#include embeded "))
				{
					dest = line.substring("#include embeded".length() + 2, line.length() - 1);
					dest = "/resources/shaders/" + dest;
					
					url = clazz.getResource(dest);
					
					shaderSource.append(readShader(url.openStream(), shaderDst, clazz));
				}
				else if(line.startsWith("#include "))
				{
					dest = line.substring("#include".length() + 2, line.length() - 1);

					
					dest = dest.replaceAll("\\\\","\\\\").replaceAll("/", "\\\\");
					if(dest.regionMatches(true, 1, ":\\\\", 0, ":\\\\".length()))
					{
						url = new File(dest).toURI().toURL();
					}
					else if(dest.startsWith("http://") || dest.startsWith("https://"))
					{
						url = new URL(dest);
					}
					else
					{
						dest = shaderDst + dest;
						
						url = new File(dest).toURI().toURL();
					}
					
					shaderSource.append(readShader(url.openStream(), shaderDst, clazz));
				}
				else
				{
					shaderSource.append(line).append("\n");					
				}
			}
			

		}
		catch (IOException e)
		{
			e.printStackTrace();
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

		return new ShaderPartData(shaderSource.toString());
	}
}
