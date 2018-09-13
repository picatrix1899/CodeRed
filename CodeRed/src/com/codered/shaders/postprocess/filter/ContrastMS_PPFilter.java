package com.codered.shaders.postprocess.filter;

import java.util.List;

import com.codered.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class ContrastMS_PPFilter extends PPFShader
{

	public ContrastMS_PPFilter(WindowContext context)
	{
		super(context);
		
		compile();

		getAllUniformLocations();
	}

	protected void getAllUniformLocations()
	{
		addUniform("textureMap");
		addUniform("contrast");
	}
	
	public void use()
	{
		start();
		loadTextureMSId("textureMap", 0, getInput("textureMap"));
		loadFloat("contrast", getInput("contrast"));
	}

	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("ppf_contrastMS"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("ppf_contrastMS"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "pos"));
	}
}
