package com.codered.engine.input;


public enum MouseButton
{
	LEFT(0),
	MIDDLE(2),
	RIGHT(1),
	;
	private final int id;
	
	private MouseButton(int id)
	{
		this.id = id;
	}
	
	public int getId() { return this.id; }
	
	public MouseButton getByIndex(int index)
	{
		return MouseButton.values()[index];
	}
}
