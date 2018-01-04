package com.codered.engine.gui.elements;

import com.codered.engine.managing.Window;

public class Area
{
	public float minX;
	public float minY;
	public float maxX;
	public float maxY;
	
	public float getCenterX()
	{
		return this.minX + (this.maxX - this.minX) * 0.5f;
	}
	
	public float getCenterY()
	{
		return this.minY + (this.maxY - this.minY) * 0.5f;
	}
	
	public boolean mouseIsInsideInclusive()
	{
		float mX = Window.active.getInputManager().getMouseX();
		float mY = Window.active.getInputManager().getMouseY();

		boolean bX = mX >= minX && mX <= maxX;
		boolean bY = mY >= minY && mY <= maxY;
		
		return bX && bY;
	}

	public boolean mouseIsInsideExclusive()
	{
		float mX = Window.active.getInputManager().getMouseX();
		float mY = Window.active.getInputManager().getMouseY();

		boolean bX = mX > minX && mX < maxX;
		boolean bY = mY > minY && mY < maxY;
		
		return bX && bY;
	}
}
