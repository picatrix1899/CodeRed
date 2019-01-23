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
	
	public DemoGame main;
	
	public GuiInventory(DemoGame parent)
	{
		super();
		this.main = parent;
		inventoryInput = new InputConfiguration();
		inventoryInput.registerKey(Key.TAB);
		inventoryInput.registerButton(0);
		inventoryInput.keyStroke.addHandler((src, dyn) -> { if(src.keyPresent(Key.TAB)) close(); });
		inventoryInput.buttonStroke.addHandler((src, dyn) -> { this.button.onClick(); });
		
		this.button = new GUIEButton(1, this, 0, 0, 60, 82, "res/materials/gray_rsquare.png");
		this.button.setText("Back Test", 20, "res/fonts/arial", true, false);
		
		addElement(this.button);
	}
	
	public void render()
	{
		drawTexturedRect("res/materials/inventory-background.png", 0, 0, this.context.getWindow().getWidth(), this.context.getWindow().getHeight());
		
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
