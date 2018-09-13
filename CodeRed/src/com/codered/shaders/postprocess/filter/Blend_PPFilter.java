package com.codered.shaders.postprocess.filter;

import java.util.List;

import com.codered.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class Blend_PPFilter extends PPFShader
{

	public Blend_PPFilter(WindowContext context)
	{
		super(context);
	}

	protected void getAllUniformLocations()
	{
		addUniform("scene1");
		addUniform("scene2");
		addUniform("src");
		addUniform("dst");
		addUniform("op");
	}
	
	public void use()
	{
		start();
		loadTextureId("scene1", 0, getInput("scene1"));
		loadTextureId("scene2", 1, getInput("scene2"));
		loadInt("src", getInput("src"));
		loadInt("dst", getInput("dst"));
		loadInt("op", getInput("op"));
		
	}

	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("ppf_blend"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("ppf_blend"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "pos"));
	}

}
