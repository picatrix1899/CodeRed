package com.codered.gui.elements;

import org.barghos.math.geometry.Area2f;
import org.barghos.math.point.Point2;
import org.barghos.math.vector.vec2.Vec2;

import com.codered.gui.GUIElement;
import com.codered.gui.GUIWindow;
import com.codered.gui.font.FontType;
import com.codered.rendering.texture.Texture;
import com.codered.utils.IGuiRenderer;

public class GUIEButton extends GUIElement
{
	protected Area2f main;
	
	public Texture background;
	public GUIText text;
	
	public GUIEButton(int id, GUIWindow parent, float posX, float posY, float sizeX, float sizeY, Texture background, IGuiRenderer renderer)
	{
		super(id, parent, renderer);
		
		this.main = new Area2f(new Point2(posX, posY), new Point2(posX + sizeX, posY + sizeY));
		this.background = background;
	}

	public void render()
	{
		Point2 min = this.main.getMin(new Point2());
		Point2 max = this.main.getMax(new Point2());
		
		drawTexturedRect(background, min.getX(), min.getY(), max.getX(), max.getY());
		if(text != null) drawText(text);
	}

	public void setText(String text, int fontsize, FontType font, boolean centeredHorizontal, boolean centeredVertical)
	{
		Point2 min = this.main.getMin(new Point2());
		Point2 max = this.main.getMax(new Point2());
		
		this.text = new GUIText(text, fontsize, min.getX(), min.getY(), max.getX(), max.getY(), font, centeredHorizontal, centeredVertical);
	}
	
	public void onClick()
	{
		Vec2 mousePos = this.context.getMouse().getCurrentPos();
		if(main.isPointInside(mousePos))
		{
			this.parent.response(this);
		}
	}

	public void update()
	{
	}

}
