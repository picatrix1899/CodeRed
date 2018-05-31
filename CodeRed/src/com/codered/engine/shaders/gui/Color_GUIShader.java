package com.codered.engine.shaders.gui;

import java.util.List;

import com.codered.engine.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class Color_GUIShader extends GUIShader
{
	
	public Color_GUIShader(WindowContext context)
	{
		super(context);
	}


	protected void getAllUniformLocations()
	{
		super.getAllUniformLocations();
		
		addUniform("color");
	}


	public void use()
	{
		super.use();
		
		loadColor3("color", getInput("color"));
	}


	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("gui_color"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("gui_color"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "vertexPos"));
	}
}
