package com.codered.engine.managing.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.codered.engine.managing.Paths;
import com.codered.engine.managing.ResourceManager;

import cmn.utilslib.essentials.Auto;

public class WorldResourceLoader
{
	public static void load(String file)
	{
		try
		{
			File f = new File(Paths.p_maps + file + ".resource");
			BufferedReader reader = new BufferedReader(new FileReader(f));
				
			String line;
			
			String tag = "";
			
			ArrayList<String> meshes = Auto.ArrayList();
			ArrayList<String> materials = Auto.ArrayList();
			ArrayList<String> static_models = Auto.ArrayList();
			ArrayList<String> terrain = Auto.ArrayList();
			
			while((line = reader.readLine()) != null)
			{
				if(line.startsWith("[") && line.endsWith("]"))
				{
					tag = line;
				}
				else if(tag.equalsIgnoreCase("[meshes]") && line.trim() != "" && !line.startsWith("#"))
				{
					meshes.add(line);
				}
				else if(tag.equalsIgnoreCase("[materials]") && line.trim() != "" && !line.startsWith("#"))
				{
					materials.add(line);
				}
				else if(tag.equalsIgnoreCase("[static-models]") && line.trim() != "" && !line.startsWith("#"))
				{
					static_models.add(line);
				}
				else if(tag.equalsIgnoreCase("[terrain]") && line.trim() != "" && !line.startsWith("#"))
				{
					terrain.add(line);
				}
			}
			
			for(String l : meshes)
			{
				String[] parts = l.split(":");
				
				ResourceManager.registerStaticMesh(parts[0], parts[1]);
			}
			
			for(String l : materials)
			{
				ResourceManager.registerMaterialFromFile(l);
			}
			
			for(String l : static_models)
			{
				String[] parts = l.split(":");
				
				ResourceManager.registerTexturedModel(parts[0], parts[1], parts[2]);
			}
			
			for(String l : terrain)
			{
				ResourceManager.registerRawModel(l, new TerrainLoader().loadTerrain());
			}
			
			reader.close();	
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
