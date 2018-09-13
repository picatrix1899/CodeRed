package com.codered.shaders.gui;

import java.util.List;

import com.codered.shader.ShaderProgram;
import com.codered.window.WindowContext;

import cmn.utilslib.dmap.dmaps.DMap2;
import cmn.utilslib.math.vector.api.Vec2fBase;
import cmn.utilslib.math.vector.api.Vec3fBase;

public class Font_GUIShader extends ShaderProgram
{

	public Font_GUIShader(WindowContext context)
	{
		super(context);
		
		compile();
		
		getAllUniformLocations();
	}

	protected void getAllUniformLocations()
	{
		addUniform("color");
		addUniform("fontAtlas");
		addUniform("screenSpace");
	}
	
	public void loadColor(Vec3fBase color)
	{
		setInput("color", color);
	}
	
	public void loadAtlas(int id)
	{
		setInput("fontAtlas", id);
	}
	
	public void loadScreenSpace(Vec2fBase v)
	{
		setInput("screenSpace", v);
	}
	
	public void use()
	{
		start();

		loadVector3("color", getInput("color"));
		loadTextureId("fontAtlas", 0, getInput("fontAtlas"));
		loadVector2("screenSpace", getInput("screenSpace"));
	}

	public void attachShaderParts()
	{
		attachVertexShader(this.context.getShaderParts().builtIn().getVertexShader("gui_font"));
		attachFragmentShader(this.context.getShaderParts().builtIn().getFragmentShader("gui_font"));
	}

	public void getAttribs(List<DMap2<Integer,String>> attribs)
	{
		attribs.add(new DMap2<Integer,String>(0, "position"));
		attribs.add(new DMap2<Integer,String>(1, "textureCoords"));
	}

}
