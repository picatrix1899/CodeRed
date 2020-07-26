package com.codered.engine;

import java.util.HashMap;
import java.util.Map;

import com.codered.resource.registry.ResourceRegistry;
import com.codered.window.Window;
import com.codered.window.WindowContext;

public class EngineRegistry
{
	private static Map<Long, Window> windowsById = new HashMap<>();
	private static Window currentWindow;
	
	private static Map<Long, ResourceRegistry> resourceRegistriesById = new HashMap<>();
	
	public static void setCurrentWindow(Window window)
	{
		currentWindow = window;
	}
	
	public static Window getCurrentWindow()
	{
		return currentWindow;
	}
	
	public static Window getWindow(long id)
	{
		return windowsById.get(id);
	}
	
	public static WindowContext getWindowContext(long id)
	{
		return windowsById.get(id).getContext();
	}

	public static void registerWindow(long id, Window window)
	{
		windowsById.put(id, window);
	}

	public static WindowContext getCurrentWindowContext()
	{
		return currentWindow.getContext();
	}

	public static void registerResourceRegistry(long windowId, ResourceRegistry manager)
	{
		resourceRegistriesById.put(windowId, manager);
	}
	
	public static ResourceRegistry getResourceRegistry(long windowId)
	{
		return resourceRegistriesById.get(windowId);
	}
	
	public static ResourceRegistry getResourceRegistry()
	{
		return resourceRegistriesById.get(currentWindow.getId());
	}
	
	public static void releaseCurrentContext()
	{
		long id = currentWindow.getId();
		
		windowsById.remove(id);
		resourceRegistriesById.remove(id);
	}
}
