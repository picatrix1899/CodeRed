package com.codered.shaders.gui;

import java.util.List;

import com.codered.EngineRegistry;
import com.codered.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class Texture_GUIShader extends GUIShader
{
	public Texture_GUIShader(WindowContext context)
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
		attachVertexShader(EngineRegistry.getShaderParts().builtIn().getVertexShader("gui_color"));
		attachFragmentShader(EngineRegistry.getShaderParts().builtIn().getFragmentShader("gui_color"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "vertexPos"));
		attribs.add(new DMap2<Integer,String>(1, "texCoords"));
	}
}
