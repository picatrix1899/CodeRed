package com.codered.engine.shaders.debug;

import com.codered.engine.managing.Window;
import com.codered.engine.shader.Shader.Attrib;
import com.codered.engine.shader.Shader.FragmentShader;
import com.codered.engine.shader.Shader.VertexShader;

@VertexShader("debug/dbg_writeDepth")
@FragmentShader("debug/dbg_writeDepth")
@Attrib(pos=0, var="pos")
public class DepthWrite_DBGShader extends DebugShader
{
	protected void getAllUniformLocations()
	{
		addUniform("frame");
		addUniform("near");
		addUniform("far");
	}

	public void use()
	{
		start();
		loadTexture("frame", 0, getInput("frame"));
		loadFloat("near", Window.active.NEAR);
		loadFloat("far", Window.active.FAR);
	}
	
}
