package com.codered.engine.resource;


public class ResourcePath
{
	private String base = "";
	private String file = "";
	private String extension = "";
	
	private Class<?> src = getClass();
	private ResourceDestination dest = ResourceDestination.LOCAL;
	
	public ResourcePath() { }
	
	public Class<?> src() { return this.src; }
	
	public ResourcePath src(Class<?> clazz) { this.src = clazz; return this; }
	
	public ResourceDestination dest() { return this.dest; }
	
	public ResourcePath dest(ResourceDestination dest) { this.dest = dest; return this; }
	
	public String base() { return this.base; }
	
	public ResourcePath base(String s) { this.base = s; return this; }
	
	public String file() { return this.file; }
	
	public ResourcePath file(String s) { this.file = s; return this; }
	
	public String extension() { return this.extension; }
	
	public ResourcePath extension(String s) { this.extension = s; return this; }
	
	
	public String toString()
	{
		return this.base + this.file + this.extension;
	}
	
	public static enum ResourceDestination
	{
		EMBEDED,
		URL,
		LOCAL
	}
}
