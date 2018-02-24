package com.codered.demo;

import com.codered.engine.gui.GUIElement;
import com.codered.engine.gui.GUIWindow;
import com.codered.engine.gui.elements.GUIEButton;
import com.codered.engine.gui.elements.GUIEColoredHSlider;
import com.codered.engine.gui.elements.GUIEImage;
import com.codered.engine.gui.elements.GUIELabel;
import com.codered.engine.managing.ResourceManager;
import com.codered.engine.managing.Window;
import com.codered.engine.shaders.GUIShaders;

import cmn.utilslib.math.vector.Vector2f;

public class GUIIngameOverlay extends GUIWindow
{
	public static GUIIngameOverlay instance = new GUIIngameOverlay();
	
	public GUIELabel label = new GUIELabel(1, this, 0, 200, 40, 40, "pacman1");
	
	public GUIIngameOverlay()
	{
		addElement(label);
	}
	
	public int getMinX()
	{
		return 0;
	}
	
	public int getMinY()
	{
		return 0;
	}
	
	public int getMaxX()
	{
		return Window.active.WIDTH;
	}
	
	public int getMaxY()
	{
		return Window.active.HEIGHT;
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

	public void response(GUIElement e)
	{
		
	}
	
	public boolean allowWorldProcessing()
	{
		return true;
	}	
}
