package org.resources.utils;

import java.util.HashMap;
import java.util.Map;

public class ResourceLookupTable
{
	private Map<String,ResourcePath> paths = new HashMap<String,ResourcePath>();
	
	public void put(String id, ResourcePath path)
	{
		this.paths.put(id, path);
	}
	
	public ResourcePath get(String id)
	{
		return this.paths.get(id);
	}
	
	public boolean contains(String id)
	{
		return this.paths.containsKey(id);
	}
}
