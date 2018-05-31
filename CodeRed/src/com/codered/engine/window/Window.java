package com.codered.engine.window;

import org.lwjgl.opengl.GLCapabilities;

public interface Window
{
	GLCapabilities getCapabilities();
	
	long getWindowId();
	
	void setWindowShouldClose();
	
	WindowRoutine getRoutine();
	
	WindowTickRoutine getTickRoutine();
	
	void release();
}
