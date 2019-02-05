package org.resources.utils;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ResourceLookupTable
{
	private Map<String,URL> paths = new HashMap<String,URL>();
	
	public void put(String id, URL url)
	{
		this.paths.put(id, url);
	}
	
	public URL get(String id)
	{
		return this.paths.get(id);
	}
	
	public boolean contains(String id)
	{
		return this.paths.containsKey(id);
	}
}
