package com.codered.resourcemanager.errors;

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
