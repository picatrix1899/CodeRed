package com.codered.engine.gui.elements;

import com.codered.engine.gui.GUIElement;
import com.codered.engine.gui.GUIWindow;
import com.codered.engine.window.Window;

import cmn.utilslib.color.colors.LDRColor3;
import cmn.utilslib.math.Func;
import cmn.utilslib.math.Maths;

public class GUIEColoredVSlider extends GUIElement
{


	protected float posX;
	protected float posY;
	protected float sizeX;
	protected float sizeY;
	
	protected float sliderOffX1;
	protected float sliderOffX2;
	protected float sliderOffY1;
	protected float sliderOffY2;
	
	protected float sliderPosX;
	protected float sliderPosY;
	protected float sliderSizeX;
	protected float sliderSizeY;
	
	protected LDRColor3 sliderColor; 
	protected LDRColor3 backgroundColor;
	
	protected boolean drag;
	
	protected float mouseOffX;
	protected float mouseOffY;
	
	protected int value;
	
	protected int max;
	
	public GUIEColoredVSlider(int id, GUIWindow parent, float posX, float posY, float sizeX, float sizeY, int val, int max)
	{
		super(id, parent);
		
		this.posX = posX;
		this.posY = posY;
		
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		
		this.sliderPosX = 0;
		
		this.sliderOffX1 = 5;
		this.sliderOffX2 = 5;
		this.sliderOffY1 = 5;
		this.sliderOffY2 = 5;
		this.sliderSizeX = sizeX;
		this.sliderSizeY = 10;
		this.max = max;
		
		float m = sizeY - sliderOffY1 - sliderSizeY - sliderOffY2;
		
		this.sliderPosY = Func.projectScale(val, 0, 0, this.max, m);//Maths.rimg(val, m, this.max);
		
		this.backgroundColor = new LDRColor3(255, 0, 0);
		this.sliderColor = new LDRColor3(0, 0, 0);

	}
	
	public int max()
	{
		return this.max;
	}
	
	public int value()
	{
		return this.value;
	}
	
	public void value(int val)
	{
		float max = sizeY - sliderOffY1 - sliderSizeY - sliderOffY2;
		this.value = val;
		this.sliderPosY = Func.projectScale(val, 0, 0, this.max, max);//Maths.rimg(val, max, this.max);
	}

	public LDRColor3 backgroundColor()
	{
		return this.backgroundColor;
	}
	
	public void render()
	{
		drawColoredRect(this.backgroundColor, this.posX, this.posY, this.sizeX, this.sizeY);
		drawColoredRect(this.sliderColor, this.posX + this.sliderOffX1, this.posY + this.sliderOffY1 + sliderPosY, this.sliderSizeX - this.sliderOffX1 - this.sliderOffX2, this.sliderSizeY);
	}

	public void update()
	{
		float sMinX = this.posX + this.sliderOffX1 + this.sliderPosX;
		float sMinY = this.posY + this.sliderOffY1 + this.sliderPosY;
		float sMaxX = sMinX + this.sliderSizeX;
		float sMaxY = sMinY + this.sliderSizeY;
		
		if(mouseIsInsideInclusive(sMinX, sMinY, sMaxX, sMaxY))
		{
			this.sliderColor.set(0, 255, 0);
		}
		else
		{
			this.sliderColor.set(0, 0, 0);
		}
		
		if(Window.active.getInputManager().isButtonPressed(0))
		{
			if(mouseIsInsideInclusive(sMinX, sMinY, sMaxX, sMaxY))
			{
				Window.active.getInputManager().setMouseGrabbed(false);
				this.drag = true;
				this.mouseOffX =  Window.active.getInputManager().getMouseX() - this.sliderPosX;
				this.mouseOffY =  this.sliderPosY - Window.active.getInputManager().getMouseY();
			}			
		}
	
		if(Window.active.getInputManager().isButtonHelt(0))
		{
			if(this.drag)
			{
				this.sliderPosY -= Window.active.getInputManager().getDY();
				float max = this.sizeY - this.sliderOffY1 - this.sliderSizeY - this.sliderOffY2;
				
				this.sliderPosY = Maths.clamp(this.sliderPosY, 0, max);
				
				float perc = Func.projectScale(this.sliderPosY, 0, 0, max, this.max); //Maths.img(this.sliderPosY, max, this.max);
				
				if((int)perc != this.value)
				{
					this.value = (int)perc;
					this.parent.response(this);
				}
				
			}
		}
		
		if(Window.active.getInputManager().isButtonReleased(0))
		{
			if(this.drag)
			{
				setMousePos((int)(this.sliderPosX + this.mouseOffX), (int)(com.codered.engine.window.active.HEIGHT - (this.sliderPosY - this.mouseOffY)));					
				Window.active.getInputManager().setMouseGrabbed(false);

				this.drag = false;
			}

		}
		
	}
}
