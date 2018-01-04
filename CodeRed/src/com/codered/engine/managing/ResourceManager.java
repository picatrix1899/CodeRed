package com.codered.engine.managing;

import java.io.File;
import java.util.HashMap;

import com.codered.engine.managing.loader.LambdaFont;
import com.codered.engine.managing.loader.MaterialLoader;
import com.codered.engine.managing.loader.OBJFile2;
import com.codered.engine.managing.loader.TextureLoader;
import com.codered.engine.managing.models.Mesh;
import com.codered.engine.managing.models.RawModel;
import com.codered.engine.managing.models.TexturedModel;

import cmn.utilslib.essentials.Auto;

public class ResourceManager
{
	private static HashMap<String,Texture> colorMaps = Auto.HashMap();
	private static HashMap<String,Texture> normalMaps = Auto.HashMap();
	private static HashMap<String,Texture> glowMaps = Auto.HashMap();
	private static HashMap<String,Texture> dispMaps = Auto.HashMap(); 
	private static HashMap<String,Mesh> staticMeshes = Auto.HashMap();
	private static HashMap<String,RawModel> rawModels = Auto.HashMap();
	private static HashMap<String,Material> materials = Auto.HashMap();
	private static HashMap<String,TexturedModel> texturedModels = Auto.HashMap();
	private static HashMap<String,LambdaFont> fonts = Auto.HashMap();
	
	static
	{
		ResourceManager.registerColorMap("black", "black");
		ResourceManager.registerNormalMap("black", "black");
		ResourceManager.registerGlowMap("black", "black");
		ResourceManager.registerDisplacementMap("black","black");
	}
	
	public static Material getMaterial(String name)
	{
		return materials.get(name);
	}
	
	public static LambdaFont getFont(String name)
	{
		return fonts.get(name);
	}
	 
	public static Mesh getStaticMesh(String name)
	{
		return staticMeshes.get(name);
	}
	
	public static RawModel getRawModel(String name)
	{
		return rawModels.get(name);
	}
	
	public static TexturedModel getTexturedModel(String name)
	{
		return texturedModels.get(name);
	}
	
	public static Texture getColorMap(String name)
	{
		return colorMaps.get(name);
	}
	
	public static Texture getNormalMap(String name)
	{
		return normalMaps.get(name);
	}
	
	public static Texture getGlowMap(String name)
	{
		return glowMaps.get(name);
	}
	
	public static Texture getDisplacementMap(String name)
	{
		return dispMaps.get(name);
	}
	
	public static void registerMaterial(String name, Material mat)
	{
		if(!materials.containsKey(name))
			materials.put(name, mat);			
	}
	
	public static void registerFont(String name, File file)
	{
		if(!fonts.containsKey(name))
			try
			{
				LambdaFont f = new LambdaFont(file);
				f.load();
				fonts.put(name, f);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
	}
	


	public static void registerMaterial(String name, String colorMap, String normalMap, String glowMap, String dispMap, float specularPower, float specularIntensity)
	{
		if(!materials.containsKey(name))
			materials.put(name, new Material(getColorMap(colorMap), getNormalMap(colorMap), getGlowMap(glowMap), getDisplacementMap(dispMap), specularPower, specularIntensity));			
	}
	
	public static void registerColorMap(String name, String file)
	{
		if(!colorMaps.containsKey(name))
			colorMaps.put(name, TextureLoader.loadTexture(file));			
	}
	
	public static void registerNormalMap(String name, String file)
	{
		if(!normalMaps.containsKey(name))
		{
			normalMaps.put(name, TextureLoader.loadTexture(file));			
		}
	}
	
	public static void registerGlowMap(String name, String file)
	{
		if(!glowMaps.containsKey(name))
		{
			glowMaps.put(name, TextureLoader.loadTexture(file));			
		}
	}
	
	public static void registerDisplacementMap(String name, String file)
	{
		if(!dispMaps.containsKey(name))
		{
			dispMaps.put(name, TextureLoader.loadTexture(file));			
		}
	}
	
	
	
	public static void registerMaterialFromFile(String file)
	{
		MaterialLoader.load(file);
	}
	

	
	public static void registerStaticMesh(String name, String file)
	{
		if(!staticMeshes.containsKey(name))
		{
			
			OBJFile2 f = new OBJFile2();
			
			f.load(new File(Paths.p_models + name + Paths.e_obj));
			
			Mesh m = new Mesh();
			
			m.loadFromObj(f);

			staticMeshes.put(name, m);			
		}
	}
	
	public static void registerRawModel(String name, RawModel model)
	{
		if(!rawModels.containsKey(name))
		{
			rawModels.put(name, model);			
		}
	}
	
	public static void registerTexturedModel(String name, String mesh, String material)
	{
		if(!texturedModels.containsKey(name))
		{
			texturedModels.put(name, new TexturedModel(getStaticMesh(mesh), getMaterial(material)));			
		}
	}
	
	
	public static void cleanup()
	{
		
	}
}
