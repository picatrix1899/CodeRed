package com.codered.engine.window;

public interface IWindowTickRoutine extends Runnable
{
	void setWindow(Window w);
	
	void end();
	
	void run();
	
	void init();
}
