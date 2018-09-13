package com.codered.gui.elements;

import com.codered.gui.GUIElement;
import com.codered.gui.GUIWindow;
import com.codered.resource.ResourceManager;

public class GUIELabel extends GUIElement
{
	private String text;
	private float posX;
	private float posY;
	private float sizeX;
	private float sizeY;
	
	public GUIELabel(int id, GUIWindow parent, float posX, float posY, float sizeX, float sizeY, String text)
	{
		super(id, parent);
		
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.text = text;
	}
	
	public void text(String text)
	{
		this.text = text;
	}
	
	public String text()
	{
		return this.text;
	}
	
	public void render()
	{
		drawText(text, posX, posY, sizeX, sizeY, ResourceManager.getFont("lucida"));
	}

	public void update() { }
	
	
}
