package com.codered.engine.shaders.gui;

import java.util.List;

import com.codered.engine.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class No_GUIShader extends GUIShader
{
	public No_GUIShader(WindowContext context)
	{
		super(context);
	}

	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		addUniform("textureMap");
	}

	public void loadTextureMap(int id)
	{
		setInput("textureMap", id);
	}

	public void use()
	{
		super.use();
		
		loadTextureId("textureMap", 0, getInput("textureMap"));
	}

	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("gui_no"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("gui_no"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "vertexPos"));
		attribs.add(new DMap2<Integer,String>(1, "texCoords"));
	}
}
