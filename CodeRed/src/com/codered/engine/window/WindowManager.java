package com.codered.engine.window;

import java.util.ArrayList;

public class WindowManager
{
	private static ArrayList<Window> windows = new ArrayList<Window>();

	public static void addWindow(Window w)
	{
		WindowManager.windows.add(w);
	}
	
	public static void start()
	{
		for(Window w : WindowManager.windows)
		{
			Thread t = new Thread(w.getTickRoutine());
			t.setDaemon(false);
			
			t.start();
		}
	}
	
	
	public static void end()
	{
		for(Window w : WindowManager.windows)
		{
			w.internalRelease();
		}
	}
}
