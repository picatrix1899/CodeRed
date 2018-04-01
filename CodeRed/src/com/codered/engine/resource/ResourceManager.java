package com.codered.engine.resource;

import com.codered.engine.managing.Material;
import com.codered.engine.managing.Texture;
import com.codered.engine.managing.loader.LambdaFont;
import com.codered.engine.managing.loader.TextureLoader;
import com.codered.engine.managing.models.Mesh;
import com.codered.engine.managing.models.RawModel;
import com.codered.engine.managing.models.TexturedModel;

public class ResourceManager
{
	
	public static final InternalResourceManager WORLD = new InternalResourceManager();
	public static final InternalResourceManager GUI = new InternalResourceManager();
	public static final InternalResourceManager STATIC = new InternalResourceManager();
	
	public static final Texture DEFAULT_TEXTURE;
	
	static
	{
		DEFAULT_TEXTURE = TextureLoader.loadTexture("black");
		
		STATIC.renew();
		WORLD.renew();
		GUI.renew();
	}
	
	public static Material getMaterial(String name)
	{
		if(WORLD.containsMaterial(name)) return WORLD.getMaterial(name);
		if(GUI.containsMaterial(name)) return GUI.getMaterial(name);
		return STATIC.getMaterial(name);
	}
	
	public static LambdaFont getFont(String name)
	{
		if(WORLD.containsFont(name)) return WORLD.getFont(name);
		if(GUI.containsFont(name)) return GUI.getFont(name);
		return STATIC.getFont(name);
	}

	public static Mesh getStaticMesh(String name)
	{
		if(WORLD.containsStaticMesh(name)) return WORLD.getStaticMesh(name);
		if(GUI.containsStaticMesh(name)) return GUI.getStaticMesh(name);
		return STATIC.getStaticMesh(name);
	}
	
	public static RawModel getRawModel(String name)
	{
		if(WORLD.containsRawModel(name)) return WORLD.getRawModel(name);
		if(GUI.containsRawModel(name)) return GUI.getRawModel(name);
		return STATIC.getRawModel(name);
	}
	
	public static TexturedModel getTexturedModel(String name)
	{
		if(WORLD.containsTexturedModel(name)) return WORLD.getTexturedModel(name);
		if(GUI.containsTexturedModel(name)) return GUI.getTexturedModel(name);
		return STATIC.getTexturedModel(name);
	}
	
	public static Texture getColorMap(String name)
	{
		if(WORLD.containsColorMap(name)) return WORLD.getColorMap(name);
		if(GUI.containsColorMap(name)) return GUI.getColorMap(name);
		return STATIC.getColorMap(name);
	}

	public static Texture getNormalMap(String name)
	{
		if(WORLD.containsNormalMap(name)) return WORLD.getNormalMap(name);
		if(GUI.containsNormalMap(name)) return GUI.getNormalMap(name);
		return STATIC.getNormalMap(name);
	}

	public static Texture getGlowMap(String name)
	{
		if(WORLD.containsGlowMap(name)) return WORLD.getGlowMap(name);
		if(GUI.containsGlowMap(name)) return GUI.getGlowMap(name);
		return STATIC.getGlowMap(name);
	}

	
	public static Texture getDisplacementMap(String name)
	{
		if(WORLD.containsDisplacementMap(name)) return WORLD.getDisplacementMap(name);
		if(GUI.containsDisplacementMap(name)) return GUI.getDisplacementMap(name);
		return STATIC.getDisplacementMap(name);
	}

	public void clear()
	{
		WORLD.clear();
		STATIC.clear();
		GUI.clear();
	}
	
	public void clearWorld()
	{
		WORLD.clear();
	}
	
	public void clearGUI()
	{
		GUI.clear();
	}
}
