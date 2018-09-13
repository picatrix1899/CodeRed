package com.codered.demo;

import com.codered.gui.GUIElement;
import com.codered.gui.GUIWindow;
import com.codered.gui.elements.GUIEButton;
import com.codered.input.InputConfiguration;
import com.codered.input.Key;

public class GuiInventory extends GUIWindow
{

	public GUIEButton button;
	
	public InputConfiguration inventoryInput;
	
	public DemoWindowContext1 main;
	
	public GuiInventory(DemoWindowContext1 parent)
	{
		super();
		this.main = parent;
		inventoryInput = new InputConfiguration();
		inventoryInput.registerKey(Key.TAB);
		inventoryInput.registerButton(0);
		inventoryInput.keyStroke.addHandler((src, dyn) -> { if(src.keyPresent(Key.TAB)) close(); });
		inventoryInput.buttonStroke.addHandler((src, dyn) -> { this.button.onClick(); });
		
		this.button = new GUIEButton(1, this, 0, 0, 60, 22, "res/materials/gray_rsquare.png");
		this.button.setText("Back", 20, "res/fonts/arial", true, true);
		
		addElement(this.button);
	}
	
	public void render()
	{
		drawTexturedRect("res/materials/inventory-background.png", 0, 0, this.context.getWidth(), this.context.getHeight());
		
		super.render();
	}
	
	public void open()
	{
		GlobalSettings.ingameInput.pushDubConfiguration(inventoryInput);
	}
	
	public void close()
	{
		this.main.showInventory = false;
		GlobalSettings.ingameInput.popDubConfiguration();
	}
	
	public boolean allowWorldProcessing()
	{
		return true;
	}

	public void response(GUIElement e)
	{
		if(e.getID() == 1)
		{
			close();
		}
	}

	public int getMinX()
	{
		return 0;
	}

	public int getMaxX()
	{
		return this.context.getWidth();
	}

	public int getMinY()
	{
		return 0;
	}

	public int getMaxY()
	{
		return this.context.getHeight();
	}

}
