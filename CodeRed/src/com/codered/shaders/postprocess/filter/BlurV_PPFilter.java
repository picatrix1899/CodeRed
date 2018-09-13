package com.codered.shaders.postprocess.filter;

import java.util.List;

import com.codered.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class BlurV_PPFilter extends PPFShader
{

	public BlurV_PPFilter(WindowContext context)
	{
		super(context);
		
		compile();

		getAllUniformLocations();
	}

	protected void getAllUniformLocations()
	{
		addUniform("textureMap");
		addUniform("targetHeight");
	}
	
	public void use()
	{
		start();
		loadTextureId("textureMap", 0, getInput("textureMap"));
		loadFloat("targetHeight", getInput("targetHeight"));
	}

	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("ppf_blurVertical"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("ppf_blur"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "pos"));
	}
}
