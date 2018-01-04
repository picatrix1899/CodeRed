package com.codered.engine.managing.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

import com.codered.engine.managing.Material;
import com.codered.engine.managing.Paths;
import com.codered.engine.managing.ResourceManager;
import com.codered.engine.managing.Texture;

public class MaterialLoader
{
	public static void load(String file)
	{

		try
		{
			File f = new File(Paths.p_materials + file + ".mat");
			BufferedReader reader = new BufferedReader(new FileReader(f));
				
			String line;
			
			String name = "";
			String texture = "";
			String normal = "";
			String glow = "";
			String displacement = "";
			
			float intensity = 0.0f;
			float power = 0.0f;
			
			Texture colorMap;
			Texture normalMap;
			Texture dispMap;
			Texture glowMap;
			
			Material m;
			
			
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
				
				if(line.startsWith("name="))
				{
					String[] s = line.split("=");
					name = s[1];
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
			
			if(name != "")
			{
			
				if(texture != "")
				{
					ResourceManager.registerColorMap(texture, texture);
					colorMap = ResourceManager.getColorMap(texture);
				}
				else
				{
					colorMap = ResourceManager.getColorMap("black");
				}
				
				if(normal != "")
				{
					ResourceManager.registerNormalMap(normal, normal);
					normalMap = ResourceManager.getNormalMap(normal);
				}
				else
				{
					normalMap = ResourceManager.getNormalMap("black");
				}
				
				if(displacement != "")
				{
					ResourceManager.registerDisplacementMap(displacement, displacement);
					dispMap = ResourceManager.getDisplacementMap(displacement);
				}
				else
				{
					dispMap = ResourceManager.getDisplacementMap("black");
				}
				
				if(glow != "")
				{
					ResourceManager.registerGlowMap(glow, glow);
					glowMap = ResourceManager.getGlowMap(glow);
				}
				else
				{
					glowMap = ResourceManager.getGlowMap("black");
				}
				
				m = new Material(colorMap, normalMap, glowMap, dispMap, power, intensity);
				
				ResourceManager.registerMaterial(name, m);
				
			}
		
			reader.close();	
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
