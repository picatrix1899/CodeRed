package com.codered.demo;

import com.codered.engine.gui.GUIElement;
import com.codered.engine.gui.GUIWindow;
import com.codered.engine.managing.Window;
import com.codered.engine.shaders.GUIShaders;

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
		return Window.active.WIDTH;
	}

	public int getMinY()
	{
		return Window.active.HEIGHT;
	}

	public int getMaxY()
	{
		return 0;
	}

	public void render()
	{
		GUIShaders.No.setInput("screenSpace", new Vector2f(getMaxX(), getMaxY()));
		GUIShaders.Color.setInput("screenSpace", new Vector2f(getMaxX(), getMaxY()));
		
		super.render();
	}
	
	public void update()
	{
		super.update();
	}
}
