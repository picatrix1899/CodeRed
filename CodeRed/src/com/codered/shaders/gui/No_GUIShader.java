package com.codered.shaders.gui;

import java.util.List;

import com.codered.shader.UniformTexture;
import com.codered.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class No_GUIShader extends GUIShader
{
	
	public UniformTexture u_textureMap = new UniformTexture("textureMap", 0);
	
	public No_GUIShader(WindowContext context)
	{
		super(context);
		
		addUniform(u_textureMap);
		
		compile();
		
		getAllUniformLocations();
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
