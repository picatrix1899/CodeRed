package com.codered.gui.elements;

import org.barghos.math.geometry.Area2;
import org.barghos.math.point.Point2f;
import org.barghos.math.vector.vec2.Vec2f;

import com.codered.gui.GUIElement;
import com.codered.gui.GUIWindow;
import com.codered.gui.font.FontType;
import com.codered.rendering.texture.Texture;
import com.codered.utils.IGuiRenderer;

public class GUIEButton extends GUIElement
{
	protected Area2 main;
	
	public int background;
	public GUIText text;
	
	public GUIEButton(int id, GUIWindow parent, float posX, float posY, float sizeX, float sizeY, Texture background, IGuiRenderer renderer)
	{
		super(id, parent, renderer);
		
		this.main = new Area2(new Point2f(posX, posY), new Point2f(posX + sizeX, posY + sizeY));
		this.background = background.getId();
	}

	public void render()
	{
		Point2f min = this.main.getMin(new Point2f());
		Point2f max = this.main.getMax(new Point2f());
		
		drawTexturedRect(background, min.getX(), min.getY(), max.getX(), max.getY());
		if(text != null) drawText(text);
	}

	public void setText(String text, int fontsize, FontType font, boolean centeredHorizontal, boolean centeredVertical)
	{
		Point2f min = this.main.getMin(new Point2f());
		Point2f max = this.main.getMax(new Point2f());
		
		this.text = new GUIText(text, fontsize, min.getX(), min.getY(), max.getX(), max.getY(), font, centeredHorizontal, centeredVertical);
	}
	
	public void onClick()
	{
		Vec2f mousePos = this.context.getMouse().getCurrentPos();
		if(main.isPointInside(mousePos))
		{
			this.parent.response(this);
		}
	}

	public void update()
	{
	}

}
