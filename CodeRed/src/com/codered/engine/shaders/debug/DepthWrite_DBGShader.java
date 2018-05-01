package com.codered.engine.shaders.debug;

import java.util.List;

import com.codered.engine.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class DepthWrite_DBGShader extends DebugShader
{
	public DepthWrite_DBGShader(WindowContext context)
	{
		super(context);
	}

	protected void getAllUniformLocations()
	{
		addUniform("frame");
		addUniform("near");
		addUniform("far");
	}

	public void use()
	{
		start();
		loadTextureId("frame", 0, getInput("frame"));
		//loadFloat("near", com.codered.engine.window.active.NEAR);
		//loadFloat("far", com.codered.engine.window.active.FAR);
	}

	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("debug/dbg_writeDepth"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("debug/dbg_writeDepth"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0,"pos"));
	}
	
}
