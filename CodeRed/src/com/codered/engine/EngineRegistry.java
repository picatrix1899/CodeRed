package com.codered.engine;

import java.util.HashMap;
import java.util.Map;

import com.codered.managing.VAOManager;
import com.codered.window.WindowContext;

public class EngineRegistry
{

	private static Map<Long, WindowContext> contextsById = new HashMap<>();
	private static Map<String, WindowContext> contextsByName = new HashMap<>();
	private static WindowContext currentWindowContext;
	
	private static Map<Long, VAOManager> vaoManagersById = new HashMap<>();
	private static Map<String, VAOManager> vaoManagersByName = new HashMap<>();
	
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
	
	public static void releaseCurrentContext()
	{
		long id = currentWindowContext.getWindowId();
		String name = currentWindowContext.getName();
		
		contextsById.remove(id);
		contextsByName.remove(name);
		vaoManagersById.remove(id);
		vaoManagersByName.remove(name);
	}
}
