package org.resources.errors;


public class ResourceErrorPending implements ResourceError
{

	private String id;
	
	public ResourceErrorPending(String id)
	{
		this.id = id;
	}
	
	public String getId()
	{
		return this.id;
	}
}
