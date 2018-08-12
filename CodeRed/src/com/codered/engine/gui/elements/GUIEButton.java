package com.codered.engine.gui.elements;

import com.codered.engine.gui.GUIElement;
import com.codered.engine.gui.GUIWindow;

public class GUIEButton extends GUIElement
{
	protected Area main;
	
	public int background;
	public String text;
	
	public GUIEButton(int id, GUIWindow parent, float posX, float posY, float sizeX, float sizeY, String text, int background)
	{
		super(id, parent);
		
		this.main = new Area(this.context);
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
	}

	public void onClick()
	{
		if(main.mouseIsInsideInclusive())
		{
				this.parent.response(this);
		}
	}

	public void update()
	{
	}

}
