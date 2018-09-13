package com.codered.shaders.postprocess.filter;

import java.util.List;

import com.codered.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class RadialBlur_PPFilter extends PPFShader
{

	public RadialBlur_PPFilter(WindowContext context)
	{
		super(context);
		
		compile();

		getAllUniformLocations();
	}

	protected void getAllUniformLocations()
	{
		addUniform("textureMap");
		addUniform("delta");
		addUniform("amplitude");
		addUniform("cycles");
	}
	
	public void use()
	{
		start();
		loadTextureId("textureMap", 0, getInput("textureMap"));
		loadInt("cycles", getInput("cycles"));
		loadFloat("delta", getInput("delta"));
		loadFloat("amplitude", getInput("amplitude"));
	}

	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("ppf_radialBlur"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("ppf_radialBlur"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "pos"));
	}
}
