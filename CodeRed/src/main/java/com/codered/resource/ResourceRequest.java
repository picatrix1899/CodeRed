package com.codered.resource;

public class ResourceRequest
{
	public String key;
	public String file;
	public boolean created;
	public ResourceResponse response;
	
	public  ResourceRequest(String file)
	{
		this.key = file;
		this.file = file;
	}

}
