package com.codered.engine;

import java.util.HashMap;
import java.util.Map;

import com.codered.managing.TextureManager;
import com.codered.managing.VAOManager;
import com.codered.resource.manager.ResourceManager;
import com.codered.resource.registry.ResourceRegistry;
import com.codered.window.WindowContext;

public class EngineRegistry
{

	private static Map<Long, WindowContext> contextsById = new HashMap<>();
	private static Map<String, WindowContext> contextsByName = new HashMap<>();
	private static WindowContext currentWindowContext;
	
	private static Map<Long, VAOManager> vaoManagersById = new HashMap<>();
	private static Map<String, VAOManager> vaoManagersByName = new HashMap<>();
	
	private static Map<Long, TextureManager> textureManagersById = new HashMap<>();
	private static Map<String, TextureManager> textureManagersByName = new HashMap<>();
	
	private static Map<Long, ResourceManager> resourceManagersById = new HashMap<>();
	private static Map<String, ResourceManager> resourceManagersByName = new HashMap<>();
	
	private static Map<Long, ResourceRegistry> resourceRegistriesById = new HashMap<>();
	private static Map<String, ResourceRegistry> resourceRegistriesByName = new HashMap<>();
	
	public static WindowContext getWindowContext(long id)
	{
		return contextsById.get(id);
	}
	
	public static WindowContext getWindowContext(String name)
	{
		return contextsByName.get(name);
	}

	public static void registerWindowContext(String name, long id, WindowContext context)
	{
		contextsById.put(id, context);
		contextsByName.put(name, context);
	}
	
	public static void setCurrentWindowContext(WindowContext context)
	{
		currentWindowContext = context;
	}
	
	public static WindowContext getCurrentWindowContext()
	{
		return currentWindowContext;
	}
	
	public static void registerVAOManager(String contextName, long windowId, VAOManager list)
	{
		vaoManagersById.put(windowId, list);
		vaoManagersByName.put(contextName, list);
	}
	
	public static VAOManager getVAOManager(String contextName)
	{
		return vaoManagersByName.get(contextName);
	}
	
	public static VAOManager getVAOManager(long windowId)
	{
		return vaoManagersById.get(windowId);
	}
	
	public static VAOManager getVAOManager()
	{
		return vaoManagersById.get(currentWindowContext.getWindowId());
	}
	
	public static void registerTextureManager(String contextName, long windowId, TextureManager list)
	{
		textureManagersById.put(windowId, list);
		textureManagersByName.put(contextName, list);
	}
	
	public static TextureManager getTextureManager(String contextName)
	{
		return textureManagersByName.get(contextName);
	}
	
	public static TextureManager getTextureManager(long windowId)
	{
		return textureManagersById.get(windowId);
	}
	
	public static TextureManager getTextureManager()
	{
		return textureManagersById.get(currentWindowContext.getWindowId());
	}

	
	public static void registerResourceManager(String contextName, long windowId, ResourceManager manager)
	{
		resourceManagersById.put(windowId, manager);
		resourceManagersByName.put(contextName, manager);
	}
	
	public static ResourceManager getResourceManager(String contextName)
	{
		return resourceManagersByName.get(contextName);
	}
	
	public static ResourceManager getResourceManager(long windowId)
	{
		return resourceManagersById.get(windowId);
	}
	
	public static ResourceManager getResourceManager()
	{
		return resourceManagersById.get(currentWindowContext.getWindowId());
	}
	
	public static void registerResourceRegistry(String contextName, long windowId, ResourceRegistry manager)
	{
		resourceRegistriesById.put(windowId, manager);
		resourceRegistriesByName.put(contextName, manager);
	}
	
	public static ResourceRegistry getResourceRegistry(String contextName)
	{
		return resourceRegistriesByName.get(contextName);
	}
	
	public static ResourceRegistry getResourceRegistry(long windowId)
	{
		return resourceRegistriesById.get(windowId);
	}
	
	public static ResourceRegistry getResourceRegistry()
	{
		return resourceRegistriesById.get(currentWindowContext.getWindowId());
	}
	
	public static void releaseCurrentContext()
	{
		long id = currentWindowContext.getWindowId();
		String name = currentWindowContext.getName();
		
		contextsById.remove(id);
		contextsByName.remove(name);
		vaoManagersById.remove(id);
		vaoManagersByName.remove(name);
		resourceManagersById.remove(id);
		resourceManagersByName.remove(name);
		resourceRegistriesById.remove(id);
		resourceRegistriesByName.remove(name);
	}
}
