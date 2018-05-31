package com.codered.engine.shaders.postprocess.filter;

import java.util.List;

import com.codered.engine.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class Invert_PPFilter extends PPFShader
{

	public Invert_PPFilter(WindowContext context)
	{
		super(context);
	}

	protected void getAllUniformLocations()
	{
		addUniform("frame");
	}
	
	public void use()
	{
		start();
		loadTextureId("frame", 0, getInput("frame"));
	}

	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("ppf_invert"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("ppf_invert"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "pos"));
	}
}
