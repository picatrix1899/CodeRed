package com.codered.engine.managing.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import com.codered.engine.managing.Paths;
import com.codered.engine.managing.loader.data.ShaderPartData;
import com.codered.engine.shader.ShaderPart;

public class ShaderPartLoader
{
	public static ShaderPartData readShader(InputStream stream, Class<?> clazz)
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

					shaderSource.append(readShader(url.openStream(), clazz).getData());
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
