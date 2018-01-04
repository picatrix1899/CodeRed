package com.codered.engine.managing;


public class ResourcePath
{
	private String base = "";
	private String file = "";
	private String extension = "";
	
	public ResourcePath() { }
	
	public ResourcePath(String base)
	{
		this.base = base;
	}
	
	public ResourcePath(String base, String file)
	{
		this.base = base;
		this.file = file;
	}
	
	public ResourcePath(String base, String file, String extension)
	{
		this.base = base;
		this.file = file;
		this.extension = extension;
	}
	
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
}
