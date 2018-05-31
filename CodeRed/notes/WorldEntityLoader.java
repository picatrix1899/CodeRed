package com.codered.engine.managing.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.codered.engine.managing.Paths;
import com.codered.engine.managing.World;

import cmn.utilslib.color.colors.LDRColor3;
import cmn.utilslib.essentials.Auto;
import cmn.utilslib.math.vector.Vector3f;

public class WorldEntityLoader
{
	public static void load(String file, World w)
	{
		try
		{
			File f = new File(Paths.p_maps + file + ".crm");
			BufferedReader reader = new BufferedReader(new FileReader(f));
				
			String line;
			
			String tag = "";
			
			ArrayList<String> static_entities = Auto.ArrayList();
			ArrayList<String> pointlights = Auto.ArrayList();
			
			while((line = reader.readLine()) != null)
			{
				if(line.startsWith("[") && line.endsWith("]"))
				{
					tag = line;
				}
				else if(tag.equalsIgnoreCase("[static-entities]") && line.trim() != "" && !line.startsWith("#"))
				{
					static_entities.add(line);
				}
				else if(tag.equalsIgnoreCase("[pointlights]") && line.trim() != "" && !line.startsWith("#"))
				{
					pointlights.add(line);
				}
			}
			
			for(String l : static_entities)
			{
				String[] parts = l.split(":");
				
				long id = Long.parseLong(parts[0]);
				String model = parts[1];
				
				String[] compPos = parts[2].split(",");
				Vector3f pos = new Vector3f(Float.parseFloat(compPos[0]), Float.parseFloat(compPos[1]), Float.parseFloat(compPos[2]));
				
				String[] compRot = parts[3].split(",");
				float rx = Float.parseFloat(compRot[0]);
				float ry = Float.parseFloat(compRot[1]);
				float rz = Float.parseFloat(compRot[2]);
				
				w.addStaticEntity(id, model, pos, rx, ry, rz);
			}
			
			for(String l : pointlights)
			{
				String[] parts = l.split(":");
				
				long id = Long.parseLong(parts[0]);

				String[] compPos = parts[1].split(",");
				Vector3f pos = new Vector3f(Float.parseFloat(compPos[0]), Float.parseFloat(compPos[1]), Float.parseFloat(compPos[2]));
				
				String[] compColor = parts[2].split(",");
				LDRColor3 color = new LDRColor3(Float.parseFloat(compColor[0]), Float.parseFloat(compColor[1]), Float.parseFloat(compColor[2]));
				
				float intensity = Float.parseFloat(parts[3]);
				float constant = Float.parseFloat(parts[4]);
				float linear = Float.parseFloat(parts[5]);
				float exponent = Float.parseFloat(parts[6]);
				
				w.addStaticPointLight(id, pos, color, intensity, constant, linear, exponent);
			}
			
			reader.close();	
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
