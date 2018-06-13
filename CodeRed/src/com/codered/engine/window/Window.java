package com.codered.engine.window;

import org.lwjgl.opengl.GLCapabilities;

import cmn.utilslib.events.EventHandler;

public interface Window
{
	GLCapabilities getCapabilities();
	
	long getWindowId();
	
	void setWindowShouldClose();
	
	WindowRoutine getRoutine();
	
	WindowTickRoutine getTickRoutine();
	
	void release();
	
	void addResizeHandler(EventHandler<EventArgs> handler)
}
