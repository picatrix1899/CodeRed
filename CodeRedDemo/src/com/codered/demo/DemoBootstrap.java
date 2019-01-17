package com.codered.demo;

import com.codered.EngineBootstrap;
import com.codered.window.Window;

public class DemoBootstrap extends EngineBootstrap
{
	public void boot()
	{
		super.boot();
		
		setWindow(new Window(800, 600, "CoderRed 3", new DemoWindowDebug()));

		start();
	}

}
