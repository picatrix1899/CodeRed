package com.codered.engine.gui.elements;

import com.codered.engine.gui.GUIElement;
import com.codered.engine.gui.GUIWindow;
import com.codered.engine.resource.ResourceManager;
import com.codered.engine.window.Window;

public class GUIEButton extends GUIElement
{
	protected Area main = new Area();
	
	public int background;
	public String text;
	
	public GUIEButton(int id, GUIWindow parent, float posX, float posY, float sizeX, float sizeY, String text, int background)
	{
		super(id, parent);
		
		this.main.minX = posX;
		this.main.minY = posY;
		this.main.maxX = sizeX;
		this.main.maxY = sizeY;
		this.background = background;
		this.text = text;
	}

	public void render()
	{

		drawTexturedRect(background, this.main.minX, this.main.minY, this.main.maxX, this.main.maxY);
		drawText(this.text, this.main.minX, this.main.minY, this.main.maxX, this.main.maxY, ResourceManager.getFont("lucida"));
	}

	public void update()
	{
		if(main.mouseIsInsideInclusive())
		{
			if(Window.active.getInputManager().isButtonPressed(0))
			{
				this.parent.response(this);
			}
		}
	}

}
