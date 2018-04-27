package com.codered.engine.resource;

import com.codered.engine.managing.Material;
import com.codered.engine.managing.Texture;
import com.codered.engine.managing.loader.LambdaFont;
import com.codered.engine.managing.models.Mesh;
import com.codered.engine.managing.models.RawModel;
import com.codered.engine.managing.models.TexturedModel;
import com.codered.engine.window.WindowContext;

public class ResourceManager
{
	
	public final InternalResourceManager WORLD = new InternalResourceManager(this);
	public final InternalResourceManager GUI = new InternalResourceManager(this);
	public final InternalResourceManager STATIC = new InternalResourceManager(this);

	private WindowContext context;
	
	public ResourceManager(WindowContext context)
	{
		this.context = context;
		STATIC.renew();
		WORLD.renew();
		GUI.renew();
	}
	
	public WindowContext getWindowContext()
	{
		return this.context;
	}
	
	public Material getMaterial(String name)
	{
		if(WORLD.containsMaterial(name)) return WORLD.getMaterial(name);
		if(GUI.containsMaterial(name)) return GUI.getMaterial(name);
		return STATIC.getMaterial(name);
	}
	
	public LambdaFont getFont(String name)
	{
		if(WORLD.containsFont(name)) return WORLD.getFont(name);
		if(GUI.containsFont(name)) return GUI.getFont(name);
		return STATIC.getFont(name);
	}

	public Mesh getStaticMesh(String name)
	{
		if(WORLD.containsStaticMesh(name)) return WORLD.getStaticMesh(name);
		if(GUI.containsStaticMesh(name)) return GUI.getStaticMesh(name);
		return STATIC.getStaticMesh(name);
	}
	
	public RawModel getRawModel(String name)
	{
		if(WORLD.containsRawModel(name)) return WORLD.getRawModel(name);
		if(GUI.containsRawModel(name)) return GUI.getRawModel(name);
		return STATIC.getRawModel(name);
	}
	
	public TexturedModel getTexturedModel(String name)
	{
		if(WORLD.containsTexturedModel(name)) return WORLD.getTexturedModel(name);
		if(GUI.containsTexturedModel(name)) return GUI.getTexturedModel(name);
		return STATIC.getTexturedModel(name);
	}
	
	public Texture getTexture(String name)
	{
		if(WORLD.containsTexture(name)) return WORLD.getTexture(name);
		if(GUI.containsTexture(name)) return GUI.getTexture(name);
		return STATIC.getTexture(name);
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
