package com.codered.shaders.postprocess.filter;

import java.util.List;

import com.codered.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class DepthMap_PPFilter extends PPFShader
{

	public DepthMap_PPFilter(WindowContext context)
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
		loadTextureId("frame", 0, (int) getInput("frame"));
		loadFloat("near", (float) getInput("near"));
		loadFloat("far", (float) getInput("far"));
	}

	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("ppf_depthMap"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("ppf_depthMap"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "pos"));
	}
}
