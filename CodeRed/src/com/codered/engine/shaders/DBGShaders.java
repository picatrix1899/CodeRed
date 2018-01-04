package com.codered.engine.shaders;

import com.codered.engine.shaders.debug.Colored_DBGShader;
import com.codered.engine.shaders.debug.DepthWrite_DBGShader;

public class DBGShaders
{
	private DBGShaders() { }
	
	public static final Colored_DBGShader Colored = new Colored_DBGShader();
	public static final DepthWrite_DBGShader DepthWrite = new DepthWrite_DBGShader();
}
