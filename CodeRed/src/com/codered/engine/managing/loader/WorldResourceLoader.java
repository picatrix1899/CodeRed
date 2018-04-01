package com.codered.engine.managing.loader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

import com.codered.engine.managing.Paths;
import com.codered.engine.managing.models.Mesh;
import com.codered.engine.managing.models.TexturedModel;
import com.codered.engine.resource.ResourceManager;

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
				
				OBJFile obj = new OBJFile();
				
				obj.load(new File(Paths.p_models + parts[1] + Paths.e_obj));
				
				Mesh m = new Mesh();
				
				m.loadFromObj(obj);
				
				ResourceManager.WORLD.regStaticMesh(parts[0], m);
			}
			
			for(String l : materials)
			{
				ResourceManager.WORLD.regMaterial(l, MaterialLoader.load(l));
			}
			
			for(String l : static_models)
			{
				String[] parts = l.split(":");
				ResourceManager.WORLD.regTexturedModel(parts[0], new TexturedModel(ResourceManager.getStaticMesh(parts[1]), ResourceManager.getMaterial(parts[2])));
			}
			
			reader.close();	
		
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}
}
