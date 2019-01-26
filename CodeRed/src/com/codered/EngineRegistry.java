package com.codered;

import java.util.Map;

import com.codered.managing.VAOManager;
import com.codered.shader.ShaderList;
import com.codered.shader.ShaderParts;
import com.codered.shader.ShaderProgram;
import com.codered.window.WindowContext;

import com.google.common.collect.Maps;

public class EngineRegistry
{

	private static Map<Long, WindowContext> contextsById = Maps.newHashMap();
	private static Map<String, WindowContext> contextsByName = Maps.newHashMap();
	private static WindowContext currentWindowContext;
	
	private static Map<Long, ShaderList> shaderListsById = Maps.newHashMap();
	private static Map<String, ShaderList> shaderListsByName = Maps.newHashMap();
	
	private static Map<Long, ShaderParts> shaderPartsById = Maps.newHashMap();
	private static Map<String, ShaderParts> shaderPartsByName = Maps.newHashMap();
	
	private static Map<Long, VAOManager> vaoManagersById = Maps.newHashMap();
	private static Map<String, VAOManager> vaoManagersByName = Maps.newHashMap();
	
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
	
	
	
	public static void registerShaderList(String contextName, long windowId, ShaderList list)
	{
		shaderListsById.put(windowId, list);
		shaderListsByName.put(contextName, list);
	}
	
	public static ShaderList getShaderList(String contextName)
	{
		return shaderListsByName.get(contextName);
	}
	
	public static ShaderList getShaderList(long windowId)
	{
		return shaderListsById.get(windowId);
	}
	
	public static ShaderList getShaderList()
	{
		return shaderListsById.get(currentWindowContext.getWindowId());
	}
	
	public static void registerShader(String contextName, Class<? extends ShaderProgram> clazz)
	{
		shaderListsByName.get(contextName).addShader(clazz);
	}
	
	public static void registerShader(long windowId, Class<? extends ShaderProgram> clazz)
	{
		shaderListsById.get(windowId).addShader(clazz);
	}
	
	public static void registerShader(Class<? extends ShaderProgram> clazz)
	{
		shaderListsById.get(currentWindowContext.getWindowId()).addShader(clazz);
	}
	
	public static <A extends ShaderProgram> A getShader(String contextName, Class<A> clazz)
	{
		return getShaderList(contextName).getShader(clazz);
	}
	
	public static <A extends ShaderProgram> A getShader(long windowId, Class<A> clazz)
	{
		return getShaderList(windowId).getShader(clazz);
	}
	
	public static <A extends ShaderProgram> A getShader(Class<A> clazz)
	{
		return getShaderList().getShader(clazz);
	}
	
	
	public static void registerShaderParts(String contextName, long windowId, ShaderParts list)
	{
		shaderPartsById.put(windowId, list);
		shaderPartsByName.put(contextName, list);
	}
	
	public static ShaderParts getShaderParts(String contextName)
	{
		return shaderPartsByName.get(contextName);
	}
	
	public static ShaderParts getShaderParts(long windowId)
	{
		return shaderPartsById.get(windowId);
	}
	
	public static ShaderParts getShaderParts()
	{
		return shaderPartsById.get(currentWindowContext.getWindowId());
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
		shaderListsById.remove(id);
		shaderListsByName.remove(name);
		shaderPartsById.remove(id);
		shaderPartsByName.remove(name);
		vaoManagersById.remove(id);
		vaoManagersByName.remove(name);
	}
}
