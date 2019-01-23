package org.resources.errors;


public class MissingResourceLookupException extends Exception
{

	private static final long serialVersionUID = 1L;
	
	private String id;
	private String type;
	
	public MissingResourceLookupException(String type, String id)
	{
		this.id = id;
		this.type = type;
	}
	
	public String getId()
	{
		return this.id;
	}
	
	public String getType()
	{
		return this.type;
	}
}
