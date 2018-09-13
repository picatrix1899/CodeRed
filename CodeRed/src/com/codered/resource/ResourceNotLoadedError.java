package com.codered.resource;

import java.util.Arrays;

import com.codered.CodeRed;

public class ResourceNotLoadedError extends Error
{
	private static final long serialVersionUID = 1L;

	public String path;
	public String type;
	
	public ResourceNotLoadedError(String type, String path)
	{
		super("\nThe Requested Resource has not been loaded!" + " Type: " + type + "; Resource: " + path);
		this.path = path;
		this.type = type;
	}
	
	public ResourceNotLoadedError(String type, String path, int reduction)
	{
		this(type, path);
		
		if (CodeRed.ENABLE_STACK_REDUCTION) reduceStack(reduction);
	}
	
	private void reduceStack(int stackreduction)
	{
		StackTraceElement[] element = this.getStackTrace();
		
		element = Arrays.copyOfRange(element, 0 + stackreduction, element.length);
		
		this.setStackTrace(element);
		
		throw this;			
	}
}
