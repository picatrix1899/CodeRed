package com.codered.shaders.postprocess.filter;

import java.util.List;

import com.codered.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class DepthTestMS_PPFilter extends PPFShader
{

	public DepthTestMS_PPFilter(WindowContext context)
	{
		super(context);
	}

	protected void getAllUniformLocations()
	{
		addUniform("frameSrc");
		addUniform("frameDst");
		addUniform("depthSrc");
		addUniform("depthDst");
		addUniform("near");
		addUniform("far");
	}
	
	public void use()
	{
		start();
		loadTextureMSId("frameSrc", 0, (int) getInput("frameSrc"));
		loadTextureMSId("frameDst", 1, (int) getInput("frameDst"));
		loadTextureMSId("depthSrc", 2, (int) getInput("depthSrc"));
		loadTextureMSId("depthDst", 3, (int) getInput("depthDst"));
		loadFloat("near", (float) getInput("near"));
		loadFloat("far", (float) getInput("far"));
	}

	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("ppf_depthTestMS"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("ppf_depthTestMS"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "pos"));
	}
}
