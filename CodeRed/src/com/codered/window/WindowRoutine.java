package com.codered.window;

public abstract class WindowRoutine
{
	protected WindowContext context;
	
	void setContext(WindowContext context)
	{
		this.context = context;
	}
	
 	public abstract void init();
	public abstract void update(double delta);
	public abstract void render(double delta);
	public abstract void release();
}
