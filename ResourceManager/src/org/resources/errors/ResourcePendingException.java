package org.resources.errors;


public class ResourcePendingException extends Exception
{

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	public ResourcePendingException(String id)
	{
		this.id = id;
	}
	
	public String getId()
	{
		return this.id;
	}
}
