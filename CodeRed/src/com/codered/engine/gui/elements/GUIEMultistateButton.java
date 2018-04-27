package com.codered.engine.gui.elements;


import com.codered.engine.gui.GUIElement;
import com.codered.engine.gui.GUIWindow;
import com.codered.engine.resource.ResourceManager;
import com.codered.engine.window.Window;

import cmn.utilslib.color.colors.LDRColor3;
import cmn.utilslib.math.Maths;

public class GUIEMultistateButton extends GUIElement
{
	private String text;
	private float posX;
	private float posY;
	private float sizeX;
	private float sizeY;
	private int state;
	private int max;
	
	private LDRColor3 backgroundColor;
	
	public GUIEMultistateButton(int id, GUIWindow parent, float posX, float posY, float sizeX, float sizeY, String text, int state, int max)
	{
		super(id, parent);
		
		this.posX = posX;
		this.posY = posY;
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.text = text;
		this.state = state;
		this.max = max;
		
		this.backgroundColor = new LDRColor3(255, 255, 0);
	}
	
	public void text(String text)
	{
		this.text = text;
	}
	
	public String text()
	{
		return this.text;
	}
	
	public int state()
	{
		return this.state;
	}
	
	public void state(int state)
	{
		this.state = Maths.clamp(state, 0, max - 1);
	}
	
	public void render()
	{
		drawText(text, posX, posY, sizeX, sizeY, ResourceManager.getFont("lucida"));
		drawColoredRect(this.backgroundColor, this.posX, this.posY, this.sizeX, this.sizeY);
	}

	public void update()
	{
		
		if(mouseIsInsideInclusive(posX, posY, posX + sizeX, posY + sizeY))
		{
			this.backgroundColor.set(0, 255, 0);
		}
		else
		{
			this.backgroundColor.set(255, 255, 0);
		}
		
		if(Window.active.getInputManager().isButtonPressed(0))
		{
			if(mouseIsInsideInclusive(posX, posY, posX + sizeX, posY + sizeY))
			{
				if(state == max - 1)
				{
					state = 0;
				}
				else
				{
					state++;
				}
				
				parent.response(this);
			}			
		}
	}
}
