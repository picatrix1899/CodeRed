package com.codered.engine.window;

public interface WindowTickRoutine extends Runnable
{
	void setWindow(WindowImpl w);
	
	void end();
	
	void run();
	
	void init();
}
