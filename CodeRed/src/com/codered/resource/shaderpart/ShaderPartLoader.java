package com.codered.resource.shaderpart;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ShaderPartLoader
{
	public static ShaderPartData loadResource(InputStream stream) throws Exception
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

		return new ShaderPartData(shaderSource.toString());
	}
}
