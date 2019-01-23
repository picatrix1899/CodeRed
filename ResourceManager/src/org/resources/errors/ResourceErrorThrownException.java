package org.resources.errors;

public class ResourceErrorThrownException implements ResourceError
{
	private Exception exception;
	
	public ResourceErrorThrownException(Exception e)
	{
		this.exception = e;
	}
	
	public Exception getException()
	{
		return this.exception;
	}
}
