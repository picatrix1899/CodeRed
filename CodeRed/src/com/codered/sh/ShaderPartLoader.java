package com.codered.sh;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.codered.managing.loader.data.ShaderPartData;

public class ShaderPartLoader
{
	public static ShaderPartData readShader(InputStream stream)
	{
		StringBuilder shaderSource = new StringBuilder();
		
		String line = "";
		BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

		try
		{
			while((line = reader.readLine()) != null)
			{
					shaderSource.append(line).append("\n");					
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
