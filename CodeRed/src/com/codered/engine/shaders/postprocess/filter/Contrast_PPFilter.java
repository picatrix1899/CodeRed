package com.codered.engine.shaders.postprocess.filter;

import java.util.List;

import com.codered.engine.window.IWindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class Contrast_PPFilter extends PPFShader
{

	public Contrast_PPFilter(IWindowContext context)
	{
		super(context);
	}

	protected void getAllUniformLocations()
	{
		addUniform("frame");
		addUniform("contrast");
	}
	
	public void use()
	{
		start();
		loadTextureId("frame", 0, getInput("frame"));
		loadFloat("contrast", getInput("contrast"));
	}

	
	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("ppf_contrast"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("ppf_contrast"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "pos"));
	}
}
