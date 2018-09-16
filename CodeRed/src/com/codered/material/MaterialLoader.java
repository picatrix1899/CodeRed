package com.codered.material;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.codered.managing.loader.data.MaterialData;

public class MaterialLoader
{
	public static MaterialData load(String fileName)
	{
		return load(new File(fileName));
	}
	
	public static MaterialData load(File f)
	{
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(f));
				
			String line;
			
			String texture = "";
			String normal = "";
			String glow = "";
			String displacement = "";
			
			float intensity = 0.0f;
			float power = 0.0f;
			
			String colorMap;
			String normalMap;
			String dispMap;
			String glowMap;
			
			MaterialData m = null;
			
			
			while((line = reader.readLine()) != null)
			{
				
				if(line.startsWith("texture="))
				{
					String[] s = line.split("=");
					texture = s[1];
				}
				
				if(line.startsWith("normalMap="))
				{
					String[] s = line.split("=");
					normal = s[1];
				}
				
				if(line.startsWith("glowMap="))
				{
					String[] s = line.split("=");
					glow = s[1];
				}
				
				if(line.startsWith("dispMap="))
				{
					String[] s = line.split("=");
					displacement = s[1];
				}
				
				if(line.startsWith("specular.intensity="))
				{
					String[] s = line.split("=");
					intensity = Float.parseFloat(s[1]);
				}
				
				if(line.startsWith("specular.power="))
				{
					String[] s = line.split("=");
					power = Float.parseFloat(s[1]);
				}
				
			}
			
			if(texture != "")
			{
				
				colorMap = texture;
			}
			else
			{
				colorMap = "black";
			}
			
			if(normal != "")
			{
				normalMap = normal;
			}
			else
			{
				normalMap = "black";
			}
			
			if(displacement != "")
			{
				dispMap = displacement;
			}
			else
			{
				dispMap = "black";
			}
			
			if(glow != "")
			{
				glowMap = glow;
			}
			else
			{
				glowMap = "black";
			}
			
			m = new MaterialData(colorMap, normalMap, glowMap, dispMap, power, intensity);
		
			reader.close();	
			
			return m;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		return null;
		
	}
}
