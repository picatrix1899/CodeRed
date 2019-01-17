package com.codered.window;

public interface WindowTickRoutine extends Runnable
{
	void setWindow(Window w);
	
	void end();
	
	void run();
	
	void init();
}
