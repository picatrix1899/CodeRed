package com.codered.gui;

import com.codered.utils.IGuiRenderer;

public abstract class GUIElement extends GUI
{
	
	protected GUIWindow parent;
	private final int id;
	
	public GUIElement(int id, GUIWindow parent, IGuiRenderer renderer)
	{
		super(renderer);
		this.id = id;
		this.parent = parent;
	}
	
	public int getID()
	{
		return this.id;
	}

}
