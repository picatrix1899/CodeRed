package com.codered.gui.elements;

import com.codered.window.WindowContext;

public class Area
{
	public float minX;
	public float minY;
	public float maxX;
	public float maxY;
	
	protected WindowContext context;
	
	public Area(WindowContext context)
	{
		this.context = context;
	}
	
	public Area(WindowContext context, float minX, float minY, float maxX, float maxY)
	{
		this.context = context;
		
		this.minX = minX;
		this.minY = minY;
		this.maxX = maxX;
		this.maxY = maxY;
	}
	
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
		float mX = this.context.getMouse().getCurrentPos().x;
		float mY = this.context.getMouse().getCurrentPos().y;

		boolean bX = mX >= minX && mX <= maxX;
		boolean bY = mY >= minY && mY <= maxY;
		
		return bX && bY;
	}

	public boolean mouseIsInsideExclusive()
	{
		float mX = this.context.getMouse().getCurrentPos().x;
		float mY = this.context.getMouse().getCurrentPos().y;

		boolean bX = mX > minX && mX < maxX;
		boolean bY = mY > minY && mY < maxY;
		
		return bX && bY;
	}
}
