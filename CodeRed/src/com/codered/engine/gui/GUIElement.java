package com.codered.engine.gui;


public abstract class GUIElement extends GUI
{
	
	protected GUIWindow parent;
	private final int id;
	
	public GUIElement(int id, GUIWindow parent)
	{
		this.id = id;
		this.parent = parent;
	}
	
	public int getID()
	{
		return this.id;
	}

}
