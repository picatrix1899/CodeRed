package com.codered.window;

public abstract class WindowRoutine
{
	protected WindowContext context;
	
	public final void setWindowContext(WindowContext context) { this.context = context; }
	
	public void initWindowHints() { }
	
	public void init() { }
	
	public void update(double delta) { }
	
	public void render(double delta) { }
	
	public void release() { }
}
