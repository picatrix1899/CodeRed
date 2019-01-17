package com.codered.shaders.gui;

import java.util.List;

import com.codered.shader.UniformVector3;
import com.codered.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class Color_GUIShader extends GUIShader
{
	
	public UniformVector3 u_color = new UniformVector3("color");
	
	public Color_GUIShader(WindowContext context)
	{
		super(context);
		
		addUniform(u_color);
		
		compile();
		
		getAllUniformLocations();
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
