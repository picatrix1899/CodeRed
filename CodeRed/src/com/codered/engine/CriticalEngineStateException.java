package com.codered.engine;


public class CriticalEngineStateException extends RuntimeException
{

	private static final long serialVersionUID = 1L; 
	
	public CriticalEngineStateException(Throwable t)
	{
		super(t);
	}
	
}
