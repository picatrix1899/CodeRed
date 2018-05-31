package com.codered.demo;

import com.codered.engine.EngineBootstrap;
import com.codered.engine.window.WindowImpl;

public class DemoBootstrap extends EngineBootstrap
{
	public void boot()
	{
		super.boot();
		
		setWindow(new WindowImpl(800, 600, "CoderRed 3", new DemoWindowDebug()));

		start();
	}

}
