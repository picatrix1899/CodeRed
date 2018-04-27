package com.codered.engine.window;

public interface IWindowRoutine
{
	void setWindow(IWindowContext w);
	
	void initWindowHints();
	
	void init();
	
	void update(double delta);
	
	void render(double delta);
	
	void release();
	
	void preloadResources();
}
