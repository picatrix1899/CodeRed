package com.codered.shaders.gui;

import java.util.List;

import com.codered.engine.EngineRegistry;
import com.codered.shader.ShaderProgram;
import com.codered.shader.UniformTexture;
import com.codered.shader.UniformVector2;
import com.codered.shader.UniformVector3;
import com.codered.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;

public class Font_GUIShader extends ShaderProgram
{

	public UniformTexture u_fontAtlas = new UniformTexture("fontAtlas", 0);
	public UniformVector3 u_color = new UniformVector3("color");
	public UniformVector2 u_screenSpace = new UniformVector2("screenSpace");
	
	public Font_GUIShader(WindowContext context)
	{
		super(context);
		
		addUniform(u_fontAtlas);
		addUniform(u_color);
		addUniform(u_screenSpace);
		
		compile();
		
		getAllUniformLocations();
	}

	public void attachShaderParts()
	{
		attachVertexShader(EngineRegistry.getShaderParts().builtIn().getVertexShader("gui_font"));
		attachFragmentShader(EngineRegistry.getShaderParts().builtIn().getFragmentShader("gui_font"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "position"));
		attribs.add(new DMap2<Integer,String>(1, "textureCoords"));
	}

}
