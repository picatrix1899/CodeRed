package com.codered.resource.shaderpart;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ShaderPartLoader
{
	public static ShaderPartData loadResource(String file) throws Exception
	{
		StringBuilder shaderSource = new StringBuilder();
		
		String line = "";
		BufferedReader reader = new BufferedReader(new FileReader(new File(file)));

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
