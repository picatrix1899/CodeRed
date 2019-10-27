package com.codered.gui.elements;

import com.codered.font.FontType;
import com.codered.gui.GUIElement;
import com.codered.gui.GUIWindow;
import com.codered.texture.Texture;
import com.codered.utils.IGuiRenderer;

public class GUIEButton extends GUIElement
{
	protected Area main;
	
	public Texture background;
	public GUIText text;
	
	public GUIEButton(int id, GUIWindow parent, float posX, float posY, float sizeX, float sizeY, Texture background, IGuiRenderer renderer)
	{
		super(id, parent, renderer);
		
		this.main = new Area(this.context);
		this.main.minX = posX;
		this.main.minY = posY;
		this.main.maxX = posX + sizeX;
		this.main.maxY = posY + sizeY;
		this.background = background;
	}

	public void render()
	{
		drawTexturedRect(background, this.main.minX, this.main.minY, this.main.maxX, this.main.maxY);
		if(text != null) drawText(text);
	}

	public void setText(String text, int fontsize, FontType font, boolean centeredHorizontal, boolean centeredVertical)
	{
		this.text = new GUIText(text, fontsize, main.minX, main.minY, main.maxX, main.maxY, font, centeredHorizontal, centeredVertical);
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
