package com.codered.resource.registry;

import java.util.HashMap;
import java.util.Map;

public class ResourceRegistryEntry<T>
{
	public Map<String,T> resources = new HashMap<>();
	
	public T get(String key)
	{
		return this.resources.get(key);
	}
	
	public void add(String key, T resource)
	{
		this.resources.put(key, resource);
	}
	
	public boolean contains(String key)
	{
		return this.resources.containsKey(key);
	}
	
	public void remove(String key)
	{
		this.resources.remove(key);
	}
	
	public void clear()
	{
		this.resources.clear();
	}
}
