package org.resources.utils;

import java.util.HashMap;
import java.util.Map;

public class ResourceStorage<T>
{
	private Map<String, T> resources = new HashMap<String,T>();
	
	public void add(String id, T data)
	{
		this.resources.put(id, data);
	}
	
	public T get(String id)
	{
		return this.resources.get(id);
	}
	
	public boolean contains(String id)
	{
		return this.resources.containsKey(id);
	}
	
	public void unloadAll()
	{
		this.resources.clear();
	}
	
	public void unload(String id)
	{
		this.resources.remove(id);
	}
}
