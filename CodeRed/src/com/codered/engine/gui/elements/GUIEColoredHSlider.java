package com.codered.engine.gui.elements;

import com.codered.engine.gui.GUIElement;
import com.codered.engine.gui.GUIWindow;
import com.codered.engine.window.Window;

import cmn.utilslib.color.colors.LDRColor3;
import cmn.utilslib.math.Func;
import cmn.utilslib.math.Maths;

public class GUIEColoredHSlider extends GUIElement
{


	private float posX;
	private float posY;
	private float sizeX;
	private float sizeY;
	
	private float sliderOffX1;
	private float sliderOffX2;
	private float sliderOffY1;
	private float sliderOffY2;
	
	private float sliderPosX;
	private float sliderPosY;
	private float sliderSizeX;
	private float sliderSizeY;
	
	private LDRColor3 sliderColor; 
	private LDRColor3 backgroundColor;
	
	private boolean drag;
	
	private float mouseOffX;
	private float mouseOffY;
	
	private int value;
	private int max;
	
	public GUIEColoredHSlider(int id, GUIWindow parent, float posX, float posY, float sizeX, float sizeY, int val, int max)
	{
		super(id, parent);
		this.posX = posX;
		this.posY = posY;	
		this.sizeX = sizeX;
		this.sizeY = sizeY;
		this.sliderPosY = 0;
		this.sliderOffX1 = 5;
		this.sliderOffX2 = 5;
		this.sliderOffY1 = 5;
		this.sliderOffY2 = 5;
		this.sliderSizeX = 10;
		this.sliderSizeY = sizeY;
		this.max = max;
		
		float m = sizeX - sliderOffX1 - sliderSizeX - sliderOffX2;
		
		this.sliderPosX = Func.projectScale(val, 0, 0, this.max, m); // Maths.rimg(val, m, this.max);
		
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
		float max = sizeX - sliderOffX1 - sliderSizeX - sliderOffX2;
		this.value = val;
		this.sliderPosX = Func.projectScale(val, 0, 0, this.max, max);//Maths.rimg(val, max, this.max);
	}

	public LDRColor3 backgroundColor()
	{
		return this.backgroundColor;
	}
	
	public void render()
	{
		drawColoredRect(this.backgroundColor, this.posX, this.posY, this.sizeX, this.sizeY);
		drawColoredRect(this.sliderColor, this.posX + this.sliderOffX1 + this.sliderPosX, this.posY + this.sliderOffY1, this.sliderSizeX, this.sliderSizeY - this.sliderOffY1 - this.sliderOffY2);
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
				Window.active.getInputManager().setMouseGrabbed(true);
				this.drag = true;
				this.mouseOffX =  Window.active.getInputManager().getMouseX() - this.sliderPosX;
				this.mouseOffY =  this.sliderPosY - Window.active.getInputManager().getMouseY();
			}			
		}
	
		if(Window.active.getInputManager().isButtonHelt(0))
		{
			if(this.drag)
			{
				this.sliderPosX += Window.active.getInputManager().getDX();
				float max = this.sizeX - this.sliderOffX1 - this.sliderSizeX - this.sliderOffX2;
				
				this.sliderPosX = Maths.clamp(this.sliderPosX, 0, max);
				
				float perc = Func.projectScale(this.sliderPosX, 0, 0, max, this.max); //Maths.img(this.sliderPosX, max, this.max);
				
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
