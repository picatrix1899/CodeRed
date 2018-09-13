package com.codered.window;

import org.lwjgl.opengl.GLCapabilities;

import cmn.utilslib.events.EventArgs;
import cmn.utilslib.events.EventHandler;

public interface Window
{
	GLCapabilities getCapabilities();
	
	long getWindowId();
	
	void setWindowShouldClose();
	
	WindowRoutine getRoutine();
	
	WindowTickRoutine getTickRoutine();
	
	void addResizeHandler(EventHandler<ResizeEventArgs> handler);
	
	public static class ResizeEventArgs implements EventArgs
	{

		public int width;
		public int height;
		
		public ResizeEventArgs(int width, int height)
		{
			this.width = width;
			this.height = height;
		}
		

		@Override
		public EventArgs cloneArgs()
		{
			return new ResizeEventArgs(this.width, this.height);
		}
		
	}
}
