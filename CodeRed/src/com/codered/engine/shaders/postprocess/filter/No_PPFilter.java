package com.codered.engine.shaders.postprocess.filter;

import java.util.List;

import com.codered.engine.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class No_PPFilter extends PPFShader
{

	public No_PPFilter(WindowContext context)
	{
		super(context);
		
		compile();

		getAllUniformLocations();
	}

	protected void getAllUniformLocations()
	{
		addUniform("textureMap");
	}
	
	public void use()
	{
		start();
		loadTextureId("textureMap", 0, getInput("textureMap"));
	}

	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("ppf_no"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("ppf_no"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "pos"));
	}
}
