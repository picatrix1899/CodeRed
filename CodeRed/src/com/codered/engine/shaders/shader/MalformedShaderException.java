package com.codered.engine.shaders.shader;

import java.net.URL;

public class MalformedShaderException extends Exception
{
	private static final long serialVersionUID = 1L;

	public MalformedShaderException(URL url, String info)
	{
		super(url.toExternalForm() + "\n" + info);
		setStackTrace(new StackTraceElement[0]);
	}
	

}
