package com.codered.engine.shaders.shader;

import java.net.URL;

public class ShaderNotFoundException extends Exception
{
	private static final long serialVersionUID = 1L;

	public ShaderNotFoundException(URL url)
	{
		super(url.toExternalForm());
	}
	
	public ShaderNotFoundException(String path)
	{
		super(path);
	}
}
