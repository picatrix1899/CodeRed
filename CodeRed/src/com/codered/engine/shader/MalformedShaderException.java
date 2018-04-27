package com.codered.engine.shader;

public class MalformedShaderException extends Exception
{
	private static final long serialVersionUID = 1L;

	public MalformedShaderException(String info)
	{
		super(info);
		setStackTrace(new StackTraceElement[0]);
	}
	

}
