package com.codered.engine.shaders.postprocess.filter;

import java.util.List;

import com.codered.engine.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class HDR_PPFilter extends PPFShader
{

	public HDR_PPFilter(WindowContext context)
	{
		super(context);
		
		compile();

		getAllUniformLocations();
	}

	protected void getAllUniformLocations()
	{
		addUniform("textureMap");
		addUniform("exposure");
	}
	
	public void use()
	{
		start();
		loadTextureId("textureMap", 0, getInput("textureMap"));
		loadFloat("exposure", getInput("exposure"));
	}

	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("ppf_hdr"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("ppf_hdr"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "pos"));
	}
}
