package com.codered.demo;

import com.codered.engine.gui.GUIElement;
import com.codered.engine.gui.GUIWindow;
import com.codered.engine.window.Window;

import cmn.utilslib.math.vector.Vector2f;

public class GUIStartMenu extends GUIWindow
{

	public boolean allowWorldProcessing()
	{
		return false;
	}

	public void response(GUIElement e)
	{
	}

	public int getMinX()
	{
		return 0;
	}

	public int getMaxX()
	{
		return com.codered.engine.window.active.WIDTH;
	}

	public int getMinY()
	{
		return com.codered.engine.window.active.HEIGHT;
	}

	public int getMaxY()
	{
		return 0;
	}

	public void render()
	{
		Window.active.getContext().guiShaders.No.setInput("screenSpace", new Vector2f(getMaxX(), getMaxY()));
		Window.active.getContext().guiShaders.Color.setInput("screenSpace", new Vector2f(getMaxX(), getMaxY()));
		
		super.render();
	}
	
	public void update()
	{
		super.update();
	}
}
