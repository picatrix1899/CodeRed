package com.codered.demo;

import com.codered.engine.EngineBootstrap;
import com.codered.engine.window.Window;
import com.codered.engine.window.WindowManager;

public class DemoBootstrap extends EngineBootstrap
{
	public void boot()
	{
		super.boot();
		
		WindowManager.addWindow(new Window(800, 600, "CoderRed 3", new DemoWindowDebug()));
		WindowManager.addWindow(new Window(800, 600, "CoderRed 3 II", new DemoWindowContext2()));
		
		start();
	}

}
