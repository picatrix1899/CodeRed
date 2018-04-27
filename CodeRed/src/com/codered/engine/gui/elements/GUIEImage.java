package com.codered.engine.gui.elements;

import com.codered.engine.gui.GUIElement;
import com.codered.engine.gui.GUIWindow;
import com.codered.engine.managing.Texture;
import com.codered.engine.resource.ResourceManager;
import com.codered.engine.window.Window;

public class GUIEImage extends GUIElement
{

	public Texture t;
	public float posX;
	public float posY;
	public float sizeX;
	public float sizeY;
	
	private boolean drag = false;
	
	public GUIEImage(int id, GUIWindow parent, float posX, float posY, float sizeX, float sizeY, Texture t)
	{
		super(id, parent);
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.t = t;
	}

	public GUIEImage(int id, GUIWindow parent, float posX, float posY, float sizeX, float sizeY, String texture)
	{
		super(id, parent);
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.t = ResourceManager.getColorMap(texture);
	}
	
	public void render()
	{
		drawTexturedRect(t.getId(), posX, posY, sizeX, sizeY);
		
	}

	public void update()
	{
		float sMinX = this.posX;
		float sMinY = this.posY;
		float sMaxX = this.posX + this.sizeX;
		float sMaxY = this.posY + this.sizeY;
		
		if(Window.active.getInputManager().isButtonPressed(0))
		{
			if(mouseIsInsideInclusive(sMinX, sMinY, sMaxX, sMaxY))
			{
				Window.active.getInputManager().setMouseGrabbed(true);
				this.drag = true;
			}			
		}
		
		if(Window.active.getInputManager().isButtonHelt(0))
		{
			if(this.drag)
			{
				this.posX += Window.active.getInputManager().getDX();
				this.posY += -Window.active.getInputManager().getDY();
			}
		}
		
		if(Window.active.getInputManager().isButtonReleased(0))
		{
			if(this.drag)
			{			
				Window.active.getInputManager().setMouseGrabbed(false);

				this.drag = false;
			}

		}
	}

	public void setTexture(Texture t)
	{
		this.t = t;
	}
	

	
}
