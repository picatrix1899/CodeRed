package com.codered.window;

public abstract class WindowRoutine
{
	protected WindowContext context;
	
	void setContext(WindowContext context)
	{
		this.context = context;
	}
	
 	public abstract void init();
 	public abstract void preUpdate();
	public abstract void update(double timestep);
	public abstract void render(double timestep, double alpha);
	public abstract void release(boolean forced);
}
