package com.codered.shader;

import java.io.Closeable;

public class ShaderSession implements Closeable
{
	private ShaderProgram program;
	
	public ShaderSession(ShaderProgram program)
	{
		this.program = program;
	}

	@Override
	public void close()
	{
		program.stop();
	}
	
	
}
