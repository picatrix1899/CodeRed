package com.codered.engine.shaders.postprocess.filter;

import java.util.List;

import com.codered.engine.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class Brightness_PPFilter extends PPFShader
{

	public Brightness_PPFilter(WindowContext context)
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
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("ppf_brightness"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("ppf_brightness"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "pos"));
	}

}
