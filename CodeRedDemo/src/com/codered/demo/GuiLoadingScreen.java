package com.codered.demo;

import com.codered.gui.GUIElement;
import com.codered.gui.GUIWindow;

public class GuiLoadingScreen extends GUIWindow
{

	public GuiLoadingScreen()
	{
		super();
	}
	
	public void render()
	{
		drawTexturedRect("res/materials/loadingscreen.png", 0, 0, this.context.getWindow().getWidth(), this.context.getWindow().getHeight());
		
		super.render();
	}
	
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
		return this.context.getWindow().getWidth();
	}

	public int getMinY()
	{
		return 0;
	}

	public int getMaxY()
	{
		return this.context.getWindow().getHeight();
	}

}
