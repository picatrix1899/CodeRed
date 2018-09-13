package com.codered.demo;

import com.codered.EngineBootstrap;
import com.codered.window.WindowImpl;

public class DemoBootstrap extends EngineBootstrap
{
	public void boot()
	{
		super.boot();
		
		setWindow(new WindowImpl(800, 600, "CoderRed 3", new DemoWindowDebug()));

		start();
	}

}
