package com.codered.engine.shaders.gui;

public class Base_GUIShader extends GUIShader
{
	
	protected int textureMap;

	protected void getAllUniformLocations()
	{
		addUniform("textureMap");
		addUniform("screenSpace");
	}
	
	public void loadTexture(int id)
	{
		this.textureMap = id;
	}
	
	public void use()
	{
		start();
		loadTexture("textureMap", 0, this.textureMap);
		loadVector2("screenSpace", getInput("screenSpace"));
	}

}
