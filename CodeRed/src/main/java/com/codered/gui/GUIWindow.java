package com.codered.gui;

import java.util.HashMap;

import com.codered.utils.IGuiRenderer;

public abstract class GUIWindow extends GUI
{
	
	private HashMap<Integer, GUIElement> elements = new HashMap<>();
	
	public GUIWindow(IGuiRenderer renderer)
	{
		super(renderer);
	}
	
	public void addElement(GUIElement e)
	{
		elements.put(e.getID(), e);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends GUIElement> T getElement(int id)
	{
		return (T) elements.get(id);
	}
	
	public void render()
	{
		for(GUIElement e : this.elements.values())
		{
			e.render();
		}
	}

	public void update()
	{
		for(GUIElement e : this.elements.values())
		{
			e.update();
		}
	}

	public abstract boolean allowWorldProcessing();
	
	public abstract void response(GUIElement e);
	
	public abstract int getMinX();
	public abstract int getMaxX();
	public abstract int getMinY();
	public abstract int getMaxY();
}
