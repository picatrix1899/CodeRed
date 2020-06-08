package com.codered.engine;

import java.util.HashMap;
import java.util.Map;

import com.codered.resource.registry.ResourceRegistry;
import com.codered.window.WindowContext;

public class EngineRegistry
{

	private static Map<Long, WindowContext> contextsById = new HashMap<>();
	private static Map<String, WindowContext> contextsByName = new HashMap<>();
	private static WindowContext currentWindowContext;
	
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
		resourceRegistriesById.remove(id);
		resourceRegistriesByName.remove(name);
	}
}
